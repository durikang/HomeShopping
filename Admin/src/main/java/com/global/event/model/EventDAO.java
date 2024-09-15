package com.global.event.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EventDAO {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private String sql = null;

    private static EventDAO instance = null;

    public static EventDAO getInstance() {
        if (instance == null)
            instance = new EventDAO();
        return instance;
    }

    public void openConn() {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/myoracle");
            conn = ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null && !rs.isClosed())
                rs.close();
            if (pstmt != null && !pstmt.isClosed())
                pstmt.close();
            if (conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 이벤트 수 가져오기
    public int getEventCount(String eventType) {
        int count = 0;
        try {
            openConn();
            if ("coupon".equalsIgnoreCase(eventType)) {
            	sql = "SELECT count(*) FROM COUPON_EVENT JOIN EVENT USING(EVENT_NO) "
                        + "LEFT JOIN EVENT_IMAGE ON COUPON_EVENT.IMAGE_NO = EVENT_IMAGE.IMAGE_NO";
            } else if ("banner".equalsIgnoreCase(eventType)) {
            	sql = "SELECT count(*) FROM BANNER_EVENT JOIN EVENT USING(EVENT_NO) "
                        + "LEFT JOIN EVENT_IMAGE ON BANNER_EVENT.IMAGE_NO = EVENT_IMAGE.IMAGE_NO";
            } else {
            	 sql = "SELECT count(*) FROM EVENT LEFT JOIN EVENT_IMAGE USING(EVENT_NO)";
            }
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
        return count;
    }

 // 이벤트 리스트 가져오기
    public List<? extends Event> getEventList(String eventType) {
        List<? extends Event> eventList = new ArrayList<>();
        
        try {
            openConn();

            if ("coupon".equalsIgnoreCase(eventType)) {
                sql = "SELECT * FROM COUPON_EVENT JOIN EVENT USING(EVENT_NO) "
                    + "LEFT JOIN EVENT_IMAGE ON COUPON_EVENT.IMAGE_NO = EVENT_IMAGE.IMAGE_NO";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                List<CouponEvent> couponEventList = new ArrayList<>();

                while (rs.next()) {
                    CouponEvent couponEvent = new CouponEvent();
                    populateCommonEventFields(couponEvent, rs);
                    couponEvent.setCouponCode(rs.getString("COUPON_CODE"));
                    couponEvent.setDiscountAmount(rs.getDouble("DISCOUNT_AMOUNT"));
                    couponEvent.setDiscountPercent(rs.getDouble("DISCOUNT_PERCENT"));
                    couponEvent.setExpiryDate(rs.getDate("EXPIRY_DATE"));

                    // EventImage 처리
                    EventImage eventImage = populateEventImage(rs);
                    couponEvent.setEventImage(eventImage);

                    couponEventList.add(couponEvent);
                }
                return couponEventList;

            } else if ("banner".equalsIgnoreCase(eventType)) {
                sql = "SELECT * FROM BANNER_EVENT JOIN EVENT USING(EVENT_NO) "
                    + "LEFT JOIN EVENT_IMAGE ON BANNER_EVENT.IMAGE_NO = EVENT_IMAGE.IMAGE_NO";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                List<BannerEvent> bannerEventList = new ArrayList<>();

                while (rs.next()) {
                    BannerEvent bannerEvent = new BannerEvent();
                    populateCommonEventFields(bannerEvent, rs);
                    bannerEvent.setLinkUrl(rs.getString("LINK_URL"));
                    bannerEvent.setDisplayStartDate(rs.getDate("DISPLAY_START_DATE"));
                    bannerEvent.setDisplayEndDate(rs.getDate("DISPLAY_END_DATE"));

                    // EventImage 처리
                    EventImage eventImage = populateEventImage(rs);
                    bannerEvent.setEventImage(eventImage);

                    bannerEventList.add(bannerEvent);
                }
                return bannerEventList;

            } else {
                sql = "SELECT * FROM EVENT LEFT JOIN EVENT_IMAGE USING(EVENT_NO)";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                List<Event> generalEventList = new ArrayList<>();

                while (rs.next()) {
                    Event event = new Event();
                    populateCommonEventFields(event, rs);

                    // EventImage 처리
                    EventImage eventImage = populateEventImage(rs);
                    event.setEventImage(eventImage);

                    generalEventList.add(event);
                }
                return generalEventList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
        return eventList;
    }


    // 공통 이벤트 필드를 채우는 헬퍼 메서드
    private void populateCommonEventFields(Event event, ResultSet rs) throws SQLException {
        event.setEventNo(rs.getInt("EVENT_NO"));
        event.setName(rs.getString("NAME"));
        event.setDescription(rs.getString("DESCRIPTION"));
        event.setViews(rs.getInt("VIEWS"));
        event.setStartDate(rs.getDate("START_DATE"));
        event.setEndDate(rs.getDate("END_DATE"));
        event.setCreatedAt(rs.getDate("CREATED_AT"));
        event.setUpdatedAt(rs.getDate("UPDATED_AT"));
        event.setStatus(rs.getString("STATUS"));
        event.setEventType(rs.getString("EVENT_TYPE"));
    }

 // EventImage를 생성하는 헬퍼 메서드
    private EventImage populateEventImage(ResultSet rs) throws SQLException {
        // 이미지 데이터가 존재하는지 체크 (IMAGE_NO가 null이 아니고 0이 아닐 때)
        Integer imageNo = rs.getObject("IMAGE_NO") != null ? rs.getInt("IMAGE_NO") : null;
        if (imageNo != null) {
            EventImage eventImage = new EventImage();
            eventImage.setImageId(rs.getInt("IMAGE_NO"));
            eventImage.setFilePath(rs.getString("FILE_PATH"));
            eventImage.setFileName(rs.getString("FILE_NAME"));
            eventImage.setFileSize(rs.getInt("FILE_SIZE"));
            eventImage.setCreatedAt(rs.getDate("CREATED_AT"));
            eventImage.setUpdatedAt(rs.getDate("UPDATED_AT"));
            return eventImage;
        }
        return null;  // 이미지가 없을 경우 null 반환
    }

    // 이벤트 상세 정보 가져오기
    public Event getEventDetail(int eventNo, String eventType) {
        Event event = null;
        try {
            openConn();
            String sql = "";
            if ("COUPON".equalsIgnoreCase(eventType)) {
                sql = "SELECT * FROM COUPON_EVENT JOIN EVENT USING(EVENT_NO) "
                        + "LEFT JOIN EVENT_IMAGE ON COUPON_EVENT.EVENT_NO = EVENT_IMAGE.EVENT_NO WHERE EVENT_NO = ?";
            } else if ("BANNER".equalsIgnoreCase(eventType)) {
                sql = "SELECT * FROM BANNER_EVENT JOIN EVENT USING(EVENT_NO) "
                        + "LEFT JOIN EVENT_IMAGE ON BANNER_EVENT.EVENT_NO = EVENT_IMAGE.EVENT_NO WHERE EVENT_NO = ?";
            } else if("BANNER".equalsIgnoreCase(eventType)) {
                sql = "SELECT * FROM EVENT LEFT JOIN EVENT_IMAGE USING(EVENT_NO) WHERE EVENT_NO = ?";
            }
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, eventNo);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                event = new Event();
                populateCommonEventFields(event, rs);

                // 쿠폰 이벤트 또는 배너 이벤트에 맞는 필드 설정
                if ("COUPON".equalsIgnoreCase(eventType)) {
                    ((CouponEvent) event).setCouponCode(rs.getString("COUPON_CODE"));
                    ((CouponEvent) event).setDiscountAmount(rs.getDouble("DISCOUNT_AMOUNT"));
                    ((CouponEvent) event).setDiscountPercent(rs.getDouble("DISCOUNT_PERCENT"));
                    ((CouponEvent) event).setExpiryDate(rs.getDate("EXPIRY_DATE"));
                } else if ("BANNER".equalsIgnoreCase(eventType)) {
                    ((BannerEvent) event).setLinkUrl(rs.getString("LINK_URL"));
                    ((BannerEvent) event).setDisplayStartDate(rs.getDate("DISPLAY_START_DATE"));
                    ((BannerEvent) event).setDisplayEndDate(rs.getDate("DISPLAY_END_DATE"));
                }

                // EventImage 처리
                EventImage eventImage = populateEventImage(rs);
                event.setEventImage(eventImage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
        return event;
    }
    
    public int insertEvent(Event event) {
        int eventNo = 0;
        try {
            openConn();
            sql = "INSERT INTO EVENT (EVENT_NO, NAME, DESCRIPTION, START_DATE, END_DATE, EVENT_TYPE) "
                + "VALUES (EVENT_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql, new String[] {"EVENT_NO"});
            pstmt.setString(1, event.getName());
            pstmt.setString(2, event.getDescription());
            pstmt.setDate(3, event.getStartDate());
            pstmt.setDate(4, event.getEndDate());
            pstmt.setString(5, event.getEventType());
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();  // 자동 생성된 키 값을 가져옴
            if (rs.next()) {
                eventNo = rs.getInt(1);  // 생성된 EVENT_NO 값 반환
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
        return eventNo;
    }

    public void insertBannerEvent(BannerEvent bannerEvent) {
        try {
            openConn();
            sql = "INSERT INTO BANNER_EVENT (BANNER_EVENT_NO, EVENT_NO, LINK_URL) "
                + "VALUES (BANNER_SEQ.NEXTVAL, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bannerEvent.getEventNo());
            pstmt.setString(2, bannerEvent.getLinkUrl());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
    }

    public void insertCouponEvent(CouponEvent couponEvent) {
        try {
            openConn();
            sql = "INSERT INTO COUPON_EVENT (COUPON_EVENT_NO, EVENT_NO, COUPON_CODE, DISCOUNT_AMOUNT, DISCOUNT_PERCENT, EXPIRY_DATE) "
                + "VALUES (COUPON_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, couponEvent.getEventNo());
            pstmt.setString(2, couponEvent.getCouponCode());
            pstmt.setDouble(3, couponEvent.getDiscountAmount());
            pstmt.setDouble(4, couponEvent.getDiscountPercent());
            pstmt.setDate(5, couponEvent.getExpiryDate());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
    }




}

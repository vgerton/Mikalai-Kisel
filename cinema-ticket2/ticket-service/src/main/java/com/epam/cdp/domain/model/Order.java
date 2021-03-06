package com.epam.cdp.domain.model;

/**
 * @author mikalai.kisel@ihg.com
 * @since Feb 10, 2015.
 */
public class Order {
    private static Long orderGlobalCounter = 0L;

    private Long orderId;
    private Long userId;
    private Long showTimeId;
    private Integer place;

    public Order() {
        orderGlobalCounter++;
        orderId = orderGlobalCounter;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(Long showTimeId) {
        this.showTimeId = showTimeId;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }
}

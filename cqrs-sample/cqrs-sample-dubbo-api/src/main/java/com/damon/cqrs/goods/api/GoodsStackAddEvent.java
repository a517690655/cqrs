package com.damon.cqrs.goods.api;

import com.damon.cqrs.domain.Event;

public class GoodsStackAddEvent extends Event {

    /**
     *
     */
    private static final long serialVersionUID = -1473545901956281741L;
    private int number;

    public GoodsStackAddEvent() {
        super();
    }

    /**
     * @param number
     */
    public GoodsStackAddEvent(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
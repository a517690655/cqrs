package com.damon.cqrs.sample.weixin;

import com.damon.cqrs.domain.Aggregate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

/**
 * 微信红包聚合根
 *
 * @author xianpinglu
 */
public class WeixinRedPacket extends Aggregate {
    /**
     * key 用户id   value  抢到的金额
     */
    private Map<Long, Long> map = new HashMap<>();

    private Stack<Long> redpacketStack;

    private RedPacketTypeEnum type;

    private Long sponsorId;

    public WeixinRedPacket() {

    }

    public WeixinRedPacket(Long id, Long money, int number, RedPacketTypeEnum type, Long sponsorId) {
        super(id);
        Stack<Long> stack = new Stack<>();
        if (RedPacketTypeEnum.AVG.equals(type)) {
            Long avgMoney = money / number;
            //平均分配
            for (int i = 0; i < number; i++) {
                stack.push(avgMoney);
            }
        } else {
            throw new RuntimeException("unrealized");
        }
        RedPacketGrabCreatedEvent event = new RedPacketGrabCreatedEvent(stack);
        event.setAggregateId(id);
        event.setType(type);
        event.setSponsorId(sponsorId);
        super.applyNewEvent(event);
    }

    /**
     * 抢红包
     *
     * @param userId
     * @return
     */
    public int grabRedPackage(Long userId) {

        if (redpacketStack.size() == 0) {
            return 0;
        }

        if (map.get(userId) != null) {
            return -1;
        }

        super.applyNewEvent(new RedPacketGrabSucceedEvent(redpacketStack.peek(), userId));

        return 1;
    }

    private void apply(RedPacketGrabSucceedEvent event) {
        map.put(event.getUserId(), redpacketStack.pop());
    }

    private void apply(RedPacketGrabCreatedEvent event) {
        this.redpacketStack = event.getRedpacketStack();
        this.type = event.getType();
        this.sponsorId = event.getSponsorId();
    }

    @Override
    public long createSnapshootCycle() {
        return 5;
    }

    public Map<Long, Long> getMap() {
        return map;
    }

    public Stack<Long> getRedpacketStack() {
        return redpacketStack;
    }

    public RedPacketTypeEnum getType() {
        return type;
    }

    public Long getSponsorId() {
        return sponsorId;
    }

    public static double getRandomMoney(RedPackage _redPackage) {
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        if (_redPackage.remainSize == 1) {
            _redPackage.remainSize--;
            return (double) Math.round(_redPackage.remainMoney * 100) / 100;
        }
        Random r     = new Random();
        double min   = 0.01; //
        double max   = _redPackage.remainMoney / _redPackage.remainSize * 2;
        double money = r.nextDouble() * max;
        money = money <= min ? 0.01: money;
        money = Math.floor(money * 100) / 100;
        _redPackage.remainSize--;
        _redPackage.remainMoney -= money;
        return money;
    }
}

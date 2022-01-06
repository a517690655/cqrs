package com.damon.cqrs.sample.weixin;

import com.damon.cqrs.domain.Event;
import lombok.Data;

import java.util.Stack;

@Data
public class RedPacketGrabCreatedEvent extends Event {

    private Stack<Long> redpacketStack;

    private RedPacketTypeEnum type;

    private Long sponsorId;

    public RedPacketGrabCreatedEvent() {
        super();
    }

    public RedPacketGrabCreatedEvent(Stack<Long> redpacketStack) {
        super();
        this.redpacketStack = redpacketStack;
    }


}

package com.damon.cqrs.sample.train.command;


import com.damon.cqrs.domain.Command;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class TrainCreateCommand extends Command {

    private Map<Integer, Integer> s2sSeatCount;

    /**
     * @param commandId
     * @param aggregateId
     */
    public TrainCreateCommand(long commandId, long aggregateId) {
        super(commandId, aggregateId);
    }

    public Map<Integer, Integer> getS2sSeatCount() {
        return s2sSeatCount;
    }

    public void setS2sSeatCount(Map<Integer, Integer> s2sSeatCount) {
        this.s2sSeatCount = s2sSeatCount;
    }
}


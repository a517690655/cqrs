package com.damon.cqrs.sample.train.listener;

import com.alibaba.fastjson.JSONObject;
import com.damon.cqrs.domain.Event;
import com.damon.cqrs.rocketmq.RocketMQEventListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;

import java.util.List;
import java.util.Map;

/**
 * Q端车次事件监听器，更新Q端数据
 *
 * @author xianpinglu
 */
@Slf4j
public class TrainEventListener extends RocketMQEventListener {

    public TrainEventListener(String nameServer, String topic, String consumerGroup, int minThread, int maxThread, int pullBatchSize) throws MQClientException {
        super(nameServer, topic, consumerGroup, minThread, maxThread, pullBatchSize);
    }

    @Override
    public void process(Map<String, List<Event>> aggregateEventGroup) {
        aggregateEventGroup.forEach((aggregateId, events) -> {
            log.info("aggregate type : {}, event list size: {}.", aggregateId, events.size());
            events.forEach(event -> {
                log.info(JSONObject.toJSONString(event));
            });
        });
    }
}

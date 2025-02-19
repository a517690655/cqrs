package com.damon.cqrs;

import com.damon.cqrs.domain.Aggregate;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 基于guava cache聚合根缓存
 *
 * @author xianpinglu
 */
@Slf4j
public class DefaultAggregateGuavaCache implements IAggregateCache {

    private final Cache<Long, Aggregate> aggregateCache;

    /**
     * @param cacheMaximumSize 最多能够缓存多少聚合个数
     * @param expireTime       有效时间（分钟）
     */
    public DefaultAggregateGuavaCache(int cacheMaximumSize, int expireTime) {
        /**
         * 聚合缓存
         */
        aggregateCache = CacheBuilder.newBuilder().maximumSize(cacheMaximumSize).expireAfterAccess(expireTime, TimeUnit.MINUTES).removalListener(notify -> {
            Long aggregateId = (Long) notify.getKey();
            Aggregate aggregate = (Aggregate) notify.getValue();
            log.info("aggregate id : {}, aggregate type : {} , version:{},  expired.", aggregateId, aggregate.getClass().getTypeName(), aggregate.getVersion());
        }).build();
    }

    @Override
    public void update(long id, Aggregate aggregate) {
        aggregateCache.put(id, aggregate);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Aggregate> T get(long id) {
        return (T) aggregateCache.getIfPresent(id);
    }

}

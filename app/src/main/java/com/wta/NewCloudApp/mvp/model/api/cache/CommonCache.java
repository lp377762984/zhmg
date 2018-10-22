package com.wta.NewCloudApp.mvp.model.api.cache;

import com.wta.NewCloudApp.mvp.model.entity.BType;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

public interface CommonCache {
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<List<BType>>> getUsers(Observable<List<BType>> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);
}

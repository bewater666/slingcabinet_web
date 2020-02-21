package com.orient.slingcabinet_web.utils.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @Author: zhoudun
 * @Date: 2019/3/14 14:31
 * @Func: 重写Shiro缓存管理器
 * @Version 1.0
 */

public class CustomCacheManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new CustomCache<K,V>();
    }
}

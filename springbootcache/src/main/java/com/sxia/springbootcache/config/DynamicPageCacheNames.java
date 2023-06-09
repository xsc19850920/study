package com.sxia.springbootcache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

/**
 * 动态缓存key
 */
@Component
public class DynamicPageCacheNames extends SimpleCacheResolver implements CacheResolver {
    //        private final AuthenticationHelper authenticationHelper;
    public static final String CONDITION = "condition";

    public DynamicPageCacheNames(CacheManager cacheManager/**, AuthenticationHelper authenticationHelper*/) {
        super(cacheManager);
//            log.debug(String.format("using customize CacheResolver: %s , cacheManager: %s",
//                    this.getClass().getName(), cacheManager.getClass().getName()));
//            this.authenticationHelper = authenticationHelper;
    }

    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
//            Long uId = authenticationHelper.checkAndExtractUserFromAuthentication().getId();
//            String cacheName = String.format("%s_%d", FIND_ALL_BY_USER_AND_PAGEABLE, uId);
//            log.debug(String.format("generate cache name %s for target %s", cacheName, context.getTarget()));
//            return Collections.singleton((String.format("%s_%d", FIND_ALL_BY_USER_AND_PAGEABLE, uId)));
        return Collections.singleton(String.format("%s_%s", CONDITION, context.getTarget().getClass().getName()));
    }
}
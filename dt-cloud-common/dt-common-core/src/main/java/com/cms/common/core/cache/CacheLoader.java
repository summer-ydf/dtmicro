package com.cms.common.core.cache;

/**
 * @author ydf Created by 2022/4/20 11:46
 */
@FunctionalInterface
public interface CacheLoader {
    Object load();
}

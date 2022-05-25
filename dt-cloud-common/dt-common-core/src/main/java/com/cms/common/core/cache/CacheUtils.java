package com.cms.common.core.cache;


import lombok.extern.apachecommons.CommonsLog;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

/**
 * @author DT辰白 Created by 2022/4/20 11:38
 */
@CommonsLog
@Lazy(false)
public class CacheUtils implements ApplicationContextAware, DisposableBean {

    private static final String default_cache = "default_cache";

    private static BasicCache basicCache;

    public static Object get(String cacheName, String key) {
        return get(cacheName, key,false);
    }

    public static Object get(String cacheName, String key, CacheLoader cacheLoader) {
        return get(cacheName, key,false,cacheLoader);
    }

    //是否序列化，如果要把缓存里面的数据取出来进行编辑，请使用序列化保存
    public static Object get(String cacheName, String key,boolean serializable) {
        return basicCache.get(cacheName, key,serializable);
    }

    private static Object get(String cacheName, String key, boolean serializable,CacheLoader cacheLoader) {
        return basicCache.get(cacheName, key,serializable,cacheLoader);
    }

    public static Object get(String key) {
        return get(key,false);
    }

    public static Object get(String key,CacheLoader cacheLoader) {
        return get(key,false,cacheLoader);
    }

    //是否序列化，如果要把缓存里面的数据取出来进行编辑，请使用序列化保存
    public static Object get(String key,boolean serializable) {
        return get(default_cache,key,serializable);
    }

    public static Object get(String key,boolean serializable,CacheLoader cacheLoader) {
        return get(default_cache,key,serializable,cacheLoader);
    }

    public static void put(String cacheName, String key, Object value) {
        put(cacheName, key, value,false);
    }

    public static void put(String cacheName, String key, Object value,boolean serializable) {
        basicCache.put(cacheName, key, value,serializable);
    }

    public static void put(String key, Object value) {
        put(key, value,false);;
    }

    public static void put(String key, Object value,boolean serializable) {
        basicCache.put(default_cache,key, value,serializable);
    }

    public static void remove(String cacheName, List keys) {
        basicCache.remove(cacheName, keys);
    }

    public static void remove(List keys) {
        basicCache.remove(keys);
    }

    public static void  remove(String key) {
        basicCache.remove(key);
    }

    public static void remove(String cacheName,String key) {
        basicCache.remove(cacheName, key);
    }

    public static void removeMatcher(String pattern) {
        basicCache.removeMatcher(pattern);
    }

    public static void removeMatcher(String cacheName, String pattern) {
        basicCache.removeMatcher(cacheName, pattern);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try{
            CacheManager cacheManager = applicationContext.getBean(CacheManager.class);
            CacheUtils.basicCache = new EhCache(cacheManager);
            log.info("初始化缓存成功============================ ");
        }catch (Exception e){
            log.error("初始化缓存失败============================ ");
        }
    }

    @Override
    public void destroy() {
        if(basicCache != null) {
            basicCache.destroy();
        }
    }

    private interface BasicCache {
        Object get(String cacheName, String key,boolean serializable);
        Object get(String cacheName, String key,boolean serializable,CacheLoader cacheLoader);
        void put(String cacheName, String key, Object value,boolean serializable);
        void remove(String cacheName, List keys);
        void remove(List keys);
        void removeMatcher(String pattern);
        void removeMatcher(String cacheName,String pattern);
        void remove(String key);
        void remove(String cacheName,String key);
        void destroy();
    }

    private static class EhCache implements BasicCache {
        private final CacheManager cacheManager;
        private static final Lock lock;
        static {
            ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
            lock= readWriteLock.writeLock();
        }
        public EhCache(CacheManager cacheManager) {
            this.cacheManager = cacheManager;
        }

        @Override
        public Object get(String cacheName, String key,boolean serializable) {
            Element element = getCache(cacheName).get(key);
            if(element==null) {
                return null;
            }
            if(serializable) {
                return FastJsonSerializerUtils.deserialize((byte[]) element.getObjectValue());
            }
            return element.getObjectValue();
        }

        @Override
        public Object get(String cacheName, String key, boolean serializable, CacheLoader cacheLoader) {
            Object o = get(cacheName,key,serializable);
            if(o==null) {
                lock.lock();
                try {
                    if(o == null) {
                        o = cacheLoader.load();
                        put(cacheName,key,o,serializable);
                    }else {
                        o = get(cacheName,key,serializable);
                    }
                }finally {
                    lock.unlock();
                }
            }
            return o;
        }

        @Override
        public void put(String cacheName, String key, Object value,boolean serializable) {
            Element element = null;
            if(serializable){
                element = new Element(key, FastJsonSerializerUtils.serialize(value));
            }else {
                element = new Element(key, value);
            }
            getCache(cacheName).put(element);
        }

        @Override
        public void remove(String cacheName, List keys) {
            if(keys==null || keys.size()==0) {
                return;
            }
            Cache cache=getCache(cacheName);
            if(cache!=null){
                keys.forEach(k->this.remove(ObjectUtils.toString(k)));
            }
        }

        @Override
        public void remove(List keys) {
            remove(default_cache,keys);
        }

        @Override
        public void removeMatcher(String pattern) {
            removeMatcher(default_cache,pattern);
        }

        @Override
        public void removeMatcher(String cacheName,String pattern_str) {
            if(pattern_str==null) {
                return;
            }
            Cache cache=getCache(cacheName);
            if(cache!=null){
                List keys = cache.getKeys();
                Pattern pattern=Pattern.compile(pattern_str);
                for(Object key:keys){
                    String str= ObjectUtils.toString(key);
                    if (pattern.matcher(str).find()){
                        remove(cacheName,str);
                    }
                }
            }
        }

        @Override
        public void remove(String key) {
            remove(default_cache,key);
        }

        @Override
        public void remove(String cacheName, String key) {
            Cache cache=getCache(cacheName);
            if(cache!=null){
                cache.remove(key);
            }
        }

        @Override
        public void destroy() {
            if(cacheManager!=null){
                cacheManager.shutdown();
            }
        }

        private synchronized Cache getCache(String cacheName){
            Cache cache = cacheManager.getCache(cacheName);
            if (cache == null){
                cacheManager.addCache(cacheName);
                cache = cacheManager.getCache(cacheName);
                cache.getCacheConfiguration().setEternal(true);
            }
            return cache;
        }
    }
}

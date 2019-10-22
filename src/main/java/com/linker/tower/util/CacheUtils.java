package com.linker.tower.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author liu.jj
 * @date 2019/10/10
 */
@Slf4j
@Component
public class CacheUtils {

    LoadingCache<String, String> graphs = CacheBuilder.newBuilder()
            .maximumSize(2)
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<String, String>() {
                        @Override
                        public String load(String key) {
                            return null;
                        }
                    }
            );

    public String graphsGet() throws ExecutionException {
         try {
             if(graphs.get("key") == null){
                 return "";
             }else{
                 return graphs.get("key");
             }
         }catch (Exception e){
             log.error("查询不到缓存");
         }
         return "";

    }

    public void graphsSet(String content) throws ExecutionException {
        graphs.put("key",content);
    }

    public void graphsInvalidate() throws ExecutionException {
        graphs.invalidate("key");
    }
}

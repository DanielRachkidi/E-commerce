package org.example.ehcache;

import com.commerce.datamodel.Product;
import java.util.Map;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriter;
import javax.cache.integration.CacheWriterException;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.*;
import org.ehcache.config.units.*;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.spi.loaderwriter.CacheLoaderWriter;
import org.ehcache.spi.loaderwriter.CacheLoaderWriterConfiguration;
import org.ehcache.spi.loaderwriter.CacheLoaderWriterProvider;

import java.util.concurrent.TimeUnit;
import org.ehcache.spi.service.Service;
import org.ehcache.spi.service.ServiceProvider;

public class ProductCache
{
  
  private Cache<Integer, Product> cache;
  
  public ProductCache()
  {
    CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
    cacheManager.init();
    
    CacheConfigurationBuilder<Integer, com.commerce.datamodel.Product> configBuilder =
      CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, com.commerce.datamodel.Product.class,
          ResourcePoolsBuilder.newResourcePoolsBuilder()
            .heap(100, EntryUnit.ENTRIES)
            .offheap(1, MemoryUnit.MB)
            .disk(20, MemoryUnit.MB, true)
        )
        .withExpiry(Expirations.timeToLiveExpiration(Duration.of(30, TimeUnit.SECONDS)))
        .withLoaderWriter(createCacheLoaderWriter());
    
    cache = cacheManager.createCache("productCache", configBuilder.build());
  }
  
  public Cache<Integer, Product> getCache() {
    return cache;
  }
  
  private CacheLoaderWriter<Integer, Product> createCacheLoaderWriter() {
    CacheLoader<Product> cacheLoader = new ProductCacheLoader();
    CacheWriter<Integer, Product> cacheWriter = new ProductCacheWriter();
    
    CacheLoaderWriterProvider cacheLoaderWriterProvider = new CacheLoaderWriterProvider() {
      @Override
      public <K, V> CacheLoaderWriter<? super K, V> createCacheLoaderWriter(String cacheName, CacheConfiguration<K, V> configuration) {
        if (cacheName.equals("productCache")) {
          return (CacheLoaderWriter<? super K, V>) new ProductCacheLoaderWriter(cacheLoader, cacheWriter);
        }
        return null;
      }
    };
    
    return cacheLoaderWriterProvider.createCacheLoaderWriter("productCache", null);
  }
  
}
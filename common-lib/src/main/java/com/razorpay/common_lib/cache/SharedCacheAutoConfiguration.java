package com.razorpay.common_lib.cache;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import tools.jackson.databind.ObjectMapper;

@AutoConfiguration
public class SharedCacheAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ApiKeyCache.class)
    @ConditionalOnBean({StringRedisTemplate.class, ObjectMapper.class})
    public ApiKeyCache apiKeyCache(StringRedisTemplate stringRedisTemplate,
                                   ObjectMapper objectMapper) {
        return new RedisApiKeyCache(stringRedisTemplate, objectMapper);
    }
}

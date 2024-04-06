package com.tencent.wxcloudrun.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liusheng
 * @date 2024/4/61:44
 * @desc TODO
 */
@Configuration
public class SyObjectMapper {
    @Bean
    public ObjectMapper objectMapper() {
        // 单例模式
        return new ObjectMapper();
    }
}

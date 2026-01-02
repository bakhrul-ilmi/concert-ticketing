//package com.goats.buyer.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.NameMapper;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//
//@Configuration
//public class TempRedisConfig {
//
//    @Value("${redis.host}")
//    private String redisHost;
//
//    @Value("${redis.prefix}")
//    private String redisPrefix;
//
//    @Value("${redis.password}")
//    private String redisPassword;
//
//    @Bean
//    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
//    public RedissonClient redis() {
//        Config config = new Config();
//        config.useSingleServer()
//            .setAddress(redisHost)
//            .setConnectionPoolSize(10)
//            .setConnectionMinimumIdleSize(5)
//            .setConnectTimeout(30000)
//            .setNameMapper(customNameWMapper(redisPrefix));
//
//        if (!redisPassword.isEmpty()) {
//            config.useSingleServer()
//                .setAddress(redisHost)
//                .setConnectionPoolSize(10)
//                .setConnectionMinimumIdleSize(5)
//                .setConnectTimeout(30000)
//                .setNameMapper(customNameWMapper(redisPrefix))
//                .setPassword(redisPassword);
//        }
//        return Redisson.create(config);
//    }
//
//    private NameMapper customNameWMapper(String prefixKey) {
//        return new NameMapper() {
//            @Override
//            public String map(String name) {
//                return prefixKey + ":" + name;
//            }
//
//            @Override
//            public String unmap(String name) {
//                String prefix = prefixKey + ":";
//                String[] splittedName = name.split(prefix);
//                if (splittedName.length > 1) {
//                    return splittedName[1];
//                }
//                return name;
//            }
//        };
//    };
//}

package com.bw.wangruixue.cache;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by lenovo on 2017/7/14.
 * 创建一个session缓存的实体类
 * 分别加上开启缓存的注解,
 * 当然可以设置缓存的失效时间
 * maxInactiveIntervalInSeconds: 设置Session失效时间，
 * 使用Redis Session之后，原Boot的server.session.timeout属性不再生效
 *
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 36000 * 30)
public class SessionConfig {

    //kkkkkkkkkkkkkkkkkkkkkkkkkkkkkk
}

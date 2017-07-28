package com.bw.wangruixue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Created by WangRuiXue on 2017/7/28.
 */
@SpringBootApplication
@EnableCaching
public class GoodluckWangruixueProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodluckWangruixueProviderApplication.class, args);
	}
}

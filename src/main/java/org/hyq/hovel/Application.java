package org.hyq.hovel;

import org.hyq.hovel.model.ClassA;
import org.hyq.hovel.model.ClassB;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EntityScan("org.hyq.cadip.hovel.entity")
@MapperScan(basePackages = {"org.hyq.cadip.hovel.mapper"})
@EnableAsync
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public ClassA classA(){
        return new ClassA();
    }

    @Bean
    @ConditionalOnBean(name = "ClassB")
    public ClassB classB(){
        return new ClassB();
    }
}



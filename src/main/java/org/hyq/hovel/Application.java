package org.hyq.hovel;

import org.hyq.hovel.model.ClassA;
import org.hyq.hovel.model.ClassB;
import org.hyq.hovel.model.maze.Maze;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"org.hyq"})
@EntityScan("org.hyq.cadip.hovel")
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

//    @Bean(initMethod = "init")
//    public Maze maze(){
//        return new Maze();
//    }
}



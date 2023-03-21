package org.hyq.hovel.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.hyq.hovel.model.ClassA;
import org.hyq.hovel.model.ClassB;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@MapperScan("org.hyq.hovel.mapper")
public class MybatisPlusConfig {
    /**
     * 物理分页
     * */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public ClassA classA(){
        return new ClassA();
    }

    @Bean
    @ConditionalOnBean(name = "ClassA")
    public ClassB classB(){
        return new ClassB();
    }
}

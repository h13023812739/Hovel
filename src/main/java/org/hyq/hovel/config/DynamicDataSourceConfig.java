package org.hyq.hovel.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "org.hyq.hovel.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
public class DynamicDataSourceConfig {

    @Bean
    public MybatisConfiguration configuration() {
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(StdOutImpl.class);
        configuration.setUseColumnLabel(true);
        return configuration;
    }

    @Bean(name = "master")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource getDateSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource getDateSource2() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource DataSource(@Qualifier("master") DataSource masterDataSource,
                                        @Qualifier("slave") DataSource slaveDataSource) {
        //这个地⽅是⽐较核⼼的targetDataSource 集合是我们数据库和名字之间的映射
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DataSourceType.DataBaseType.Master, masterDataSource);
        targetDataSource.put(DataSourceType.DataBaseType.Slave, slaveDataSource);


        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        dataSource.setDefaultTargetDataSource(masterDataSource);
        return dataSource;
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory SqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        MybatisSqlSessionFactoryBean  bean = new MybatisSqlSessionFactoryBean();
        // jdbc数据源整合mybatis的SqlSessionFactory
        bean.setDataSource(dynamicDataSource);
        // 设置配置: 类似mybatis-config.xml
        bean.setConfiguration(configuration());
//        bean.setTypeAliasesPackage("com.lonk.demo.template.entity");
        // bean.setPlugins();
//        bean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource){
        return new DataSourceTransactionManager(dynamicDataSource);
    }


}

package com.external.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:/application.properties", "classpath:config/db.properties", "classpath:config/redis.properties"})
@MapperScan(basePackages = {"com.external.oauth.mapper", "com.external.account.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
public class RootConfig {

    @Value("${db.driverClassName}")
    private String driverClassName;

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

//        config.setMaximumPoolSize(100);          // 최대 100개 커넥션 (1000명 / 10 = 100)
//        config.setMinimumIdle(50);               // 최소 50개 유지 (피크타임 대비)
//        config.setConnectionTimeout(3000);       // 3초 커넥션 대기 (빠른 실패)
//        config.setIdleTimeout(300000);           // 5분 유휴 타임아웃
//        config.setMaxLifetime(600000);           // 10분 최대 생존시간
//        config.setLeakDetectionThreshold(15000); // 15초 누수 탐지
//        config.setValidationTimeout(2000);       // 2초 검증 타임아웃*/

        // jdbc레벨 캐싱 최적화
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "100");        // 1000 → 100
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "1024");   // 2048 → 1024
        config.addDataSourceProperty("useServerPrepStmts", "false");     // true → false (중요!)
        config.addDataSourceProperty("rewriteBatchedStatements", "true"); // 유지

        return new HikariDataSource(config);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        sqlSessionFactory.setConfigLocation(new org.springframework.core.io.ClassPathResource("mybatis-config.xml"));
        sqlSessionFactory.setMapperLocations(new org.springframework.core.io.support.PathMatchingResourcePatternResolver().getResources("classpath:mappers/**/*.xml"));
        return sqlSessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    @Bean  // 해당 메서드에서 반환된 객체를 Bean으로 등록
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // RedisTemplate을 생성하고 연결 팩토리를 설정
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template; // 설정된 RedisTemplate 반환
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }
}

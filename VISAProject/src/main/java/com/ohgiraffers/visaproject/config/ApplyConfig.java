package com.ohgiraffers.visaproject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc

//@EnableJpaRepositories("com.ohgiraffers.visaproject.model.repository")
//@EnableTransactionManagement  //트랜젝션 관리 활성화
public class ApplyConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(swaggerInfo());
    }

    private io.swagger.v3.oas.models.info.Info swaggerInfo(){
        return new Info()
                .title("Ohgiraffres API")
                .description("SpringBoot Swagger연동테스트")
                .version("1.0.0");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 경로에 대해 CORS 허용
                .allowedOrigins("http://localhost:5000","http://localhost:3000")  // 허용할 프론트엔드 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메소드
                .allowedHeaders("*")  // 모든 헤더 허용
                .allowCredentials(true);  // 쿠키나 인증정보 허용
    }

//    @Bean(name = "entityManagerFactory")
//    @Primary   //이빈을 우선사용하도록 설정함
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource){
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPackagesToScan("com.ohgiraffers.visaproject.entity");//엔티티패키지설정
//
//        //추가설정필요하면 여기에설정 한대요~~ 모르겠음
//
//        //hibernate JPA공급자 설정
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//        //hibernate속성추가
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
//        properties.setProperty("hibernate.hbm2ddl.auto", "update");
//        properties.setProperty("hibernate.show_sql", "true");
//
//        //jta트랜잭션 플랫폼설정
////        properties.setProperty("hibernate.transaction.jta.platform",
////                "com.atomikos.icatch.jta.hibernate4.AtomikosJtaPlatform");
//
//        em.setJpaProperties(properties);
//
//        return em;
//    }

//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
//
//        return new JpaTransactionManager(entityManagerFactory);
//    }

//    @Bean
//    public PlatformTransactionManager transactionManager(){
//        return new JtaTransactionManager();  //jta트랜젝션 관리자 사용
//    }

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername("yeonjinKn.n@gmail.com");
        javaMailSender.setPassword("hbuu ovsk nect ntoo");
        javaMailSender.setPort(587);
        javaMailSender.setJavaMailProperties(getMailProperties());
        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties=new Properties();
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true"); //starttls활성화
        properties.setProperty("mail.debug","true");
        properties.setProperty("mail.smtp.ssl.trust","smtp.gmail.com");
        //ssl사용할경우 starttls와 함께 사용하지 않으므로 주석처리하였움
//        properties.setProperty("mail.smtp.ssl.enable","true");
        return properties;

    }



}

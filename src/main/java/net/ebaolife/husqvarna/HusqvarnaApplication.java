package net.ebaolife.husqvarna;


import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableJpaRepositories(basePackages = "net.ebaolife.husqvarna.framework", repositoryBaseClass = DaoImpl.class)
@Configuration
@SpringBootApplication
//@EnableJpaRepositories(basePackages = "net.ebaolife.husqvarna.framework")
//@EnableJpaRepositories(repositoryBaseClass = DaoImpl.class)
@EntityScan("net.ebaolife.husqvarna.dao.entity")
@EnableTransactionManagement//开启事务控制
@EnableCaching
@ServletComponentScan
//@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
public class HusqvarnaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(HusqvarnaApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(Application.class);
    }

    /*@Autowired(required=true)
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public SessionFactory getSessionFactory() {
        if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return entityManagerFactory.unwrap(SessionFactory.class);
    }*/



}

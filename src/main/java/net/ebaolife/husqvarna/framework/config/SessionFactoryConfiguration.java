package net.ebaolife.husqvarna.framework.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * @ Author     ：Wu JianTao
 * @ Date       ：Created in 5:07 PM 2019/12/23
 * @ Description：${description}
 * @ Modified By：
 */


@Configuration
@PropertySource(value = { "classpath:application.properties" })
@EnableTransactionManagement

public class SessionFactoryConfiguration {


    @Autowired
    private Environment environment;
    //session factory
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "net.ebaolife.husqvarna.framework.dao.entity","net.ebaolife.husqvarna.sales.entity" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
    // 数据源配置
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));
        return dataSource;
    }
    //获取hibernate配置
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return properties;
    }
    // 事务管理
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sf) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sf);
        return txManager;
    }


    /*  @Bean
      public HibernateJpaSessionFactoryBean sessionFactory() {
          return new HibernateJpaSessionFactoryBean();
      }
  */

/*

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    //@Primary
    @Bean
    public Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    //@Bean
    public SessionFactory getSessionFactory() {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }
*/

    /*@Bean
    public Session getSession(){
        return this.entityManager.unwrap(Session.class);
    }*/
    //@Autowired
    //private EntityManagerFactory entityManagerFactory;


    /*@Autowired
    private EntityManagerFactory entityManagerFactory;


    @Bean
    public SessionFactory getSessionFactory() {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }*/

    //@Autowired
    //@PersistenceContext
    //private EntityManagerFactory entityManagerFactory;

//    @PersistenceContext
//    EntityManager entityManager;


    /*@Bean
    public SessionFactory sessionFactory(@Qualifier("entityManagerFactory") EntityManagerFactory emf){
        return emf.unwrap(SessionFactory.class);
    }*/

    //@Resource
    // @Autowired
    /*@PersistenceContext
    EntityManager entityManager;

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }*



    /*@Bean
    private Session getSession() {
        return entityManager.unwrap(SessionFactory.class).getCurrentSession();
    }*/


   /* @Bean
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){
        return hemf.getSessionFactory();
    }*/

    /*@Bean
    public SessionFactory getSessionFactory() {
        if (entityManager.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return entityManager.unwrap(SessionFactory.class);
    }*/


   /* @PersistenceContext
    EntityManager entityManager;

    @Bean
    public SessionFactory getSession() {
        return entityManager.unwrap(SessionFactory.class);
    }*/

/*


    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[]{"net.ebaolife","net.ebaolife.husqvarna"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hiberante.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return properties;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }
*/


}

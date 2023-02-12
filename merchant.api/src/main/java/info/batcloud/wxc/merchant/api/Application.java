package info.batcloud.wxc.merchant.api;

import com.zaxxer.hikari.HikariDataSource;
import info.batcloud.wxc.core.CommonConfig;
import info.batcloud.wxc.merchant.api.comsumers.OrderConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.inject.Inject;
import javax.sql.DataSource;

@SpringBootApplication
@ServletComponentScan({"com.ctospace.archit.servlet", "info.batcloud.wxc"})
@EnableCaching
@ComponentScan({"info.batcloud.wxc"})
@EnableJpaRepositories(basePackages = {"info.batcloud.wxc.core.repository"})
@EntityScan(basePackages = {"info.batcloud.wxc.core.entity"})
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableKafka
public class Application extends CommonConfig {

    @Inject
    private KafkaTemplate<String, String> template;

    @Bean
    @ConfigurationProperties(prefix = "datasource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        return dataSource;
    }

    @Override
    @Bean
    public ReloadableResourceBundleMessageSource resourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = super.resourceBundleMessageSource();
        messageSource.addBasenames("classpath:messages/api/exceptions");
        return messageSource;
    }

//    @Bean
//    public CharacterEncodingFilter getCharacterEncodingFilter() {
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceEncoding(true);
//        return characterEncodingFilter;
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

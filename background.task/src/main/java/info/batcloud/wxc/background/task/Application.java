package info.batcloud.wxc.background.task;

import com.zaxxer.hikari.HikariDataSource;
import info.batcloud.wxc.core.CommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;

@SpringBootApplication
@ServletComponentScan({"com.ctospace.archit.servlet", "info.batcloud.wxc"})
@EnableCaching
@ComponentScan({"info.batcloud.wxc"})
@EnableJpaRepositories(basePackages = "info.batcloud.wxc")
@EnableTransactionManagement
@EntityScan(basePackages = "info.batcloud.wxc")
@Configuration
@EnableScheduling
public class Application extends CommonConfig {

    @Bean
    @ConfigurationProperties(prefix = "datasource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        return dataSource;
    }

    @Bean
    public CharacterEncodingFilter getCharacterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}

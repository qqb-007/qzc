package info.batcloud.wxc.core;

import com.sankuai.meituan.banma.PeisongClient;
import com.zaxxer.hikari.HikariDataSource;
import dada.com.DaDaClient;
import info.batcloud.wxc.core.config.*;
import info.batcloud.wxc.core.context.StaticContext;
import info.batcloud.wxc.core.weixin.WxaConfig;
import jd.sdk.JingdongClient;
import jd.sdk.JingdongzClient;
import me.ele.sdk.up.EleClient;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import shansong.ShanSongClient;
import shunfeng.ShunfengClent;
import wante.sdk.up.WanteClient;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableAutoConfiguration
@ServletComponentScan({"com.ctospace.archit.servlet", "info.batcloud.wxc"})
@EnableCaching
@ComponentScan({"info.batcloud.wxc"})
@EnableTransactionManagement
@EntityScan(basePackages = "info.batcloud.wxc.core.entity")
@EnableJpaRepositories(basePackages = "info.batcloud.wxc.core.repository")
@EnableScheduling
@EnableAspectJAutoProxy
@EnableKafka
public class CommonConfig {

    @Bean
    @ConfigurationProperties(prefix = "datasource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        return dataSource;
    }

    @Bean
    @ConfigurationProperties("jwt")
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }

    @Bean
    @ConfigurationProperties("weixin.wxa")
    public WxaConfig wxaConfig() {
        return new WxaConfig();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConfigurationProperties("alipay")
    public AlipayConfig alipayConfig() {
        return new AlipayConfig();
    }

    @Bean
    @ConfigurationProperties("shunfengpeisong")
    public ShunfengPeisongApp shunfengPeisongApp() {
        return new ShunfengPeisongApp();
    }

    @Bean
    @ConfigurationProperties("meituanpeisong")
    public MeituanPeisongApp meituanPeisongApp() {
        return new MeituanPeisongApp();
    }

    @Bean
    @ConfigurationProperties("ele.app")
    public EleApp eleApp() {
        return new EleApp();
    }

    @Bean
    @ConfigurationProperties("clbm")
    public ClbmApp clbmApp() {
        return new ClbmApp();
    }

    @Bean
    @ConfigurationProperties("jd")
    public JingdongApp jingdongApp() {
        return new JingdongApp();
    }

    @Bean
    @ConfigurationProperties("jdz")
    public JingdongzApp jingdongzApp() {
        return new JingdongzApp();
    }

    @Bean
    public EleClient eleClient(EleApp app) {
        EleClient eleClient = new EleClient();
        eleClient.setSecret(app.getSecret());
        eleClient.setSource(app.getSource());
        eleClient.setVersion(app.getVersion());
        return eleClient;
    }

    @Bean
    public DaDaClient daDaClientClient() {
        DaDaClient client = new DaDaClient();
        return client;
    }

    @Bean
    public WanteClient wanteClient() {
        WanteClient wanteClient = new WanteClient();
        return wanteClient;
    }

    @Bean
    public ShanSongClient shanSongClient() {
        ShanSongClient client = new ShanSongClient();
        return client;
    }

    @Bean
    public PeisongClient peisongClient(MeituanPeisongApp app) {
        PeisongClient peisongClient = new PeisongClient();
        peisongClient.setSecret(app.getSecret());
        peisongClient.setAppKey(app.getAppKey());
        return peisongClient;
    }

    @Bean
    public JingdongClient jingdongClient(JingdongApp app) {
        JingdongClient jingdongClient = new JingdongClient();
        jingdongClient.setAppSecret(app.getAppSecret());
        jingdongClient.setAppKey(app.getAppKey());
        jingdongClient.setToken(app.getToken());
        jingdongClient.setV(app.getV());
        return jingdongClient;
    }

    @Bean
    public JingdongzClient jingdongzClient(JingdongzApp zapp) {
        JingdongzClient jingdongzClient = new JingdongzClient();
        jingdongzClient.setAppSecret(zapp.getAppSecret());
        jingdongzClient.setAppKey(zapp.getAppKey());
        jingdongzClient.setToken(zapp.getToken());
        jingdongzClient.setV(zapp.getV());
        return jingdongzClient;
    }

    @Bean
    public ShunfengClent shunfengClent(ShunfengPeisongApp app) {
        ShunfengClent shunfengClent = new ShunfengClent();
        shunfengClent.setDevId(app.getDevId());
        shunfengClent.setDevKey(app.getDevKey());
        return shunfengClent;
    }

    //i18n
    @Bean
    public ReloadableResourceBundleMessageSource resourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages/enums", "classpath:messages/exceptions", "classpath:messages/messages");
        messageSource.setDefaultEncoding("utf8");
        messageSource.setAlwaysUseMessageFormat(true);
        StaticContext.messageSource = messageSource;
        return messageSource;
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(3, 500, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    }

}

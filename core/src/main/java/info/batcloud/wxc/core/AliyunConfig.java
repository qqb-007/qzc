package info.batcloud.wxc.core;

import com.aliyun.oss.OSSClient;
import info.batcloud.wxc.core.aliyun.AccessKey;
import info.batcloud.wxc.core.aliyun.OSSConfig;
import info.batcloud.wxc.core.aliyun.OssImageConfig;
import info.batcloud.wxc.core.aliyun.Ram;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AliyunConfig {

    @Bean
    @ConfigurationProperties("aliyun.oss")
    public OSSConfig OSSConfig() {
        return OSSConfig.getInstance();
    }

    @Bean
    @ConfigurationProperties("aliyun.ram")
    public Ram ram() {
        return new Ram();
    }

    @Bean
    @ConfigurationProperties("aliyun.accessKey")
    public AccessKey accessKey() {
        return new AccessKey();
    }

    @Bean
    @ConfigurationProperties("aliyun.ossImage")
    public OssImageConfig ossImageConfig() {
        return OssImageConfig.getInstance();
    }


    //ossclient
    @Bean
    @Scope("prototype")
    public OSSClient ossClient() {
        OSSConfig config = OSSConfig();
        AccessKey accessKey = ram().getUsers().getAliyunManager().getAccessKey();
        OSSClient client = new OSSClient(config.getEndpoint(), accessKey.getId(), accessKey.getSecret());
        return client;
    }

}

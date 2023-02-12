package info.batcloud.wxc.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/kafka/test")
public class KafkaTestController {

    @Inject
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping
    public Object execute(@RequestParam String topic, @RequestParam String value, @RequestParam(required = false) String key, @RequestParam(required = false) Integer times) {
        if (times == null) {
            times = 1;
        }
        String[] values = value.split(",");
        while(times-- > 0) {
            if (values.length == 1) {
                if (StringUtils.isNotBlank(key)) {
                    kafkaTemplate.send(topic, key, value);
                } else {
                    kafkaTemplate.send(topic, value);
                }
            } else {
                int start = Integer.valueOf(values[0]);
                int end = Integer.valueOf(values[1]);
                while(start++ < end) {
                    if (StringUtils.isNotBlank(key)) {
                        kafkaTemplate.send(topic, key, start + "");
                    } else {
                        kafkaTemplate.send(topic, start + "");
                    }
                }
            }
        }
        return true;
    }

}

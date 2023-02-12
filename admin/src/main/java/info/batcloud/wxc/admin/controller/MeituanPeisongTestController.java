package info.batcloud.wxc.admin.controller;


import com.sankuai.meituan.banma.PeisongClient;
import com.sankuai.meituan.banma.request.TestRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RequestMapping("/api/mt-peisong/test")
@RestController
public class MeituanPeisongTestController {

    private static final Logger logger = LoggerFactory.getLogger(MeituanPeisongTestController.class);

    @Inject
    private PeisongClient peisongClient;

    @PostMapping
    public Object execute(TestRequest testRequest) {
        return peisongClient.execute(testRequest);
    }


}

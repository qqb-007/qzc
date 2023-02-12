package info.batcloud.wxc.core.service.impl;

import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import info.batcloud.wxc.core.entity.Member;
import info.batcloud.wxc.core.service.MeituanMemberService;
import info.batcloud.wxc.core.service.MeituanWaimaiService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class MeituanMemberServiceImpl implements MeituanMemberService {

    @Inject
    private MeituanWaimaiService meituanWaimaiService;


    @Override
    public List<Member> memberList() {
        try {
            Long now = System.currentTimeMillis();
            Long startTime = now - 30 * 60 * 60 * 24 * 1000;
            String result  = APIFactory.getMemberApi().memberList(meituanWaimaiService.getSystemParam(), "7105492", startTime.intValue(), now.intValue(), 0, 200);
            System.out.println(result);
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.core.service.MeituanMemberService;
import info.batcloud.wxc.core.service.MeituanWaimaiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/meituan/member")
public class MeituanMemberController {

    @Inject
    private MeituanMemberService meituanMemberService;

    @GetMapping("/member-list")
    public Object memberList() {
        meituanMemberService.memberList();
        return true;
    }

}

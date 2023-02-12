package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.core.service.OrderService;
import jd.sdk.JingdongClient;
import jd.sdk.request.GetTigReq;
import jd.sdk.response.GetTigRes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/jingdong-waimai")
@RestController
public class JingdongWaimaiController {
    @Inject
    private JingdongClient jingdongClient;
    @Inject
    private OrderService orderService;

    @GetMapping("/retail/getJdTagIds")
    public Object getJdTagIds() {
        //到家类目为水果/蔬菜、水产、肉品，散装商品类目的商品UPC非必填，其他类目必填(散装商品不在经营类目中)
        List<Long> tagId = new ArrayList<>();
        List<JdCategoryVo> tigList = new ArrayList<>();
        tagId.add(20307l);
        tagId.add(20292l);
        tagId.add(20248l);
        for (Long aLong : tagId) {
            List<JdCategoryVo> child = getChild(aLong);
            tigList.addAll(child);
        }
        for (JdCategoryVo vo : tigList) {
            List<JdCategoryVo> child = getChild(vo.getId());
            vo.getChildren().addAll(child);
        }
        return tigList;
    }
    private List<JdCategoryVo> getChild(Long id){
        List<JdCategoryVo> list = new ArrayList<>();
        GetTigReq req = new GetTigReq();
        req.setFields(new ArrayList<>());
        req.getFields().add("ID");
        req.getFields().add("PID");
        req.getFields().add("CATEGORY_NAME");
        req.getFields().add("CATEGORY_LEVEL");
        req.getFields().add("CATEGORY_STATUS");
        req.setId(id);
        GetTigRes res = jingdongClient.request(req);
        List<GetTigRes.CategoryInfo> result = res.getData().getResult();
        for (GetTigRes.CategoryInfo info : result) {
            if (info.getCategoryStatus() != 1){
                continue;
            }
            JdCategoryVo vo = new JdCategoryVo();
            vo.setCategoryName(info.getCategoryName());
            vo.setCategoryLevel(info.getCategoryLevel());
            vo.setCategoryStatus(info.getCategoryStatus());
            vo.setId(info.getId());
            vo.setPid(info.getPid());
            vo.setChildren(new ArrayList<>());
            list.add(vo);
        }
        return list;
    }
    public static class JdCategoryVo{
        private Long id;
        private Long pid;
        private String categoryName;
        private Integer categoryLevel;
        private Integer categoryStatus;
        private List<JdCategoryVo> children;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getPid() {
            return pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public Integer getCategoryLevel() {
            return categoryLevel;
        }

        public void setCategoryLevel(Integer categoryLevel) {
            this.categoryLevel = categoryLevel;
        }

        public Integer getCategoryStatus() {
            return categoryStatus;
        }

        public void setCategoryStatus(Integer categoryStatus) {
            this.categoryStatus = categoryStatus;
        }

        public List<JdCategoryVo> getChildren() {
            return children;
        }

        public void setChildren(List<JdCategoryVo> children) {
            this.children = children;
        }
    }
}

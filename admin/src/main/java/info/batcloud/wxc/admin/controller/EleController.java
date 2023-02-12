package info.batcloud.wxc.admin.controller;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.helper.PagingHelper;
import me.ele.sdk.up.EleClient;
import me.ele.sdk.up.request.BrandListRequest;
import me.ele.sdk.up.request.CategoryListRequest;
import me.ele.sdk.up.response.BrandListResponse;
import me.ele.sdk.up.response.CategoryListResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ele")
public class EleController {

    @Inject
    private EleClient eleClient;

    @GetMapping("/brand/list")
    public Object brandList(@RequestParam String keyword, @RequestParam(required = false, defaultValue = "1") int page) {
        BrandListRequest req = new BrandListRequest();
        req.setKeyword(keyword);
        req.setPage(page);
        BrandListResponse res = eleClient.request(req);
        Paging<BrandListResponse.Brand> paging = PagingHelper.of(res.getData().getDetail(), res.getData().getCount(), res.getData().getPageNum(), res.getData().getPageSize());
        return paging;
    }

    @GetMapping("/category/list")
    public Object categoryList() {
        CategoryListRequest request = new CategoryListRequest();
        request.setDepth(1);
        request.setParent_id(0l);
        CategoryListResponse response1 = eleClient.request(request);
        List<CategoryListResponse.Data> data1 = response1.getData();
        List<CategoryListVo> listVos1 = new ArrayList<>();
        for (CategoryListResponse.Data datum : data1) {
            CategoryListVo vo1 = new CategoryListVo();
            vo1.setChildenList(new ArrayList<>());
            BeanUtils.copyProperties(datum, vo1);
            vo1.setLevel(0);
            request.setDepth(2);
            request.setParent_id(Long.valueOf(datum.getCat_id()));
            CategoryListResponse response2 = eleClient.request(request);
            List<CategoryListResponse.Data> data2 = response2.getData();
            for (CategoryListResponse.Data data : data2) {
                CategoryListVo vo2 = new CategoryListVo();
                BeanUtils.copyProperties(data, vo2);
                vo2.setLevel(1);
                vo2.setChildenList(new ArrayList<>());
                request.setDepth(3);
                request.setParent_id(Long.valueOf(data.getCat_id()));
                CategoryListResponse response3 = eleClient.request(request);
                List<CategoryListResponse.Data> data3 = response3.getData();
                for (CategoryListResponse.Data data4 : data3) {
                    CategoryListVo vo3 = new CategoryListVo();
                    BeanUtils.copyProperties(data4, vo3);
                    vo3.setLevel(2);
                    vo2.getChildenList().add(vo3);
                }
                vo1.getChildenList().add(vo2);
            }
            listVos1.add(vo1);
        }
        return listVos1;
    }

    public static class CategoryListVo extends CategoryListResponse.Data {

        private int level;
        private List<CategoryListVo> childenList;

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public List<CategoryListVo> getChildenList() {
            return childenList;
        }

        public void setChildenList(List<CategoryListVo> childenList) {
            this.childenList = childenList;
        }
    }

}

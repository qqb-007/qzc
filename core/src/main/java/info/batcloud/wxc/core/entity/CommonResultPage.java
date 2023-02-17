package info.batcloud.wxc.core.entity;

import lombok.Data;

/**
 * @ClassName: CommonResultPage
 * @Description:
 * @Author V
 * @Date 15/2/2023
 * @Version 1.0
 */
@Data
public  class CommonResultPage<T> {
    private String msg;
    private Integer code;
    private T data;
    private Integer page;
    private Long total;
    private Integer pageSize;
    private Integer maxPage;
    private boolean hasNext;
    public CommonResultPage(T data, Integer page,Long total,Integer pageSize,Integer maxPage,boolean hasNext){
        this.data = data;
        this.msg = "数据获取成功";
        this.code=200;
        this.page = page;
        this.total = total;
        this.pageSize = pageSize;
        this.maxPage = maxPage;
        this.hasNext = hasNext;
    }
}

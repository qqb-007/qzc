package info.batcloud.wxc.core.helper;

import com.ctospace.archit.common.pagination.Paging;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PagingHelper {

    public static <T> Paging<T> of(Page<T> page, int pageNumber, int pageSize) {
        Paging<T> paging = new Paging<>();
        paging.setResults(page.getContent());
        paging.setTotal(Long.valueOf(page.getTotalElements()).intValue());
        paging.setPage(pageNumber);
        paging.setPageSize(pageSize);
        return paging;
    }

    public static <T, R> Paging<T> of(Page<R> page, Function<R, T> fun, int pageNumber, int pageSize) {
        Paging<T> paging = new Paging<>();
        paging.setResults(page.getContent().stream().map(o -> fun.apply(o)).collect(Collectors.toList()));
        paging.setTotal(Long.valueOf(page.getTotalElements()).intValue());
        paging.setPage(pageNumber);
        paging.setPageSize(pageSize);
        return paging;
    }

    public static <T> Paging<T> of(List<T> list, int total, int pageNumber, int pageSize) {
        Paging<T> paging = new Paging<>();
        paging.setResults(list);
        paging.setTotal(total);
        paging.setPage(pageNumber);
        paging.setPageSize(pageSize);
        return paging;
    }

    public static <T, R> Paging<T> of(List<R> list, Function<R, T> fun, int total, int pageNumber, int pageSize) {
        Paging<T> paging = new Paging<>();
        paging.setResults(list.stream().map(o -> fun.apply(o)).collect(Collectors.toList()));
        paging.setTotal(total);
        paging.setPage(pageNumber);
        paging.setPageSize(pageSize);
        return paging;
    }

    public static <T> Paging<T> of(List<T> list, long total, int pageNumber, int pageSize) {
        return of(list, Long.valueOf(total).intValue(), pageNumber, pageSize);
    }

}

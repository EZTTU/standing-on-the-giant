package org.cuit.echo.seon.commonweb.utils;

import lombok.Data;
import org.cuit.echo.seon.commonweb.response.PageQuery;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 * 分页模型
 *
 * @author wuwen
 * @since 2023/11/25
 */
@Data
public class PageUtils<T> {

    /**
     * 页数
     */
    private Long pn;

    /**
     * 页大小
     */
    @NotNull
    private Long ps;

    /**
     * 偏移量
     */
    private Long offset;

    /**
     * 总页数
     */
    @NotNull
    private Long total;

    /**
     * 列表数据
     */
    @NotNull
    private List<T> list = Collections.emptyList();

    public PageUtils(PageQuery query) {
        this.ps = query.getPs();
        if (query.getOffset() != null) {
            this.offset = query.getOffset();
        }
        this.pn = query.getPn();
        this.ps = query.getPs();
        this.offset = query.getOffset();
    }

    public PageUtils(PageQuery query, long total) {
        this(query);
        this.total = total;
    }

    public PageUtils(PageQuery query, long total, List<T> list) {
        this(query, total);
        this.setList(list);
    }

    public void setList(List<T> list) {
        if (list != null && !list.isEmpty()) {
            this.list = list;
        }
    }

    /**
     * 根据 total 判断当前分页信息是否有数据
     *
     * @return 若 total 为 null，则返回 null；若当前分页有数据返回 true，否则返回 false
     */
    public Boolean hasPage() {
        if (total == null) {
            return null;
        }
        return this.getOffset() < total;
    }
}

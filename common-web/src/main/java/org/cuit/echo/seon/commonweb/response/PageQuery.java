package org.cuit.echo.seon.commonweb.response;

import lombok.Data;
import lombok.Getter;

/**
 * @author Seon
 * @version 1.0
 * @since 2024-10-15
 */
@Data
public class PageQuery {

    /**
     * 页数
     */
    @Getter
    private Long pn = 1L;

    /**
     * 页大小
     */
    @Getter
    private Long ps = 10L;

    /**
     * 偏移量
     */
    private Long offset;

    public void setPn(Long pn) {
        if (pn != null && pn >= 1) {
            this.pn = pn;
        }
    }

    public void setPs(Long ps) {
        if (ps != null && ps >= 1 && ps <= 50) {
            this.ps = ps;
        }
    }

    /**
     * 强制修改页大小
     *
     * @param ps 页大小
     */
    public void setPsForce(Long ps) {
        this.ps = ps;
    }

    public void setOffset(Long offset) {
        if (offset != null && offset >= 0) {
            this.offset = offset;
        }
    }

    public Long getOffset() {
        return this.offset != null ? this.offset : (this.pn - 1) * this.ps;
    }
}

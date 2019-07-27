package com.stodger.vo;

import java.util.List;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-07-04 11:16
 */
public class PageInfoVo<T> {
    private Integer totalPage;
    private Integer prePage;
    private Integer nextPage;
    private boolean hasPrePage;
    private boolean hasNextPage;
    private T list;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPrePage() {
        return prePage;
    }

    public void setPrePage(Integer prePage) {
        this.prePage = prePage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageInfoVo{" +
                "totalPage=" + totalPage +
                ", prePage=" + prePage +
                ", nextPage=" + nextPage +
                ", hasPrePage=" + hasPrePage +
                ", hasNextPage=" + hasNextPage +
                ", list=" + list +
                '}';
    }
}

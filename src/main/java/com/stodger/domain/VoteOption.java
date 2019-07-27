package com.stodger.domain;

import com.stodger.util.IdUtil;

import java.util.List;

/**
 * 投票主题选项
 * @author Stodger
 * @version V1.0
 * @date 2019-06-30 17:28
 */
public class VoteOption {
    private Integer id;
    private String option;
    private Integer subjectId;
    private Integer order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "VoteOption{" +
                "id=" + id +
                ", option='" + option + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", order=" + order +
                '}';
    }
}

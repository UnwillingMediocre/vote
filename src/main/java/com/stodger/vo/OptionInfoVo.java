package com.stodger.vo;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-07-03 11:49
 */
public class OptionInfoVo {
    private Integer optionId;
    private String content;
    private Integer count;
    private Integer isOption;

    public OptionInfoVo(){
        this.count = 0;
    }

    public Integer getOptionId() {
        return optionId;
    }
    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getIsOption() {
        return isOption;
    }

    public void setIsOption(Integer isOption) {
        this.isOption = isOption;
    }

    @Override
    public String toString() {
        return "OptionInfoVo{" +
                "optionId=" + optionId +
                ", content='" + content + '\'' +
                ", count=" + count +
                ", isOption=" + isOption +
                '}';
    }
}

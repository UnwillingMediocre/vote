package com.stodger.vo;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-07-03 11:27
 */
public class VoteInfoVo {
    private Integer subjectId;
    private String title;
    private String username;
    private String description;
    private Integer totalVote;
    private String startTime;
    private String endTime;
    private String type;
    private List<OptionInfoVo> optionInfoVoList;


    public VoteInfoVo(){
        this.totalVote = 0;
        this.optionInfoVoList = new ArrayList<>();
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(Integer totalVote) {
        this.totalVote = totalVote;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<OptionInfoVo> getOptionInfoVoList() {
        return optionInfoVoList;
    }

    public void setOptionInfoVoList(List<OptionInfoVo> optionInfoVoList) {
        this.optionInfoVoList = optionInfoVoList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "VoteInfoVo{" +
                "subjectId=" + subjectId +
                ", title='" + title + '\'' +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", totalVote=" + totalVote +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", type='" + type + '\'' +
                ", optionInfoVoList=" + optionInfoVoList +
                '}';
    }
}

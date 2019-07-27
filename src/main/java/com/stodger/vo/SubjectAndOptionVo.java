package com.stodger.vo;

import com.stodger.domain.VoteOption;
import com.stodger.domain.VoteSubject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-07-01 11:04
 */
public class SubjectAndOptionVo {
    private VoteSubject voteSubject;
    private List<VoteOption> voteOptionList;

    public SubjectAndOptionVo(){
        voteSubject = new VoteSubject();
        voteOptionList = new ArrayList<>();
    }

    public VoteSubject getVoteSubject() {
        return voteSubject;
    }

    public void setVoteSubject(VoteSubject voteSubject) {
        this.voteSubject = voteSubject;
    }

    public List<VoteOption> getVoteOptionList() {
        return voteOptionList;
    }

    public void setVoteOptionList(List<VoteOption> voteOptionList) {
        this.voteOptionList = voteOptionList;
    }

    @Override
    public String toString() {
        return "SubjectAndOptionVo{" +
                "voteSubject=" + voteSubject +
                ", voteOptionList=" + voteOptionList +
                '}';
    }
}

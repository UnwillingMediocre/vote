package com.stodger.service;

import com.stodger.common.ServerResponse;
import com.stodger.domain.VoteSubject;
import com.stodger.vo.PageInfoVo;
import com.stodger.vo.SubjectAndOptionVo;
import com.stodger.vo.VoteInfoVo;

import java.util.List;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-07-03 09:57
 */
public interface VoteService {

    /**
     * 保存投票信息
     * @param title 题目
     * @param description 描述
     * @param options 选项
     * @param type 类型
     * @param endTime 截止时间
     * @param userId 用户Id
     * @return boolean
     */
    boolean saveVote(String title, String description, String [] options, Integer type, String endTime, String userId);

    /**
     * 根据用户Id查询
     * @param userId 用户Id
     * @param pageNum 当前页
     * @param count 条数
     * @return List<VoteSubject>
     */
    PageInfoVo findVoteSubjectByUserId(String userId, Integer pageNum, int count);


    /**
     * 根据主题Id查询
     * @param subjectId 主题Id
     * @return VoteInfoVo
     */
    VoteInfoVo findVoteInfoBySubjectId(int subjectId);

    /**
     * 根据主题Id删除
     * @param subjectId 主题Id
     * @return boolean
     */
    boolean deleteVoteBySubjectId(Integer subjectId);

    /**
     * 根据主题Id查找
     * @param subjectId 主题Id
     * @return SubjectAndOptionVo
     */
    SubjectAndOptionVo findVoteBySubjectId(int subjectId);

    /**
     * 根据主题Id查询
     * @param subjectId 主题Id
     * @return boolean
     */
    boolean findItemBySubjectId(int subjectId);

    /**
     * 更新投票
     * @param subjectId  主题名
     * @param title 题目
     * @param description 描述
     * @param optionIds 选项Id
     * @param options 选项内容
     * @param type 类型
     * @param endTime 截止时间
     * @return boolean
     */
    boolean updateVote(String subjectId, String title, String description, String[] optionIds, String[] options, Integer type, String endTime);

    /**
     * 查询所有投票
     * @param pageNum 当前页
     * @param count 一页显示多少条数据
     * @return PageInfoVo
     */
    PageInfoVo findVoteAll(int pageNum, int count);

    /**
     * 投票
     * @param options 选项信息
     * @param subjectId 主题Id
     * @param userId 用户Id
     */
    void doVote(String[] options, String subjectId, String userId);

    /**
     *
     * 根据主题Id和用户ID查询
     * @param subjectId 主题Id
     * @param userId 用户Id
     * @return VoteInfoVo
     */
    ServerResponse<VoteInfoVo> findVoteInfoBySubjectIdAndUserId(int subjectId, String userId);

    /**
     * 根据关键词搜索
     * @param pageNum 当前页数
     * @param keyWord 关键词
     * @param count 条数
     * @return PageInfoVo
     */
    PageInfoVo searchVote(int pageNum, String keyWord, int count);

    /**
     * 根据用户名查找
     * @param pageNum 当前页
     * @param userId 用户Id
     * @param count 条数
     * @return PageInfoVo
     */
    PageInfoVo findMyVoteByUserId(Integer pageNum, String userId, Integer count);

    /**
     * 所有投票
     * @param pageNum 当前页
     * @param count 条数
     * @return List<VoteSubject>
     */
    PageInfoVo findAll(Integer pageNum, int count);

}

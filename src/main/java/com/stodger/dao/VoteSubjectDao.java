package com.stodger.dao;

import com.stodger.domain.VoteOption;
import com.stodger.domain.VoteSubject;
import com.stodger.vo.SubjectAndOptionVo;
import com.stodger.vo.VoteInfoVo;

import java.util.Date;
import java.util.List;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-06-30 17:53
 */
public interface VoteSubjectDao {

    /**
     * 保存投票主题
     *
     * @param title       题目
     * @param description 描述
     * @param type        类型
     * @param options     选项
     * @param startTime   开始时间
     * @param endTime     截止时间
     * @param status      状态
     * @param order       排序
     * @param userId      用户Id
     * @return boolean
     */
    boolean saveSubjectAndOption(String title, String description, String[] options, Integer type, Date startTime, Date endTime, Integer status, Integer order, String userId);

    /**
     * 更新投票主题
     *
     * @param voteSubject    主题信息
     * @param voteOptionList 选项信息
     * @return boolean
     */
    boolean updateSubjectAndOption(VoteSubject voteSubject, List<VoteOption> voteOptionList);

    /**
     * 根据主题Id查询
     *
     * @param subjectId 主题Id
     * @return SubjectAndOption
     */
    SubjectAndOptionVo findSubjectAndOptionVoBySubjectId(Integer subjectId);

    /**
     * 查询所有主题及选项
     *
     * @return List<SubjectAndOptionVo>
     */
    List<SubjectAndOptionVo> findSubjectAndOptionAll();

    /**
     * 根据用户Id查询
     *
     * @param userId 用户Id
     * @return List<VoteSubject>
     */
    List<VoteSubject> findVoteSubjectByUserId(String userId, Integer pageNum, int count);

    /**
     * 根据主题Id查询
     *
     * @param subjectId 主题Id
     * @return VoteInfoVo
     */
    VoteInfoVo findVoteInfoBySubjectId(int subjectId);

    /**
     * 根据主题Id删除投票
     *
     * @param subjectId 主题Id
     * @return boolean
     */
    boolean deleteVoteBySubjectId(Integer subjectId);

    /**
     * 根据主题Id操作
     *
     * @param subjectId 主题Id
     * @return boolean
     */
    boolean findItemBySubjectId(int subjectId);

    /**
     * 查询总条数
     *
     * @return int
     */
    int findVoteAllCount();

    /**
     * 查询所有
     *
     * @return List<VoteInfoVo>
     */
    List<VoteInfoVo> findVoteAll(int pageNum, int count);

    /**
     * 保存投票结果
     *
     * @param optionIdList 选项Id
     * @param subjectId    主题Id
     * @param userId       用户Id
     */
    void saveItem(List<Integer> optionIdList, Integer subjectId, String userId);

    /**
     * 根据用户Id 选项Id 主题Id查询
     *
     * @param optionId  选项Id
     * @param subjectId 主题Id
     * @param userId    用户Id
     * @return boolean
     */
    boolean findItemById(Integer optionId, int subjectId, String userId);

    /**
     * 根据关键词搜索
     *
     * @param keyWord 关键词
     * @return int
     */
    int findVoteTotalByKeyWord(String keyWord);

    /**
     * 根据关键词搜索
     *
     * @param pageNum 当前页
     * @param count   条数
     * @param keyWord 关键词
     * @return List<VoteInfoVo>
     */
    List<VoteInfoVo> findVoteByKeyWord(int pageNum, int count, String keyWord);

    /**
     * 查找我的参与投票总数
     *
     * @param userId 用户Id
     * @return int
     */
    int findMyVoteCount(String userId);

    /**
     * 查询我的参与投票
     *
     * @param pageNum 当前页
     * @param count   条数
     * @param userId  用户Id
     * @return List<VoteInfoVo>
     */
    List<VoteInfoVo> findMyVoteByUserId(int pageNum, Integer count, String userId);

    /**
     * 查询所有投票
     *
     * @param pageNum 当前页
     * @param count   条数
     * @return List<VoteSubject>
     */
    List<VoteSubject> findAll(int pageNum, int count);

    /**
     * 查询所有投票总数
     *
     * @return int
     */
    int findAllCount();

    /**
     * 根据用户Id查询投票主题总数
     *
     * @param userId 用户Id
     * @return int
     */
    int findVoteSubjectByUserIdCount(String userId);


    /**
     * 主题
     * @param subjectId 主题Id
     * @return SubjectAndOptionVo
     */
    /* SubjectAndOptionVo findVoteBySubjectId(int subjectId);*/

}

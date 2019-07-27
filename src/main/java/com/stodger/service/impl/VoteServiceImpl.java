package com.stodger.service.impl;

import com.stodger.common.Const;
import com.stodger.common.ServerResponse;
import com.stodger.dao.VoteSubjectDao;
import com.stodger.dao.VoteUserDao;
import com.stodger.dao.impl.VoteSubjectDaoImpl;
import com.stodger.dao.impl.VoteUserDaoImpl;
import com.stodger.domain.VoteOption;
import com.stodger.domain.VoteSubject;
import com.stodger.service.VoteService;
import com.stodger.vo.OptionInfoVo;
import com.stodger.vo.PageInfoVo;
import com.stodger.vo.SubjectAndOptionVo;
import com.stodger.vo.VoteInfoVo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-07-03 10:00
 */
public class VoteServiceImpl implements VoteService {
    private VoteSubjectDao voteSubjectDao = new VoteSubjectDaoImpl();

    @Override
    public boolean saveVote(String title, String description, String[] options, Integer type, String endTime, String userId) {
        Date date = new Date();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        return voteSubjectDao.saveSubjectAndOption(title, description, options, type, new Date(), date, 0, 1, userId);
    }

    @Override
    public PageInfoVo findVoteSubjectByUserId(String userId, Integer pageNum, int count) {
        PageInfoVo pageInfoVo = new PageInfoVo();
        int totalVote = voteSubjectDao.findVoteSubjectByUserIdCount(userId);
        int totalPage = totalVote / count;
        pageInfoVo = getPage(totalPage,totalVote,pageNum,count);
        List<VoteSubject> voteSubjectList = voteSubjectDao.findVoteSubjectByUserId(userId, (pageNum - 1) * count, count);
        pageInfoVo.setList(voteSubjectList);
        return pageInfoVo;
    }

    @Override
    public VoteInfoVo findVoteInfoBySubjectId(int subjectId) {
        return voteSubjectDao.findVoteInfoBySubjectId(subjectId);
    }

    @Override
    public boolean deleteVoteBySubjectId(Integer subjectId) {
        return voteSubjectDao.deleteVoteBySubjectId(subjectId);
    }

    @Override
    public SubjectAndOptionVo findVoteBySubjectId(int subjectId) {
        return voteSubjectDao.findSubjectAndOptionVoBySubjectId(subjectId);
    }

    @Override
    public boolean findItemBySubjectId(int subjectId) {
        return voteSubjectDao.findItemBySubjectId(subjectId);
    }

    @Override
    public boolean updateVote(String subjectId, String title, String description, String[] optionIds, String[] options, Integer type, String endTime) {
        VoteSubject voteSubject = new VoteSubject();
        voteSubject.setId(Integer.parseInt(subjectId));
        voteSubject.setTitle(title);
        voteSubject.setDescription(description);
        voteSubject.setType(type);
        Date date = new Date();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        voteSubject.setEndTime(date);
        List<VoteOption> voteOptionList = new ArrayList<>();
        for (int i = 0; i < options.length; i++) {
            VoteOption voteOption = new VoteOption();
            if (i < optionIds.length) {
                voteOption.setId(Integer.parseInt(optionIds[i]));
                voteOption.setOption(options[i]);
            } else {
                voteOption.setId(-1);
                voteOption.setOption(options[i]);
                voteOption.setOrder(1);
            }
            voteOptionList.add(voteOption);
        }
        return voteSubjectDao.updateSubjectAndOption(voteSubject, voteOptionList);
    }

    @Override
    public PageInfoVo findVoteAll(int pageNum, int count) {
        PageInfoVo pageInfoVo = new PageInfoVo();
        int totalVote = voteSubjectDao.findVoteAllCount();
        int totalPage = totalVote / count;
        pageInfoVo = getPage(totalPage,totalVote,pageNum,count);
        List<VoteInfoVo> voteInfoVoList = voteSubjectDao.findVoteAll((pageNum - 1) * count, count);
        pageInfoVo.setList(voteInfoVoList);
        return pageInfoVo;
    }

    private PageInfoVo getPage(int totalPage, int totalVote, int pageNum, int count) {
        PageInfoVo pageInfoVo = new PageInfoVo();
        pageInfoVo.setPrePage(pageNum);
        if (totalVote % count > 0) {
            totalPage += 1;
        }
        if (pageNum <= 1) {
            pageInfoVo.setHasPrePage(false);
            pageInfoVo.setPrePage(1);
        } else if (totalPage > 1) {
            pageInfoVo.setHasPrePage(true);
            pageInfoVo.setPrePage(pageNum - 1);
        }
        if (totalPage <= pageNum) {
            pageInfoVo.setHasNextPage(false);
            pageInfoVo.setNextPage(totalPage);
        } else if (totalPage >= 1) {
            pageInfoVo.setHasNextPage(true);
            pageInfoVo.setNextPage(pageNum + 1);
        }
        return pageInfoVo;
    }

    @Override
    public void doVote(String[] options, String subjectId, String userId) {
        List<Integer> optionIdList = new ArrayList<>();

        for (String str : options) {
            String[] temp = str.split("-");
            optionIdList.add(Integer.parseInt(temp[0]));
        }
        voteSubjectDao.saveItem(optionIdList, Integer.parseInt(subjectId), userId);
    }

    @Override
    public ServerResponse<VoteInfoVo> findVoteInfoBySubjectIdAndUserId(int subjectId, String userId) {
        ServerResponse<VoteInfoVo> voteInfoVoServerResponse = new ServerResponse<>();
        VoteInfoVo voteInfoVo = findVoteInfoBySubjectId(subjectId);
        int flag = 0;
        List<OptionInfoVo> optionInfoVoList = voteInfoVo.getOptionInfoVoList();
        List<OptionInfoVo> tempOptionInfoVoList = new ArrayList<>();
        for(OptionInfoVo optionInfoVo : optionInfoVoList){
            boolean result = voteSubjectDao.findItemById(optionInfoVo.getOptionId(),subjectId,userId);
            if(result){
                flag = 1;
                optionInfoVo.setIsOption(1);
            }else {
                optionInfoVo.setIsOption(0);
            }
            tempOptionInfoVoList.add(optionInfoVo);
        }
        voteInfoVo.setOptionInfoVoList(tempOptionInfoVoList);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = formatter.format(date);
        if(dateStr.compareTo(voteInfoVo.getEndTime()) < 0 && flag == 0){
            voteInfoVoServerResponse.setCode(Const.VoteUserEnum.VOTE_YES.getCode());
        }else{
            voteInfoVoServerResponse.setCode(Const.VoteUserEnum.VOTE_NO.getCode());
        }
        voteInfoVoServerResponse.setData(voteInfoVo);
        return voteInfoVoServerResponse;
    }

    @Override
    public PageInfoVo searchVote(int pageNum, String keyWord, int count) {
        PageInfoVo pageInfoVo = new PageInfoVo();
        int voteTotal = voteSubjectDao.findVoteTotalByKeyWord(keyWord);
        System.out.println(voteTotal + "=====>");
        int totalPage = voteTotal / count;
        pageInfoVo = getPage(totalPage,voteTotal,pageNum,count);
        List<VoteInfoVo> voteInfoVoList = voteSubjectDao.findVoteByKeyWord((pageNum - 1) * count, count,keyWord);
        pageInfoVo.setList(voteInfoVoList);
        return pageInfoVo;
    }

    @Override
    public PageInfoVo findMyVoteByUserId(Integer pageNum, String userId, Integer count) {
        PageInfoVo pageInfoVo = new PageInfoVo();
        int voteTotal = voteSubjectDao.findMyVoteCount(userId);
        int totalPage = voteTotal / count;
        pageInfoVo = getPage(totalPage, voteTotal, pageNum,count);
        List<VoteInfoVo> voteInfoVoList = voteSubjectDao.findMyVoteByUserId((pageNum - 1) * count, count, userId);
        pageInfoVo.setList(voteInfoVoList);
        return pageInfoVo;
    }

    @Override
    public PageInfoVo findAll(Integer pageNum, int count) {
        PageInfoVo pageInfoVo = new PageInfoVo();
        int voteTotal = voteSubjectDao.findAllCount();
        int totalPage = voteTotal / count;
        pageInfoVo = getPage(totalPage, voteTotal, pageNum, count);
        List<VoteSubject> voteSubjectList = voteSubjectDao.findAll((pageNum - 1) * count, count);
        pageInfoVo.setList(voteSubjectList);
        return pageInfoVo;
    }


}

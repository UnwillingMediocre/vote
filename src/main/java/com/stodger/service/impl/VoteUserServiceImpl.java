package com.stodger.service.impl;

import com.stodger.common.Const;
import com.stodger.common.ServerResponse;
import com.stodger.dao.VoteUserDao;
import com.stodger.dao.impl.VoteUserDaoImpl;
import com.stodger.domain.VoteApply;
import com.stodger.domain.VoteUser;
import com.stodger.service.VoteUserService;
import com.stodger.util.IdUtil;
import com.stodger.util.Md5Util;
import com.stodger.vo.PageInfoVo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-06-28 11:28
 */
public class VoteUserServiceImpl implements VoteUserService {

    private VoteUserDao voteUserDao = new VoteUserDaoImpl();

    @Override
    public ServerResponse<VoteUser> login(String username, String password, String newCode, String oldCode) {
        ServerResponse<VoteUser> serverResponse = new ServerResponse<>();
        if (!newCode.toUpperCase().equals(oldCode.toUpperCase())) {
            serverResponse.setCode(Const.VoteUserEnum.CODE_ERROR.getCode());
            serverResponse.setMsg(Const.VoteUserEnum.CODE_ERROR.getMsg());
        } else {
            VoteUser voteUser = voteUserDao.findByUsernameAndPassword(username, Md5Util.getMd5EnCode(password));
            if (voteUser == null || voteUser.getStatus() == 2) {
                serverResponse.setCode(Const.VoteUserEnum.USER_ERROR.getCode());
                serverResponse.setMsg(Const.VoteUserEnum.USER_ERROR.getMsg());
            } else {
                serverResponse.setCode(Const.VoteUserEnum.USER_SUCCESS.getCode());
                serverResponse.setMsg(Const.VoteUserEnum.USER_SUCCESS.getMsg());
                serverResponse.setData(voteUser);
            }
        }
        return serverResponse;
    }

    @Override
    public boolean register(String username, String password, String phone, String email) {
        VoteUser voteUser = new VoteUser();
        voteUser.setUserId(IdUtil.getId());
        voteUser.setUserName(username);
        voteUser.setPassword(Md5Util.getMd5EnCode(password));
        voteUser.setStatus(1);
        voteUser.setPermission(2);
        voteUser.setVersion(1);
        voteUser.setRevise(1);
        voteUser.setPhone(phone);
        voteUser.setEmail(email);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String registerTime = simpleDateFormat.format(date);
        voteUser.setRegisterTime(registerTime);
        return voteUserDao.saveVoteUser(voteUser);
    }

    @Override
    public boolean findByUsername(String username) throws SQLException {
        return voteUserDao.findByUsernameUserId(username);
    }

    @Override
    public boolean updatePassword(String userId, String oldPwd, String newPwd) {
        if (voteUserDao.findByUserIdAndPassword(userId, Md5Util.getMd5EnCode(oldPwd))) {
            return voteUserDao.updateByUserIdRevisePassword(userId, Md5Util.getMd5EnCode(newPwd));
        }
        return false;
    }

    @Override
    public PageInfoVo findUserAll(Integer pageNum, int count) {
        PageInfoVo pageInfoVo = new PageInfoVo();
        int totalVote = voteUserDao.findUserAllCount();
        int totalPage = totalVote / count;
        pageInfoVo = getPage(totalPage, totalVote, pageNum, count);

        List<VoteUser> voteUserList = voteUserDao.findUserAll((pageNum - 1) * count, count);
        pageInfoVo.setList(voteUserList);
        return pageInfoVo;
    }

    @Override
    public boolean updateUserStatus(String userId, Integer status) {
        return voteUserDao.updateUserStatus(userId, status);
    }

    @Override
    public ServerResponse updatePasswordByUserId(String userId) {
        ServerResponse serverResponse = new ServerResponse();
        String msg = "本网站(Micro Vote)的管理员已经初始化您的密码，密码为：";
        String password = "zxvote";
        Random random = new Random();
        int temp = random.nextInt(999);
        password += temp + "";
        boolean result = voteUserDao.updatePasswordByUserId(userId, Md5Util.getMd5EnCode(password));
        if (result) {
            serverResponse.setCode(Const.VoteUserEnum.INIT_SUCCESS.getCode());
            serverResponse.setMsg(msg + password);
        } else {
            serverResponse.setCode(Const.VoteUserEnum.INIT_ERROR.getCode());
            serverResponse.setMsg(Const.VoteUserEnum.INIT_ERROR.getMsg());
        }
        return serverResponse;
    }

    @Override
    public boolean updateUserPermission(String userId, Integer permission) {
        return voteUserDao.updatePermissionByUserId(userId, permission);
    }

    @Override
    public String saveApply(String userId, String name, String email, String description) {
        String username = voteUserDao.findUsernameByUserId(userId);
        if (username != null) {
            boolean result = voteUserDao.saveApply(username, name, email, description);
            if (result) {
                return "恭喜用户为：" + username + "成功申请会员，我们网站(Micro Vote)工作人员将在1-3个工作日(节假日除外)回复您申请结果，请您耐心等待。";
            }
        }
        return null;
    }

    @Override
    public String updateApplyByUsername(String username) {
        boolean result = voteUserDao.updatePermissionByUsername(username, 1);
        if (result) {
            return "恭喜用户为：" + username + "成为我们网站会员用户，请文明使用本网站(Micro Vote)，如果发现有违规，本网站工作人员将收回权限,谢谢配合。快登录网站体验新功能吧！";
        }
        return null;
    }

    @Override
    public void updateApplyStatus(String id) {
        voteUserDao.updateApplyStatus(Integer.parseInt(id));
    }

    @Override
    public List<VoteApply> findApplyAll() {
        return voteUserDao.findApplyAll(0);
    }

    @Override
    public VoteUser findMyInfoByUserId(String userId) {
        return voteUserDao.findMyInfoByUserId(userId);
    }

    @Override
    public void updateMyInfo(String userId, String sex, String birthday, String email, String personality) {
        VoteUser voteUser = new VoteUser();
        voteUser.setUserId(userId);
        voteUser.setSex(sex);
        voteUser.setBirthday(birthday);
        voteUser.setEmail(email);
        voteUser.setPersonality(personality);
        voteUserDao.updateByUserIdAndUserNameVoteUser(voteUser);
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
}

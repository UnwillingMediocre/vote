package com.stodger.service;

import com.stodger.common.ServerResponse;
import com.stodger.domain.VoteApply;
import com.stodger.domain.VoteUser;
import com.stodger.vo.PageInfoVo;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-06-28 11:28
 */
public interface VoteUserService {
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @param newCode  输入验证码
     * @param oldCode  生成验证码
     * @return VoteUser
     */
    ServerResponse login(String username, String password, String newCode, String oldCode);

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     * @param phone 手机号
     * @param email 邮箱
     * @return boolean
     */
    boolean register(String username, String password, String phone, String email);

    /**
     * 根据用户名查找
     *
     * @param username 用户名
     * @return boolean
     * @throws SQLException sql异常
     */
    boolean findByUsername(String username) throws SQLException;

    /**
     * 修改密码
     *
     * @param userId 用户Id
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return boolean
     */
    boolean updatePassword(String userId, String oldPwd, String newPwd);

    /**
     * 查询所有用户
     * @param pageNum 当前页
     * @param count 条数
     * @return List<VoteUser>
     */
    PageInfoVo findUserAll(Integer pageNum, int count);

    /**
     * 更新用户状态(1 使用 2 删除)
     * @param userId 用户名
     * @param status 用户状态
     * @return boolean
     */
    boolean updateUserStatus(String userId, Integer status);

    /**
     * 初始化密码
     * @param userId 用户Id
     * @return boolean
     */
    ServerResponse updatePasswordByUserId(String userId);

    /**
     * 修改权限
     * @param userId 用户Id
     * @param permission 权限
     * @return boolean
     */
    boolean updateUserPermission(String userId, Integer permission);

    /**
     * 保存申请
     * @param userId 用户Id
     * @param name 标题
     * @param email 邮箱
     * @param description 描述
     * @return String
     */
    String saveApply(String userId, String name, String email, String description);

    /**
     * 根据用户名更新权限
     * @param username 用户名
     * @return String
     */
    String updateApplyByUsername(String username);

    /**
     * 更新申请状态
     * @param id 申请Id
     */
    void updateApplyStatus(String id);

    /**
     * 查询所有申请
     * @return List<VoteApply>
     */
    List<VoteApply> findApplyAll();

    /**
     * 查询个人信息
     * @param userId 用户Id
     * @return VoteUser
     */
    VoteUser findMyInfoByUserId(String userId);

    /**
     * 更新个人信息
     * @param userId 用户Id
     * @param sex 性别
     * @param birthday 生日
     * @param email 邮箱
     * @param personality 个性签名
     */
    void updateMyInfo(String userId, String sex, String birthday, String email, String personality);

}
package com.stodger.dao;

import com.stodger.domain.VoteApply;
import com.stodger.domain.VoteUser;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-06-28 11:27
 */
public interface VoteUserDao {


    /**
     * 保存用户信息
     * @param voteUser 用户信息
     * @return boolean
     */
    boolean saveVoteUser(VoteUser voteUser);
    /**
     * 更新用户信息
     * @param voteUser 用户信息
     * @return boolean
     */
    boolean updateByUserIdAndUserNameVoteUser(VoteUser voteUser);

    /**
     * 根据用户Id修改密码
     * @param userId 用户Id
     * @param password 密码
     * @return boolean
     */
    boolean updateByUserIdRevisePassword(String userId, String password);

    /**
     * 根据用户名修改Id
     * @param username 用户名
     * @param userId 用户Id
     * @return boolean
     */
    boolean updateByUsernameUserId(String username, String userId);

    /**
     * 根据用户名称查询用户Id
     * @param username 用户名
     * @return String
     */
    boolean findByUsernameUserId(String username) throws SQLException;

    /**
     * 根据用户名密码查询
     * @param username 用户名
     * @param password 密码
     * @return VoteUser
     */
    VoteUser findByUsernameAndPassword(String username, String password);

    /**
     * 根据用户Id和密码查询
     * @param userId 用户Id
     * @param password 密码
     * @return boolean
     */
    boolean findByUserIdAndPassword(String userId, String password);

    /**
     * 查询所有用户
     * @param pageNum 当前页
     * @param count 条数
     * @return List<VoteUser>
     */
    List<VoteUser> findUserAll(Integer pageNum, int count);

    /**
     * 更新状态
     * @param userId 用户Id
     * @param status 用户状态
     * @return boolean
     */
    boolean updateUserStatus(String userId,Integer status);

    /**
     * 初始密码
     * @param userId 用户Id
     * @param password 密码
     * @return boolean
     */
    boolean updatePasswordByUserId(String userId, String password);


    /**
     * 根据用户Id查询
     * @param userId 用户Id
     * @return int
     */
    int findPermissionByUserId(String userId);

    /**
     * 更新用户Id更新
     * @param userId 用户Id
     * @param permission 权限
     * @return boolean
     */
    boolean updatePermissionByUserId(String userId,Integer permission);

    /**
     * 根据用户Id查询用户名
     * @param userId 用户Id
     * @return String
     */
    String findUsernameByUserId(String userId);

    /**
     * 会员申请
     * @param username 用户名
     * @param name 标题
     * @param email 邮箱
     * @param description 描述
     * @return boolean
     */
    boolean saveApply(String username, String name, String email, String description);

    /**
     * 更新权限
     * @param username 用户名
     * @param permission 权限
     * @return boolean
     */
    boolean updatePermissionByUsername(String username, int permission);

    /**
     * 更新申请状态
     * @param id 申请Id
     */
    void updateApplyStatus(int id);

    /**
     * 查询所有申请
     * @param status 状态
     * @return List<VoteApply>
     */
    List<VoteApply> findApplyAll(int status);

    /**
     * 查询所有用户总条数
     * @return int
     */
    int findUserAllCount();

    /**
     * 查询个人信息
     * @param userId 用户Id
     * @return VoteUser
     */
    VoteUser findMyInfoByUserId(String userId);

}

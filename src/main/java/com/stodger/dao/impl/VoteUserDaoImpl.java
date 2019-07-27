package com.stodger.dao.impl;

import com.stodger.dao.VoteUserDao;
import com.stodger.domain.VoteApply;
import com.stodger.domain.VoteUser;
import com.stodger.util.DataBaseUtil;
import com.stodger.vo.PageInfoVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-06-28 11:29
 */
public class VoteUserDaoImpl implements VoteUserDao {
    @Override
    public boolean saveVoteUser(VoteUser voteUser) {
        String sql = "insert into tb_vote_user(vu_user_id, vu_user_name, vu_password, vu_status, vu_version, vu_permission, vu_revise, vu_register_time, vu_phone,vu_email) values (?,?,?,?,?,?,?,?,?,?)";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        return dataBaseUtil.executeUpdate(sql, voteUser.getUserId(), voteUser.getUserName(), voteUser.getPassword(), voteUser.getStatus(), voteUser.getVersion(), voteUser.getPermission(), voteUser.getRevise(), voteUser.getRegisterTime(), voteUser.getPhone(), voteUser.getEmail());
    }

    @Override
    public boolean updateByUserIdAndUserNameVoteUser(VoteUser voteUser) {
        String sql = "update tb_vote_user set vu_sex = ?, vu_birthday = ?, vu_personality = ?, vu_email = ? where vu_user_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        return dataBaseUtil.executeUpdate(sql, voteUser.getSex(), voteUser.getBirthday(), voteUser.getPersonality(), voteUser.getEmail(), voteUser.getUserId());
    }

    @Override
    public boolean updateByUserIdRevisePassword(String userId, String password) {
        String sql = "update tb_vote_user set vu_password = ? where vu_user_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        return dataBaseUtil.executeUpdate(sql, password, userId);
    }

    @Override
    public boolean updateByUsernameUserId(String username, String userId) {
        String sql = "update tb_vote_user set vu_user_id = ? where vu_user_name = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        return dataBaseUtil.executeUpdate(sql, userId, username);
    }

    @Override
    public boolean findByUsernameUserId(String username) {
        String sql = "select vu_user_id from tb_vote_user where vu_user_name = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, username);
        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return false;
    }

    @Override
    public VoteUser findByUsernameAndPassword(String username, String password) {
        String sql = "select * from tb_vote_user where vu_user_name = ? and vu_password = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, username, password);
        VoteUser voteUser = new VoteUser();
        try {
            if (resultSet.next()) {
                voteUser.setUserId(resultSet.getString("vu_user_id"));
                voteUser.setUserName(resultSet.getString("vu_user_name"));
                voteUser.setPassword(resultSet.getString("vu_password"));
                voteUser.setStatus(resultSet.getInt("vu_status"));
                voteUser.setPermission(resultSet.getInt("vu_permission"));
                voteUser.setVersion(resultSet.getInt("vu_version"));
                voteUser.setRevise(resultSet.getInt("vu_revise"));
                return voteUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return null;
    }

    @Override
    public boolean findByUserIdAndPassword(String userId, String password) {
        String sql = "select count(*) as count from tb_vote_user where vu_user_id = ? and vu_password = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, userId, password);
        try {
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return false;
    }

    @Override
    public List<VoteUser> findUserAll(Integer pageNum, int count) {
        List<VoteUser> voteUserList = new ArrayList<>();
        String sql = "select * from tb_vote_user limit ?,?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, pageNum, count);
        try {
            while (resultSet.next()) {
                VoteUser voteUser = new VoteUser();
                voteUser.setUserId(resultSet.getString("vu_user_id"));
                voteUser.setUserName(resultSet.getString("vu_user_name"));
                voteUser.setPermission(resultSet.getInt("vu_permission"));
                voteUser.setStatus(resultSet.getInt("vu_status"));
                voteUser.setRegisterTime(resultSet.getString("vu_register_time"));
                voteUser.setPhone(resultSet.getString("vu_phone"));
                voteUser.setEmail(resultSet.getString("vu_email"));
                voteUserList.add(voteUser);
            }
            return voteUserList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return null;
    }

    @Override
    public boolean updateUserStatus(String userId, Integer status) {
        String sql = "update tb_vote_user set vu_status = ? where vu_user_id=?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        return dataBaseUtil.executeUpdate(sql, status, userId);
    }

    @Override
    public boolean updatePasswordByUserId(String userId, String password) {
        String sql = "update tb_vote_user set vu_password = ? where vu_user_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        return dataBaseUtil.executeUpdate(sql, password, userId);
    }

    @Override
    public int findPermissionByUserId(String userId) {
        String sql = "select vu_permission from tb_vote_user where vu_user_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, userId);
        try {
            if (resultSet.next()) {
                return resultSet.getInt("vu_permission");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return 0;
    }

    @Override
    public boolean updatePermissionByUserId(String userId, Integer permission) {
        String sql = "update tb_vote_user set vu_permission = ? where vu_user_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        return dataBaseUtil.executeUpdate(sql, permission, userId);
    }

    @Override
    public String findUsernameByUserId(String userId) {
        String sql = "select vu_user_name from tb_vote_user where vu_user_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, userId);
        try {
            if (resultSet.next()) {
                return resultSet.getString("vu_user_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return null;
    }

    @Override
    public boolean saveApply(String username, String name, String email, String description) {
        String sql = "insert into tb_vote_apply(va_sponsor,va_title, va_description, va_status, va_email)values(?,?,?,?,?)";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        return dataBaseUtil.executeUpdate(sql, username, name, description, 0, email);
    }

    @Override
    public boolean updatePermissionByUsername(String username, int permission) {
        String sql = "update tb_vote_user set vu_permission = ? where vu_user_name = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        return dataBaseUtil.executeUpdate(sql, permission, username);
    }

    @Override
    public void updateApplyStatus(int id) {
        String sql = "update tb_vote_apply set va_status = ? where id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        dataBaseUtil.executeUpdate(sql, 1, id);
    }

    @Override
    public List<VoteApply> findApplyAll(int status) {
        String sql = "select * from tb_vote_apply where va_status = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, status);
        List<VoteApply> voteApplyList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                VoteApply voteApply = new VoteApply();
                voteApply.setSponsor(resultSet.getString("va_sponsor"));
                voteApply.setTitle(resultSet.getString("va_title"));
                voteApply.setDescription(resultSet.getString("va_description"));
                voteApply.setEmail(resultSet.getString("va_email"));
                voteApply.setId(resultSet.getInt("id"));
                voteApplyList.add(voteApply);
            }
            return voteApplyList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return null;
    }

    @Override
    public int findUserAllCount() {
        String sql = "select count(*) as count from tb_vote_user";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql);
        try {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return 0;
    }

    @Override
    public VoteUser findMyInfoByUserId(String userId) {
        String sql = "select * from tb_vote_user where vu_user_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, userId);
        VoteUser voteUser = new VoteUser();
        try {
            if (resultSet.next()) {
                voteUser.setUserId(resultSet.getString("vu_user_id"));
                voteUser.setUserName(resultSet.getString("vu_user_name"));
                voteUser.setSex(resultSet.getString("vu_sex"));
                voteUser.setBirthday(resultSet.getString("vu_birthday"));
                voteUser.setPhone(resultSet.getString("vu_phone"));
                voteUser.setEmail(resultSet.getString("vu_email"));
                voteUser.setPersonality(resultSet.getString("vu_personality"));
            }
            return voteUser;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return null;
    }
}

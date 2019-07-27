package com.stodger.dao.impl;

import com.mysql.jdbc.ResultSetRow;
import com.stodger.dao.VoteSubjectDao;
import com.stodger.domain.VoteOption;
import com.stodger.domain.VoteSubject;
import com.stodger.util.DataBaseUtil;
import com.stodger.vo.OptionInfoVo;
import com.stodger.vo.PageInfoVo;
import com.stodger.vo.SubjectAndOptionVo;
import com.stodger.vo.VoteInfoVo;
import sun.awt.geom.AreaOp;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-06-30 18:46
 */
public class VoteSubjectDaoImpl implements VoteSubjectDao {
    @Override
    public boolean saveSubjectAndOption(String title, String description, String[] options, Integer type, Date startTime, Date endTime, Integer status, Integer order, String userId) {
        String sql = "insert into tb_vote_subject(vs_title,vs_type,vs_start_time,vs_end_time,vs_status,vu_user_id,vs_description)values(?,?,?,?,?,?,?)";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        Connection connection = dataBaseUtil.getConnection();
        Integer subjectId = null;
        try {
            connection.setAutoCommit(false);
            ResultSet resultSet = dataBaseUtil.executeUpdateGetKey(sql, title, type, startTime, endTime, status, userId, description);
            if (resultSet.next()) {
                subjectId = resultSet.getInt(1);
            }
            sql = "insert into tb_vote_option(vo_option,vs_id,vo_order)values(?,?,?)";
            boolean result = false;
            for (String str : options) {
                result = dataBaseUtil.executeUpdate(sql, str, subjectId, order);
            }
            connection.commit();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            dataBaseUtil.closeConnection();
        }
        return false;
    }

    @Override
    public boolean updateSubjectAndOption(VoteSubject voteSubject, List<VoteOption> voteOptionList) {
        String sql = "update tb_vote_subject set vs_title = ?, vs_description = ?, vs_type = ?, vs_end_time = ? where vs_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        Connection connection = dataBaseUtil.getConnection();
        boolean result = false;
        try {
            connection.setAutoCommit(false);
            dataBaseUtil.executeUpdate(sql, voteSubject.getTitle(), voteSubject.getDescription(), voteSubject.getType(), voteSubject.getEndTime(), voteSubject.getId());
            for (VoteOption voteOption : voteOptionList) {
                if (voteOption.getId() == -1) {
                    sql = "insert into tb_vote_option(vo_option,vs_id,vo_order)values(?,?,?)";
                    result = dataBaseUtil.executeUpdate(sql, voteOption.getOption(), voteSubject.getId(), voteOption.getOrder());
                } else {
                    sql = "update tb_vote_option set vo_option = ? where vo_id = ? and vs_id = ?";
                    result = dataBaseUtil.executeUpdate(sql, voteOption.getOption(), voteOption.getId(), voteOption.getSubjectId());
                }
            }
            connection.commit();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            dataBaseUtil.closeConnection();
        }
        return false;
    }

    @Override
    public SubjectAndOptionVo findSubjectAndOptionVoBySubjectId(Integer subjectId) {
        String sql = "select * from tb_vote_subject where vs_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, subjectId);
        SubjectAndOptionVo subjectAndOptionVo = new SubjectAndOptionVo();
        try {
            if (resultSet.next()) {
                VoteSubject voteSubject = getVoteSubject(resultSet);
                subjectAndOptionVo.setVoteSubject(voteSubject);
                sql = "select * from tb_vote_option where vs_id = ?";
                resultSet = dataBaseUtil.executeQuery(sql, voteSubject.getId());
                List<VoteOption> voteOptionList = getVoteOptionList(resultSet);
                subjectAndOptionVo.setVoteOptionList(voteOptionList);
            }
            return subjectAndOptionVo;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return subjectAndOptionVo;
    }

    @Override
    public List<SubjectAndOptionVo> findSubjectAndOptionAll() {
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        String sql = "select * from tb_vote_subject";
        ResultSet resultSet = dataBaseUtil.executeQuery(sql);
        try {
            List<SubjectAndOptionVo> subjectAndOptionVoList = new ArrayList<>();
            while (resultSet.next()) {
                SubjectAndOptionVo subjectAndOptionVo = new SubjectAndOptionVo();
                VoteSubject voteSubject = getVoteSubject(resultSet);
                subjectAndOptionVo.setVoteSubject(voteSubject);
                sql = "select * from tb_vote_option where vs_id = ?";
                resultSet = dataBaseUtil.executeQuery(sql, voteSubject.getId());
                List<VoteOption> voteOptionList = getVoteOptionList(resultSet);
                subjectAndOptionVo.setVoteOptionList(voteOptionList);
                subjectAndOptionVoList.add(subjectAndOptionVo);
            }
            return subjectAndOptionVoList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return null;
    }

    @Override
    public List<VoteSubject> findVoteSubjectByUserId(String userId, Integer pageNum, int count) {
        String sql = "select * from tb_vote_subject where vu_user_id = ? limit ?,?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, userId, pageNum, count);
        List<VoteSubject> voteSubjectList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                VoteSubject voteSubject = new VoteSubject();
                voteSubject.setId(resultSet.getInt("vs_id"));
                voteSubject.setTitle(resultSet.getString("vs_title"));
                voteSubject.setDescription(resultSet.getString("vs_description"));
                voteSubject.setStartTime(resultSet.getTimestamp("vs_start_time"));
                voteSubject.setEndTime(resultSet.getTimestamp("vs_end_time"));
                voteSubject.setType(resultSet.getInt("vs_type"));
                voteSubject.setStatus(resultSet.getInt("vs_status"));
                voteSubject.setUserId(resultSet.getString("vu_user_id"));
                voteSubjectList.add(voteSubject);
            }
            return voteSubjectList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return voteSubjectList;
    }

    @Override
    public VoteInfoVo findVoteInfoBySubjectId(int subjectId) {
        String sql = "select vs_title,vs_start_time,vs_end_time,vs_type,vs_description,count(*) as total from tb_vote_subject as vs join tb_vote_item as vi on vs.vs_id = vi.vs_id and vs.vs_id=?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, subjectId);
        try {
            VoteInfoVo voteInfoVo = new VoteInfoVo();
            if (resultSet.next()) {
                voteInfoVo.setSubjectId(subjectId);
                voteInfoVo.setTitle(resultSet.getString("vs_title"));
                voteInfoVo.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("vs_start_time")));
                voteInfoVo.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("vs_end_time")));
                voteInfoVo.setTotalVote(resultSet.getInt("total"));
                voteInfoVo.setDescription(resultSet.getString("vs_description"));
                int typeStr = resultSet.getInt("vs_type");
                if (typeStr == 1) {
                    voteInfoVo.setType("radio");
                } else {
                    voteInfoVo.setType("checkbox");
                }
                sql = "select vu_user_name from tb_vote_subject as vs,tb_vote_user as vu where vs.vs_id = ? and vu.vu_user_id = vs.vu_user_id";
                ResultSet resultSet1 = dataBaseUtil.executeQuery(sql, subjectId);
                if (resultSet1.next()) {
                    voteInfoVo.setUsername(resultSet1.getString("vu_user_name"));
                }
                List<OptionInfoVo> optionInfoVoList = new ArrayList<>();
                sql = "select vo_id,vo_option from tb_vote_option where vs_id = ?";
                ResultSet resultSet2 = dataBaseUtil.executeQuery(sql, subjectId);
                while (resultSet2.next()) {
                    OptionInfoVo optionInfoVo = new OptionInfoVo();
                    optionInfoVo.setOptionId(resultSet2.getInt("vo_id"));
                    optionInfoVo.setContent(resultSet2.getString("vo_option"));
                    sql = "select count(*) as count from tb_vote_option as vo inner join tb_vote_item as vi on vo.vs_id = ? and vo.vo_id = ? and vo.vo_id = vi.vo_id";
                    ResultSet resultSet3 = dataBaseUtil.executeQuery(sql, subjectId, optionInfoVo.getOptionId());
                    if (resultSet3.next()) {
                        optionInfoVo.setCount(resultSet3.getInt("count"));
                    }
                    optionInfoVoList.add(optionInfoVo);
                }
                voteInfoVo.setOptionInfoVoList(optionInfoVoList);
            }
            return voteInfoVo;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return null;
    }

    @Override
    public boolean deleteVoteBySubjectId(Integer subjectId) {
        String sql = "delete from tb_vote_subject where vs_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        return dataBaseUtil.executeUpdate(sql, subjectId);
    }

    @Override
    public boolean findItemBySubjectId(int subjectId) {
        String sql = "select count(*) as count from tb_vote_item where vs_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, subjectId);
        try {
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                System.out.println("count = " + count);
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
    public int findVoteAllCount() {
        String sql = "select count(*) as count from tb_vote_subject";
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
    public List<VoteInfoVo> findVoteAll(int pageNum, int count) {
        String sql = "select * from tb_vote_subject ORDER BY vs_id DESC limit ?,?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet1 = dataBaseUtil.executeQuery(sql, pageNum, count);
        List<VoteInfoVo> voteInfoVoList = new ArrayList<>();
        try {
            while (resultSet1.next()) {
                VoteInfoVo voteInfoVo = new VoteInfoVo();
                voteInfoVo.setSubjectId(resultSet1.getInt("vs_id"));
                voteInfoVo.setTitle(resultSet1.getString("vs_title"));
                sql = "select count(*) as count from tb_vote_subject as vs,tb_vote_item as vi where vs.vs_id = vi.vs_id and vs.vs_id = ?";
                ResultSet resultSet2 = dataBaseUtil.executeQuery(sql, voteInfoVo.getSubjectId());
                if (resultSet2.next()) {
                    voteInfoVo.setTotalVote(resultSet2.getInt("count"));
                }
                voteInfoVoList.add(voteInfoVo);
            }
            return voteInfoVoList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return null;
    }

    @Override
    public void saveItem(List<Integer> optionIdList, Integer subjectId, String userId) {
        String sql = "insert into tb_vote_item(vo_id,vs_id,vu_user_id)values(?,?,?)";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        for (Integer id : optionIdList) {
            dataBaseUtil.executeUpdate(sql, id, subjectId, userId);
        }
    }

    @Override
    public boolean findItemById(Integer optionId, int subjectId, String userId) {
        String sql = "select count(*) as count from tb_vote_item where vo_id = ? and vs_id = ? and vu_user_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, optionId, subjectId, userId);
        try {
            if (resultSet.next()) {
                if (resultSet.getInt("count") > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return false;
    }

    @Override
    public int findVoteTotalByKeyWord(String keyWord) {
        String sql = "select count(*) as count from tb_vote_subject where vs_title like ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, '%' + keyWord + '%');
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
    public List<VoteInfoVo> findVoteByKeyWord(int pageNum, int count, String keyWord) {
        String sql = "select * from tb_vote_subject where vs_title like ? ORDER BY vs_id DESC limit ?,?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet1 = dataBaseUtil.executeQuery(sql, '%' + keyWord + '%', pageNum, count);
        List<VoteInfoVo> voteInfoVoList = new ArrayList<>();
        try {
            while (resultSet1.next()) {
                VoteInfoVo voteInfoVo = new VoteInfoVo();
                voteInfoVo.setSubjectId(resultSet1.getInt("vs_id"));
                voteInfoVo.setTitle(resultSet1.getString("vs_title"));
                sql = "select count(*) as count from tb_vote_subject as vs,tb_vote_item as vi where vs.vs_id = vi.vs_id and vs.vs_id = ?";
                ResultSet resultSet2 = dataBaseUtil.executeQuery(sql, voteInfoVo.getSubjectId());
                if (resultSet2.next()) {
                    voteInfoVo.setTotalVote(resultSet2.getInt("count"));
                }
                voteInfoVoList.add(voteInfoVo);
            }
            return voteInfoVoList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return null;
    }

    @Override
    public int findMyVoteCount(String userId) {
        String slq = "select count(DISTINCT(vs_id)) as count from tb_vote_item where vu_user_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(slq, userId);
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
    public List<VoteInfoVo> findMyVoteByUserId(int pageNum, Integer count, String userId) {
        String sql = "select DISTINCT(vs_id) from tb_vote_item where vu_user_id = ? limit ?,?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, userId, pageNum, count);
        List<VoteInfoVo> voteInfoVoList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                VoteInfoVo voteInfoVo = new VoteInfoVo();
                voteInfoVo.setSubjectId(resultSet.getInt("vs_id"));
                sql = "select vs_title,count(*) as count from tb_vote_subject as vs,tb_vote_item as vi where vs.vs_id = vi.vs_id and vs.vs_id = ?";
                ResultSet resultSet2 = dataBaseUtil.executeQuery(sql, voteInfoVo.getSubjectId());
                if (resultSet2.next()) {
                    voteInfoVo.setTitle(resultSet2.getString("vs_title"));
                    voteInfoVo.setTotalVote(resultSet2.getInt("count"));
                }
                voteInfoVoList.add(voteInfoVo);
            }
            return voteInfoVoList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return null;
    }

    @Override
    public List<VoteSubject> findAll(int pageNum, int count) {
        String sql = "select vs.*,vu.vu_user_name from tb_vote_subject as vs, tb_vote_user as vu where vu.vu_user_id = vs.vu_user_id limit ?,?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, pageNum, count);
        List<VoteSubject> voteSubjectList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                VoteSubject voteSubject = new VoteSubject();
                voteSubject.setId(resultSet.getInt("vs_id"));
                voteSubject.setTitle(resultSet.getString("vs_title"));
                voteSubject.setEndTime(resultSet.getTimestamp("vs_end_time"));
                voteSubject.setDescription(resultSet.getString("vs_description"));
                voteSubject.setUsername(resultSet.getString("vu_user_name"));
                voteSubjectList.add(voteSubject);
            }
            return voteSubjectList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dataBaseUtil.closeConnection();
        }
        return null;
    }

    @Override
    public int findAllCount() {
        String sql = "select count(*) as count from tb_vote_subject";
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
    public int findVoteSubjectByUserIdCount(String userId) {
        String sql = "select count(*) as count from tb_vote_subject where vu_user_id = ?";
        DataBaseUtil dataBaseUtil = new DataBaseUtil();
        ResultSet resultSet = dataBaseUtil.executeQuery(sql, userId);
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

    private VoteSubject getVoteSubject(ResultSet resultSet) throws SQLException {
        Date date = new Date();
        VoteSubject voteSubject = new VoteSubject();
        voteSubject.setId(resultSet.getInt("vs_id"));
        voteSubject.setTitle(resultSet.getString("vs_title"));
        voteSubject.setDescription(resultSet.getString("vs_description"));
        voteSubject.setType(resultSet.getInt("vs_type"));
        voteSubject.setStatus(resultSet.getInt("vs_status"));
        voteSubject.setStartTime(resultSet.getTimestamp("vs_start_time"));
        voteSubject.setEndTime(resultSet.getTimestamp("vs_end_time"));
        return voteSubject;
    }

    private List<VoteOption> getVoteOptionList(ResultSet resultSet) throws SQLException {
        List<VoteOption> voteOptionList = new ArrayList<>();
        while (resultSet.next()) {
            VoteOption voteOption = new VoteOption();
            voteOption.setId(resultSet.getInt("vo_id"));
            voteOption.setOption(resultSet.getString("vo_option"));
            voteOption.setOrder(resultSet.getInt("vo_order"));
            voteOptionList.add(voteOption);
        }
        return voteOptionList;
    }
}

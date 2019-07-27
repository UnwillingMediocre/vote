package com.stodger.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-06-29 09:18
 */
public class DataBaseUtil {
    private static final String URL = "jdbc:mysql://139.199.162.74:3306/db_vote";
    private static final String USER = "jsu1573";
    private static final String PASSWORD = "jsu1573";

    /*private static final String URL = "jdbc:mysql://127.0.0.1:3306/db_vote?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";*/
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public DataBaseUtil(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean executeUpdate(String sql, Object ... param ){
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < param.length; i++){
                preparedStatement.setObject(i + 1,param[i]);
            }
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet executeUpdateGetKey(String sql, Object ... param ){
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < param.length; i++){
                preparedStatement.setObject(i + 1,param[i]);
            }
            preparedStatement.executeUpdate();
            return preparedStatement.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet executeQuery(String sql, Object ... param ){
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < param.length; i++){
                preparedStatement.setObject(i + 1, param[i]);
            }
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Object executeQueryUnique(String sql, Object ... param ){
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < param.length; i++){
                preparedStatement.setObject(i + 1, param[i]);
            }
            resultSet = preparedStatement.executeQuery();
            resultSet.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnection(){
        if(connection != null){
            return connection;
        }else {
            System.out.println("......");
            new DataBaseUtil();
            return connection;
        }
    }

    public void closeConnection(){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

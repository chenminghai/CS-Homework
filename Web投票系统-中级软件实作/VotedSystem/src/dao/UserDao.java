package dao;

import db.DB;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class UserDao {
    /**
     * 通过主键id获取用户信息
     * @param id
     * @return
     */
    public User get(int id){
        User result = null;
        String sql = "select * from user where id = ?";
        DB db = new DB(sql);
        db.setInt(1,id);
        ResultSet rs = db.select();
        try {
            if (rs.next()){
                result = new User();
                result.setId(rs.getInt("id"));
                result.setUsername(rs.getString("username"));
                result.setRealName(rs.getString("realname"));
                result.setTelephone(rs.getString("telephone"));
                result.setSex(rs.getString("sex"));
                result.setBirthday(rs.getString("birthday"));
                result.setCreate_time(rs.getLong("create_time"));
                result.setLevel(rs.getInt("level"));
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }finally {
            db.close();
        }
        return result;
    }

    /**
     * 通过用户名和密码获取用户
     * @param username
     * @param password
     * @return
     */
    public User get(String username, String password){
        User result = null;
        String sql = "select * from user where username = ? and password = ? limit 1";
        DB db = new DB(sql);
        db.setString(1,username);
        db.setString(2,password);
        ResultSet rs = db.select();
        try {
            if (rs.next()){
            	result = new User();
                result.setId(rs.getInt("id"));
                result.setUsername(rs.getString("username"));
                result.setRealName(rs.getString("realname"));
                result.setTelephone(rs.getString("telephone"));
                result.setSex(rs.getString("sex"));
                result.setBirthday(rs.getString("birthday"));
                result.setCreate_time(rs.getLong("create_time"));
                result.setLevel(rs.getInt("level"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  result;
    }

    /**
     * 通过用户名判断用户是否存在
     * @param username

     * @return
     */
    public int get(String username){
        int result = 0;
        String sql = "select * from user where username = ? limit 1";
        DB db = new DB(sql);
        db.setString(1,username);
        ResultSet rs = db.select();
        try {
            if (rs.next()){
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  result;
    }

    /**
     * 新增一个用户
     * @param username
     * @param password
     * @return
     */
    public int add(String username,String realname,String password,String telephone,String sex,String birthday,int level){
        int result = 0;
        if (this.get(username) == 1){
            return result;
        }
        String sql = "insert into user(username,password,create_time,realname,telephone,sex,birthday,level) values(?,?,?,?,?,?,?,?)";
        DB db = new DB(sql);
        db.setString(1, username);
        db.setString(2,password);
        db.setLong(3,new Date().getTime());
        db.setString(4,realname);
        db.setString(5,telephone);
        db.setString(6,sex);
        db.setString(7,birthday);
        db.setInt(8,level);
        result = db.update();
        db.close();
        return result;
    }
    
    public int update(String realname,String password,String telephone,String sex,String birthday,int id){
        int result = 0;
        String sql = "update user set realname = ? , password = ? , telephone = ? ,sex = ? ,birthday = ? where id = ?";
        DB db = new DB(sql);
        db.setString(1, realname);
        db.setString(2,password);
        db.setString(3,telephone);
        db.setString(4,sex);
        db.setString(5,birthday);
        db.setInt(6,id);
        result = db.update();
        db.close();
        return result;
    }
}

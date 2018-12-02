package dao;

import db.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class ResultDao {
    /**
     * 通过item_id获取总投票数
     */
    public int getAllVoteCount(int itemId){
        int result = 0;
        String sql = "select count(*) from result where item_id = ?";
        DB db = new DB(sql);
        db.setInt(1,itemId);
        ResultSet rs = db.select();
        try {
            while (rs.next()){
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();
        return result;
    }

    /**
     * 通过item_id和select_id获取一个投票选项的投票总数
     */
    public int getSelectVoteCount(int itemId,int selectId){
        int result = 0;
        String sql = "select count(*) from result where item_id = ? and select_id = ?";
        DB db = new DB(sql);
        db.setInt(1,itemId);
        db.setInt(2,selectId);
        ResultSet rs = db.select();
        try {
            if (rs.next()){
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();
        return result;
    }

    /**
     * 添加投票记录
     */
    public int add(int userId,int itemId,String[] select_ids,String ip){
        int result = 0;
        if(this.getByIp(itemId,ip) == 1){
            return result;//已经投票，直接返回
        }
        String sql = "insert into result(item_id,select_id,ip,user_id,vote_time) values(?,?,?,?,?)";
        DB db = new DB(sql);
        db.setInt(1,itemId);
        db.setString(3,ip);
        db.setInt(4, userId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        db.setString(5,df.format(new Date()));// new Date()为获取当前系统时间
        
        int length = select_ids.length;
        for (int i = 0; i < length; i++){
            db.setInt(2,Integer.parseInt(select_ids[i]));
            result = db.update();
        }
        db.close();
        return result;

    }

    /**
     * 通过ip查找投票记录，防止多次投票
     * @param ip
     * @return
     */
    public int getByIp(int itemId,String ip){
        int result = 0;
        String sql = "select * from result where ip = ? and item_id = ? limit 1";
        DB db = new DB(sql);
        db.setString(1,ip);
        db.setInt(2,itemId);
        ResultSet rs = db.select();
        try {
            if (rs.next()){
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();
        return result;
    }
    
    /**
     * 通过用户id查找投票记录，防止多次投票
     * @param ip
     * @return
     */
    public int getByIp(int itemId,int userId){
        int result = 0;
        String sql = "select * from result where item_id = ? and user_id = ? limit 1";
        DB db = new DB(sql);
        db.setInt(1,itemId);
        db.setInt(2,userId);
        ResultSet rs = db.select();
        try {
            if (rs.next()){
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();
        return result;
    }

}

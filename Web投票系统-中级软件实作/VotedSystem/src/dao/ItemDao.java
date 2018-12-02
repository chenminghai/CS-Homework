package dao;
import db.DB;
import entity.Item;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("rawtypes")
public class ItemDao {

    /**
     * 新增一条投票
     * @return
     */
    public int add(String theme,String start_time,String stop_time,String option_content,String type,int user_id,String isWaiver,String isOppose,int number){
        //添加一条投票
        String sql = "insert into item(theme,start_time,stop_time,type,user_id,is_waiver,is_oppose,number) values(?,?,?,?,?,?,?,?)";
        DB db = new DB(sql);
        db.setString(1, theme);
        db.setString(2,start_time);
        db.setString(3,stop_time);
        db.setString(4,type);
        db.setInt(5,user_id);
        db.setString(6,isWaiver);
        db.setString(7,isOppose);
        db.setInt(8,number);
        int result1 = db.update();
        if (result1 == 0){
            return 0;
        }
        int item_id = db.getInsertId();
        db.close();
        //对option_content的处理, 添加每一个选项，并返回id
        String[] option_array = option_content.split("\\r?\\n");
        int length = option_array.length;
        String option_ids = "";
        String option_id = "";
        System.out.println("长度是：" + length);
        for (int i = 0; i < length; i++){
            OptionDao optionDao = new OptionDao();
            option_id = optionDao.add(item_id,option_array[i]) + "";
            option_ids = option_ids + option_id + ",";
        }
        String new_str =option_ids.substring(0, option_ids.length() - 1);
        //把转换后的option_ids 写会投票中
        String sql2 = "update item set option_ids = ? , count = ? where id = ?";
        DB db2 = new DB(sql2);
        db2.setString(1,new_str);
        db2.setInt(2,length);
        db2.setInt(3,item_id);
        int result2 = db2.update();
        db2.close();
        if(result2<=0) item_id=0;
        return item_id;
    }

    /**
     * 通过用户的id获取该用户的所有投票
     * @param user_id
     * @return
     */
    @SuppressWarnings("unchecked")
	public List getItemList(int user_id){
        List list = new ArrayList();
        String sql = "select * from item where user_id = ?";
        DB db = new DB(sql);
        db.setInt(1,user_id);
        int count;
        ResultDao resultDao = new ResultDao();
        ResultSet rs = db.select();
        try {
            while (rs.next()){
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setIsStop(rs.getString("is_stop"));
                item.setIsWaiver(rs.getString("is_waiver"));
                item.setIsOppose(rs.getString("is_oppose"));
                item.setOptionIds(rs.getString("option_ids"));
                item.setTheme(rs.getString("theme"));
                item.setStartTime(rs.getString("start_time"));
                item.setStopTime(rs.getString("stop_time"));
                item.setType(rs.getString("type"));
                item.setUser_id(rs.getInt("user_id"));
                item.setCount(rs.getInt("count"));
                count = resultDao.getAllVoteCount(rs.getInt("id"));
                item.setAllVoteCount(count);
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();
        return list;
    }

    /**
     * 通过item的id获取一条投票的信息
     * @param id
     * @return
     */
    public Item getItemById(int id){
        Item item = new Item();
        String sql = "select * from item where id = ?";
        DB db = new DB(sql);
        db.setInt(1,id);
        int count;
        ResultDao resultDao = new ResultDao();
        UserDao userDao = new UserDao();
        ResultSet rs = db.select();
        try {
            if (rs.next()){
                item.setId(rs.getInt("id"));
                item.setTheme(rs.getString("theme"));
                item.setOptionIds(rs.getString("option_ids"));
                item.setStartTime(rs.getString("start_time"));
                item.setStopTime(rs.getString("stop_time"));
                item.setIsStop(rs.getString("is_stop"));
                item.setIsWaiver(rs.getString("is_waiver"));
                item.setIsOppose(rs.getString("is_oppose"));
                item.setType(rs.getString("type"));
                item.setUser_id(rs.getInt("user_id"));
                item.setNumber(rs.getInt("number"));
                item.setCount(rs.getInt("count"));
                User user = userDao.get(rs.getInt("user_id"));
                item.setUserName(user.getRealName());
                count = resultDao.getAllVoteCount(rs.getInt("id"));
                item.setAllVoteCount(count);

            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }finally {
            db.close();
        }
        return item;
    }

    /**
     * 通过id更新投票信息
     * @param theme
     * @param start_time
     * @param stop_time
     * @param type
     * @param id
     * @return
     */
    public int updateItemById(String theme,String start_time,String stop_time,String type,int number,int id){
        int result = 0;
        String sql = "update item set theme = ? , start_time = ? , stop_time = ? ,type = ? where id = ?";
        DB db = new DB(sql);
        db.setString(1,theme);
        db.setString(2,start_time);
        db.setString(3,stop_time);
        db.setString(4,type);
        db.setInt(5,id);
        result = db.update();
        db.close();
        return result;
    }

    /**
     * 停用或启用一个投票
     * @param id
     * @param status
     * @return
     */
    public int statusItemById(int id,String status){
        int result;
        String sql = "update item set is_stop = ? where id = ?";
        DB db = new DB(sql);
        db.setString(1,status);
        db.setInt(2,id);
        result = db.update();
        db.close();
        return result;
    }

    /**
     * 通过id删除一个投票
     * @param id
     * @return
     */
     public int deleteItemById(int id){
        int result;
        String sql = "delete from item where id = ?";
        DB db = new DB(sql);
        db.setInt(1,id);
        result = db.update();
        db.close();
        return result;
     }

    /**
     * 根据sql语句获取投票列表
     * @return
     */
     @SuppressWarnings("unchecked")
	private List getItemList(String sql){
         List itemList = new ArrayList();
         DB db = new DB(sql);
         UserDao userDao = new UserDao();
         ResultDao resultDao = new ResultDao();
         ResultSet rs = db.select();
         int count;
         try {
             while (rs.next()){
                 Item item = new Item();
                 item.setId(rs.getInt("id"));
                 item.setTheme(rs.getString("theme"));
                 item.setStartTime(rs.getString("start_time"));
                 item.setStopTime(rs.getString("stop_time"));
                 item.setOptionIds(rs.getString("option_ids"));
                 item.setType(rs.getString("type"));
                 item.setUser_id(rs.getInt("user_id"));
                 item.setNumber(rs.getInt("number"));
                 item.setCount(rs.getInt("count"));
                 item.setIsWaiver(rs.getString("is_waiver"));
                 item.setIsOppose(rs.getString("is_oppose"));
                 User user = userDao.get(rs.getInt("user_id"));
                 item.setUserName(user.getRealName());
                 count = resultDao.getAllVoteCount(rs.getInt("id"));
                 item.setAllVoteCount(count);
                 item.setStatus(this.getVoteStatus(rs.getString("start_time"),rs.getString("stop_time")));
                 itemList.add(item);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
         db.close();
         return itemList;
     }

    /**
     * 通过时间判断投票的状态
     */
    public int getVoteStatus(String startTime,String stopTime){
        int result = 1;
        DateFormat df  = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = df.format(new Date());
        try {
            Date start   = df.parse(startTime);
            Date stop    = df.parse(stopTime);
            Date current = df.parse(currentTime) ;
            if (start.getTime() > current.getTime()){
                result = 1;
            }else if(stop.getTime() < current.getTime()){
                result = 3;
            }else {
                result = 2;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过主题模糊查找
     */
    public List getSearchItemList(String content){
        String sql = "select * from item where is_stop = 2 and theme like '%" + content + "%' order by id desc";
        return this.getItemList(sql);
    }

    /**
     * 获取全部投票
     */
    public List getAllItemList(){
        String sql = "select * from item where is_stop = 2 order by id desc";
        return this.getItemList(sql);
    }

    /**
     * 获取指定条数投票
     */
    public List getLimitItemList(int num){
        String sql = "select * from item where is_stop = 2 order by id desc limit " + num;
        return this.getItemList(sql);
    }
}


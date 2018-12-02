package dao;

import db.DB;
import entity.Option;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionDao {
    /**
     * 插入一条选项，并返回插入的id
     */
    public int add(int item_id,String content){
        int id = -1;
        String sql = "insert into item_options(item_id,content,name) values(?,?,?)";
        DB db = new DB(sql);
        db.setInt(1,item_id);
        db.setString(2,content.trim());
        db.setString(3,content.trim());
        db.update();
        id = db.getInsertId();
        db.close();
        return id;
    }

    /**
     * 通过投票id更新候选人信息
     * @param theme
     * @param start_time
     * @param stop_time
     * @param type
     * @param id
     * @return
     */
    public int updateOptionById(int id,String sex,String birthday,String age,String nation,String political,String address,String telephone,String recommend){
        int result = 0;
        String sql = "update item_options set sex = ? ,birthday = ? ,age = ? ,nation = ? ,political = ? ,address = ? ,telephone = ? ,recommend = ? where id = ?";
        DB db = new DB(sql);
        db.setString(1,sex);
        db.setString(2,birthday);
        db.setString(3,age);
        db.setString(4,nation);
        db.setString(5,political);
        db.setString(6,address);
        db.setString(7,telephone);
        db.setString(8,recommend);
        db.setInt(9,id);
        result = db.update();
        db.close();
        return result;
    }
    
    /**
     * 返回投票的选项详情,并计算所占比例
     * @param itemId
     * @return
     */
	public List<Option> getOptionList(int itemId,int allCount){
        List<Option> optionList = new ArrayList<>();
        String sql = "select * from item_options where item_id = ?";
        DB db = new DB(sql);
        db.setInt(1,itemId);
        int count = 0;
        ResultDao resultDao = new ResultDao();
        ResultSet rs = db.select();
        try {
            while (rs.next()){
                Option option = new Option();
                option.setId(rs.getInt("id"));
                option.setItemId(rs.getInt("item_id"));
                option.setContent(rs.getString("content").trim());
                option.setName(rs.getString("name"));
                option.setSex(rs.getString("sex"));
                option.setBirthday(rs.getString("birthday"));
                option.setAge(rs.getString("age"));
                option.setNation(rs.getString("nation"));
                option.setPolitical(rs.getString("political"));
                option.setAddress(rs.getString("address"));
                option.setTelephone(rs.getString("telephone"));
                option.setRecommend(rs.getString("recommend"));
                System.out.println(rs.getString("sex"));
                count = resultDao.getSelectVoteCount(itemId,rs.getInt("id"));
                option.setResultCount(count);
                if (allCount == 0){
                    option.setPercentage(0.00);
                }else {
                    option.setPercentage((count * 1.0 / allCount) * 100);
                }
                optionList.add(option);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();
        return optionList;
    }
}

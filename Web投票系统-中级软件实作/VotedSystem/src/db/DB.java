package db;

import java.sql.*;


public class DB {

    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/db_vote?characterEncoding=utf-8";
    public static final String USER = "root";
    public static final String PWD = "123456";

    private Connection con = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;

    public DB() {
        createConnection();
        try {
            stmt = con.createStatement();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    public DB(String preparedSql) {
        createConnection();
        try {
            pstmt = con.prepareStatement(preparedSql,Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    private void createConnection() {
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, USER, PWD);
        } catch (ClassNotFoundException ex) {
            System.err.println("Error: 类不存在!" + ex.getMessage());
        } catch (SQLException ex) {
            System.err.println("Error: 连接数据库失败!" + ex.getMessage());
        }
    }

    public ResultSet select(String sql) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return rs;
    }

    //preparedStatement的查询方法
    public ResultSet select() {
        ResultSet rs = null;
        try {
            rs = pstmt.executeQuery();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return rs;
    }
    
    public int update(String sql) {
        int result = 0;
        try {
            result = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return result;
    }

    //preparedStatement的更新
    public int update() {
        int result = 0;
        try {
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return result;
    }

    public int getInsertId(){
        int autoInckey = -1;
        ResultSet rs = null; // 获取结果
        try {
            rs = pstmt.getGeneratedKeys();
            if (rs.next()){
                autoInckey = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return autoInckey;
    }

    //以下方法为使用动态SQL语句方式时，设置prestmt的参数的方法---------------------
    //其他类型的参数对应的方法,请自行补充
    public void setString(int index, String value) {
        try {
            pstmt.setString(index, value);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    public void setInt(int index, int value) {
        try {
            pstmt.setInt(index, value);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    public void setLong(int index, long value) {
        try {
            pstmt.setLong(index, value);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    public void setDouble(int index, double value) {
        try {
            pstmt.setDouble(index, value);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    public void close() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}

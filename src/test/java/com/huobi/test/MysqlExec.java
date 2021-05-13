package com.huobi.test;

import com.alibaba.fastjson.JSON;
import com.huobi.model.trade.Order;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dekai.kong
 * @difficult
 * @create 2021-05-10 15:11
 * @from
 **/
public class MysqlExec {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/huobi?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "pass@1234";

    public static Connection connection;

    public static Connection getConn(){
        try {
            if(connection!=null&&!connection.isClosed()){
                return connection;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // 注册 JDBC 驱动
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 打开链接
        System.out.println("连接数据库...");
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public static String doSql(String sql,Class... clazz){
        String json = "";
        Connection conn = getConn();
        Statement stmt = null;
        try{
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();


            if(sql.contains("select")){
                ResultSet rs = stmt.executeQuery(sql);
                Object o = getBeans(rs, clazz[0]);
                json = JSON.toJSONString(o);
//                System.out.println("获取到的结果:"+json);
                rs.close();
            }else{
                stmt.execute(sql);
                System.out.println("执行插入sql,不打印结果");
            }
//            // 展开结果集数据库
//            while(rs.next()){
//                // 通过字段检索
//                int id  = rs.getInt("id");
//                String name = rs.getString("name");
//                String url = rs.getString("url");
//
//                // 输出数据
//                System.out.print("ID: " + id);
//                System.out.print(", 站点名称: " + name);
//                System.out.print(", 站点 URL: " + url);
//                System.out.print("\n");
//            }
            // 完成后关闭

            stmt.close();

        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做

        }
        System.out.println("Goodbye!");
        return json;
    }

    public static Object getBeans(ResultSet resultSet, Class clazz) throws Exception {
        //获取要封装的javabean声明的属性
        Field[] fields = clazz.getDeclaredFields();
        //获取ResultSetMetaData(包含ResultSet中列的名称和类型的信息)
        ResultSetMetaData metaData = resultSet.getMetaData();
        //光标移到最后
        resultSet.last();
        //获取结果数据条数
        int rows = resultSet.getRow();
        //光标移到第一条数据前
        resultSet.beforeFirst();
        //获取查询结果列数
        int columnCount = metaData.getColumnCount();
        if (rows > 1) {
            List<Object> list = new ArrayList<>();
            //遍历ResultSet
            while (resultSet.next()) {
                //调用无参构造实例化对象
                Object object = clazz.newInstance();
                for (int j = 1; j <= columnCount; j++) {
                    //获取列名
                    String columnName = metaData.getColumnName(j);
                    //匹配JavaBean的属性,然后赋值
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.getName().equalsIgnoreCase(columnName)) {
                            field.set(object, resultSet.getObject(columnName));
                            break;
                        }
                    }
                }
                list.add(object);
            }
            return list;
        } else {
            Object object = clazz.newInstance();
            //遍历ResultSet
            while (resultSet.next()) {
                for (int j = 1; j <= columnCount; j++) {
                    String columnName = metaData.getColumnName(j);
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.getName().equalsIgnoreCase(columnName)) {
                            field.set(object, resultSet.getObject(columnName));
                        }
                    }
                }
            }
            return object;
        }
    }

    public static void closeConn(){
        try {
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

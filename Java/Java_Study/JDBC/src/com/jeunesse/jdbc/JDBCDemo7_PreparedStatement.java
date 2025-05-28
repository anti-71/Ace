package com.jeunesse.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class JDBCDemo7_PreparedStatement {
    // 目的：JDBC API 详解：PreparedStatement

    // 执行DQL语句
    @Test
    public void testPreparedStatement() throws Exception {
        // 2、获取连接：如果连接的是本机mysql并且端口是默认的3306可以简化书写
        String url = "jdbc:mysql:///test";
        String username = "root";
        String password = "1234";
        Connection conn = DriverManager.getConnection(url, username, password);

        // 接收用户输入的用户名和密码
        String name = "zhangsan";
        String pwd = "123";

        // 定义sql
        String sql = "select * from tb_user where username = ? and password = ?";

        // 获取PreparedStatement对象
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 设置？的值
        pstmt.setString(1, name);
        pstmt.setString(2, pwd);

        // 执行sql
        ResultSet rs = pstmt.executeQuery();

        // 判断登录是否成功
        if (rs.next()) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }

        // 7、释放资源
        rs.close();
        pstmt.close();
        conn.close();
    }
}

package wangjie.jdbc;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Sai
 * Created by Sai on 2019-03-25.
 */
@SuppressWarnings({"Duplicates", "SqlDialectInspection", "SqlNoDataSourceInspection"})
@WebServlet("/jdbc")
public class JdbcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        // 不能显式conn.close(),否则再次执行时会抛异常-连接已关闭。
        Connection conn = (Connection) getServletContext().getAttribute("connection");
        /*
            注意，Connection预设为自动“认可/提交”（Commit），也就是Statement执行SQL叙述完后，马上对数据库进行操作变更.
            如果想要对Statement要执行的SQL进行除错，可以使用setAutoCommit(false)来将自动认可取消，在执行完SQL之后，再呼叫Connection的commit()方法认可变更，
            使用Connection的getAutoCommit()可以测试是否设定为自动认可。
            不过无论是否有无执行commit()方法，只要SQL没有错，在关闭Statement或Connection前，都会执行认可commit动作，对数据库进行变更。
         */

        // 使用JDK 7的try(){}资源句式：可以 try 一个或多个资源，使用try资源句式可以自动 close 资源。如：conn.close();stmt.close();rs.close();
        try (
                // 通过数据库的连接操作数据库，实现增删改查
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from user");
        ) {
            // 遍历每行记录
            while (rs.next()) {//如果结果集中有数据，就会循环打印出来
                System.out.println(rs.getString("name") + "," + rs.getInt("age"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

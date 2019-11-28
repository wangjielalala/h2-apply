package wangjie.jdbc;

import wangjie.PersonDTO;

import java.sql.*;

/**
 * @author Sai
 * Created by Sai on 2019-03-25.
 * <p>
 * <p>
 * <p>
 * JDBC编程步骤：
 * 0.加载数据库驱动程序
 * 1.连接到数据库
 * 2.创建SQL或MySQL语句
 * 3.在数据库中执行SQL或MySQL查询
 * 4.查看和修改记录
 * <p>
 * H2官方教程：
 * http://www.h2database.com/html/tutorial.html
 */
@SuppressWarnings({"Duplicates", "unused", "SqlNoDataSourceInspection", "SqlDialectInspection"})
public class DBUtil {

    //    MySql的URL
    //    private static final String URL="jdbc:mysql://localhost:3306/demo_jdbc";
    //    private static final String DRIVER = "com.mysql.jdbc.Driver";

    //  H2
    //  使用H2的本地嵌入式连接
    //  这个模式会与
    //  指定数据库文件：
    //      jdbc:h2:~/test
    //      jdbc:h2:file:/data/sample
    //      jdbc:h2:file:C:/data/sample (Windows only)
    //  如果数据库不存在，会在用户目录创建一个数据库文件，缺省用户是sa，密码是空字符串
    private static final String URL = "jdbc:h2:~/test;MODE=MYSQL;DB_CLOSE_DELAY=-1";
    //  本地连接的内存模式，程序模式时，应用程序结束后，数据库就被清除。
    private static final String URL_MEM = "jdbc:h2:mem:test;MODE=MYSQL;DB_CLOSE_DELAY=-1";

    // 使用H2的TCP连接模式
    // 使用服务器模式连接，必须先独立运行H2的数据库服务器,参考开发环境文档的H2章节；
    // java -jar h2*.jar
    // java -cp h2*.jar org.h2.tools.Server
    // 上面命令中的 -cp 参数，作用与 -classpath 一样，是指定类运行所依赖其他类的路径，即指定给解释器到哪里找到你的.class文件，通常是类库，jar包之类，需要全路径到jar包;
    // 如果有多个jar包，可以用分隔符分隔，window上分号“;”分隔，linux上是分号“:”分隔。不支持通配符，需要列出所有jar包，用一点“.”代表当前路径。
    private static final String URL_TCP = "jdbc:h2:tcp://localhost/~/test;MODE=MYSQL;DB_CLOSE_DELAY=-1";
    // 内存模式
    private static final String URL_TCP_MEM = "jdbc:h2:tcp://localhost/mem:test;MODE=MYSQL;DB_CLOSE_DELAY=-1";

    private static final String DRIVER = "org.h2.Driver";

    private static final String NAME = "wangjie";
    private static final String PASSWORD = "wangjie";

    public static Boolean getDRIVER(PersonDTO personDTO) {
        try (Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD)) {
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS `user` (" +
                            "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT," +
                            "  `name` varchar(100) NOT NULL," +
                            "  `age` int(11) NOT NULL," +
                            "  PRIMARY KEY (`id`)" +
                            ");"
            );
            String name = personDTO.getName();
            int age = personDTO.getAge();
            String sql = "insert into `user` (`name`,`age`) values ( ? , ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {

        // 1.加载驱动程序
        // JDBC4.0以后，使用JDK的SPI机制自动加载驱动类，所以下面的代码块可以注释掉。
        // 注：SPI 全称为 (Service Provider Interface) ，是JDK内置的一种服务提供发现机制。
        //  当服务的提供者提供了一种接口的实现之后，需要自己的jar中在classpath下的META-INF/services/目录里创建一个以服务接口命名的文件，
        //  这个文件里的内容就是这个接口的具体的实现类。当其他的程序需要这个服务的时候，就可以通过查找这个jar包（一般都是以jar包做依赖）的META-INF/services/中的配置文件，
        //  配置文件中有接口的具体实现类名，可以根据这个类名进行加载实例化，就可以使用该服务了。JDK中查找服务实现的工具类是：java.util.ServiceLoader。
        // 例如：使用的是H2数据库驱动，查看h2驱动的jar里有一个services/java.sql.Driver文件里，文件的内容是org.h2.Driver
        //
        // Java EE 7开始支持JDBC4.0
        //        try {
        //            Class.forName(DRIVER);
        //        } catch (ClassNotFoundException e) {
        //            e.printStackTrace();
        //            return;
        //        }

        // 2.获得数据库的连接
        // 使用JDK 7的try(){}资源句式：可以 try 一个或多个资源，使用try资源句式可以自动 close 资源。如：conn.close();
        try (Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD)) {
            /*
                注意，Connection预设为自动“认可/提交”（Commit），也就是Statement执行SQL叙述完后，马上对数据库进行操作变更.
                如果想要对Statement要执行的SQL进行除错，可以使用setAutoCommit(false)来将自动认可取消，在执行完SQL之后，再呼叫Connection的commit()方法认可变更，
                使用Connection的getAutoCommit()可以测试是否设定为自动认可。
                不过无论是否有无执行commit()方法，只要SQL没有错，在关闭Statement或Connection前，都会执行认可commit动作，对数据库进行变更。
            */

            //3.通过数据库的连接操作数据库，实现增删改查
            Statement stmt = conn.createStatement();

            // 创建一个表
            // 如果表不存在就建立这个表，那么可以直接用 create table if not exists tablename 这样的指令来建立，不需要先去查询表是否存在。
            // 从模板表创建表：create table if not exists like old_table_name;
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS `user` (" +
                            "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT," +
                            "  `name` varchar(100) NOT NULL," +
                            "  `age` int(11) NOT NULL," +
                            "  PRIMARY KEY (`id`)" +
                            ");"
            );

            // 插入数据记录
            // id主键是自动生成
            stmt.executeUpdate(
                    "insert into `user` (`name`,`age`) values ('Jerry', 27);" +
                            "insert into `user` (`name`,`age`) values ('Angel', 25);"
            );

            ResultSet rs = stmt.executeQuery("select * from user");//选择import java.sql.ResultSet;

            // 遍历每行记录
            while (rs.next()) {
                //如果结果集中有数据，就会循环打印出来
                System.out.println(rs.getString("id") + "," + rs.getString("name") + "," + rs.getInt("age"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

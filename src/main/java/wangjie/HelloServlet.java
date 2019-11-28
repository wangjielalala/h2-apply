package wangjie;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author 黎志雄
 * 创建于2019-2-18
 *
 * 企业级应用开发实训课程
 *
 * 本servlet访问网址：
 * http://localhost:8080/webapp-demo/hello
 * http://localhost:8080/webapp-demo/hello?user=黎志雄
 */
@WebServlet(urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        // 告诉tomcat容器，响应数据的数据格式与字符编码。否则会乱码。
        response.setContentType("text/html;charset=UTF-8");

        // 构造返回的数据流。下面构建html的文档内容。
        response.getWriter().println("<html>");
        response.getWriter().println("<h1>Hello Servlet</h1>");

        // java 8 的 Optional
        Optional.ofNullable(request.getParameter("user")).ifPresent((user) -> {
            try {
                response.getWriter().println("<h1>你好：" + user + "</h1>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // 构造响应数据
        response.getWriter().println("<h1>欢迎学习 Jakarta EE 课程，网络空间安全学院。</h1>");
        response.getWriter().println("</br>");
        response.getWriter().println("session=" + request.getSession(true).getId());
        response.getWriter().println("</html>");
    }
}
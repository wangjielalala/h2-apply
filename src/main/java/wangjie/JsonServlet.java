package wangjie;

import wangjie.jdbc.DBUtil;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * @author Sai
 * Created by Sai on 2019-03-15.
 */
@WebServlet(urlPatterns = {"/json"})
public class JsonServlet extends HttpServlet {

    // curl -v http://localhost:8080/webapp-demo/json
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 创建一个简单的Person对象,用作演示
        PersonDTO personDTO = new PersonDTO("黎志雄", 38);

        // 要获取对象的JSON表示，我们需要使用JsonbBuilder类及其toJson（）方法。
        Jsonb jsonb = JsonbBuilder.create();
        String jsonPerson = jsonb.toJson(personDTO);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().println(jsonPerson);
    }


    // 获取前端post提交的json字符串，并转换为对象；再把对象转为json字符串，生成响应返回前端。
    // curl -v -H "Content-Type: application/json" http://localhost:8080/webapp-demo/json -X POST --data '{"姓名":"黎志雄","工号":"2003066","注册日期":"15-03-2019","薪水":"1000.0","身份":"老师","邮件":"lizhx@dgut.edu.cn","部门":"网络空间安全 学院"}'
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Jsonb jsonb = JsonbBuilder.create();
        String postJsonString = new BufferedReader(new InputStreamReader(req.getInputStream()))
                                                            .lines().collect(Collectors.joining(System.lineSeparator()));

        // 利用jsonb把json字符串转换为对象
        PersonDTO personDTO = jsonb.fromJson(postJsonString, PersonDTO.class);

        Boolean bool = DBUtil.getDRIVER(personDTO);
        Response response;
        if(bool){
            response = new Response(200,"插入成功");
        }else {
            response = new Response(500,"插入失败");
        }

        // 把获取的字符串原样返回
        String jsonPerson = jsonb.toJson(response);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().println(jsonPerson);
    }
}

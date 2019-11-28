<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>demo页面</title>
</head>
<body>
<h1>
    <%
        // request.setCharacterEncoding("UTF-8");
        // 在浏览器地址栏输入下面的url:
        // http://localhost:8080/webapp-demo/index.jsp?name=黎志雄
        String name = request.getParameter("name");
        response.getWriter().println(name == null ? "无名" : name);
    %>
</h1>
<h2>Hello World! 东莞理工学院</h2>
</body>
</html>

package login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/login/check")
public class LoginCheck extends HttpServlet {
    private String message;

    @Override
    public void init() throws ServletException {
        message = "No info received";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置响应内容类型
        resp.setContentType("text/html");
        //设置逻辑实现
        PrintWriter out = resp.getWriter();
        String usn = req.getParameter("usn");
        String psw = req.getParameter("psw");
        if(!usn.equals("admin")) {
            message = "Fail";
        } else if(!psw.equals("admin")) {
            message = "Fail";
        } else {
            // 登陆成功
            // 将user存起来，请求重定向
            req.getSession().setAttribute("user", usn);
            message = "Succeed";
        }

//        // temp
//        if (usn.equals("admin") && psw.equals("admin")){
//            message = "Succeed";
//        }
//        else{
//            message = "Fail";
//        }

        out.print(message);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }


    @Override
    public void destroy() {
        super.destroy();
    }


}

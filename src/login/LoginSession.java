package login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/login/check_session")
public class LoginSession extends HttpServlet {
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

        String usn = (String)req.getSession().getAttribute("user");
        if (usn!=null){
            message = "Succeed";
        }
        else{
            message = "Failure";
        }
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

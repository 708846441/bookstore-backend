package login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import database.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@WebServlet("/login/check")
public class LoginCheck extends HttpServlet {

    private static final SessionFactory sessionFactory;

    // 加载配置文件，并创建表
    static {
        Configuration configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }


    private String message;

    @Override
    public void init() throws ServletException {
        message = "No info received";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // set up hibernate
        final Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try{
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String usn = req.getParameter("usn");
        String psw = req.getParameter("psw");

        CustomerEntity Cust = session.get(CustomerEntity.class, usn);
        if (Cust == null){
            message = "Unknown user";
        }
        else if (Cust.getPassword().equals(psw)){
            req.getSession().setAttribute("user", usn);
            message = "Succeed";
        }
        else{
            message = "Failure";
        }
        out.print(message);
        }
        catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    @Override
    public void destroy() {
        super.destroy();
    }


}

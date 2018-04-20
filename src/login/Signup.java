package login;

import database.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/signup/process")
public class Signup extends HttpServlet {

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

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String realname = req.getParameter("realname");
        String address = req.getParameter("address");

        CustomerEntity Cust = session.get(CustomerEntity.class, username);
        if (Cust != null){
            message = "Username used";
        }
        else{
            //write to database
            CustomerEntity newCustomer = new CustomerEntity();
            newCustomer.setAddress(address);
            newCustomer.setEmail(email);
            newCustomer.setName(realname);
            newCustomer.setPassword(password);
            newCustomer.setPhone(phone);
            newCustomer.setUsername(username);
            session.save(newCustomer);
            transaction.commit();
            //add session
            req.getSession().setAttribute("user", username);
            message = "Succeed";
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

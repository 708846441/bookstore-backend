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

@WebServlet("/profile/update")
public class ProfileUpdate extends HttpServlet {

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

        String usn = (String)req.getSession().getAttribute("user");
        try{
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();

            String phone = req.getParameter("phone");
            String email = req.getParameter("email");
            String name = req.getParameter("name");
            String address = req.getParameter("address");

            CustomerEntity Customer = session.get(CustomerEntity.class, usn);
            if (Customer == null){
                message = "Username not found";
            }
            else{
                //write to database
                Customer.setAddress(address);
                Customer.setEmail(email);
                Customer.setName(name);
                Customer.setPhone(phone);
                session.save(Customer);
                transaction.commit();
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

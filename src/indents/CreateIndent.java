package indents;

import database.BookEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import database.OrderformEntity;
import org.hibernate.criterion.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Book;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.OptionalDouble;

@WebServlet("/purchase/create_indent")
public class CreateIndent extends HttpServlet {

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
        if (usn==null){
            message = "Not signed in";
            return;
        }

        try{
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();

            List<OrderformEntity> orders = session.createSQLQuery("select * from orderform where username=\""+usn +"\"")
                    .addEntity(OrderformEntity.class)
                    .list();
            int len = orders.size();

            for (int i=0; i<len; i++){
                OrderformEntity order = orders.get(i);
                BookEntity book = order.getBookByBookId();


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

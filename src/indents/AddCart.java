package indents;

import database.BookEntity;
import database.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import database.OrderformEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/purchase/add_to_cart")
public class AddCart extends HttpServlet {

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
        }

        try{
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();

            String book_id = req.getParameter("ID");

            List<OrderformEntity> orders = session.createSQLQuery(
                    "select * from orderform \n where ((username=\""+usn+"\") and (book_id="+book_id +"))")
                    .addEntity(OrderformEntity.class)
                    .list();

            if(orders.size() == 0){
                // a new one
                OrderformEntity new_order = new OrderformEntity();
                new_order.setAmount(1);
                new_order.setCustomerByUsername(session.get(CustomerEntity.class, usn));
                new_order.setBookByBookId(session.get(BookEntity.class, Integer.parseInt(book_id)));
                new_order.setOrderId(usn+book_id);
                session.save(new_order);
                transaction.commit();
                message = "Succeed";
            }
            else if (orders.size()==1){
                OrderformEntity order = orders.get(0);
                order.setAmount(order.getAmount()+1);
                session.save(order);
                transaction.commit();
                message = "Succeed";
            }
            else{
                message = "Database error";
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

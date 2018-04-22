package bookinfo;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;



import database.BookEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@WebServlet("/getBook")
public class GetBook extends HttpServlet {
    private String message;

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

        {
            final Session session = getSession();
            Transaction transaction = session.beginTransaction();
            try{
                message = "hello from getbook";
            }
            catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
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
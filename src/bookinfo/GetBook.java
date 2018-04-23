package bookinfo;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Book;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;



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
                message = "Hello from getbook";
                StringBuffer buf = new StringBuffer();
                buf.append("[");
                List book_ids = session.createSQLQuery("select book_id from book")
                        .addScalar("book_id").list();
                int len = book_ids.size();
                for (int i=0; i<len; i++){
                    String id = book_ids.get(i).toString();
                    BookEntity Book = session.get(BookEntity.class, Integer.parseInt(id));
                    buf.append(
                            "{\"ID\" : \"" + id +
                                    "\", \"Name\" : \"" + Book.getBookName() +
                                    "\", \"Author\" : \"" +Book.getAuthor() +
                                    "\", \"Price\" : \"" +Book.getPrice() +
                                    "\", \"Sales\" : \"" +Book.getSales() +
                                    "\", \"Inventory\" : \"" +Book.getInventory() +
                                    "\", \"Language\" : \""+ Book.getLanguage() +"\"}");
                    if (i!=len-1){
                        buf.append(",");
                    }
                    else buf.append("]");
                }
                message = buf.toString();
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
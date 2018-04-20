package dataprocess;

import database.BookEntity;
import database.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.Date;

/**
 * 测试类
 *
 * @author RCNJTECH
 */
public class Test {
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

    public static void main(String[] args) {
        final Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            // 增
//            BookEntity B = new BookEntity();
//            B.setAuthor("nihao");
//            B.setBookId(1);
//            B.setBookName("tagaibianlezhongguo");
//            B.setInventory("kucunshu");
//            B.setLanguage("chinese");
//            B.setPrice(123);
//            B.setSales(1234);
//            session.save(B);

             //查
            CustomerEntity newCustomer = new CustomerEntity();
            newCustomer.setAddress("x11");
            newCustomer.setEmail("fytc@live.com");
            newCustomer.setName("123");
            newCustomer.setPassword("123");
            newCustomer.setPhone("1388888888888");
            newCustomer.setUsername("fycsss");
            session.save(newCustomer);

            // 改
            // Student student = session.get(Student.class, 100001);
            // student.setSex("女");
            // session.update(student);

            // 删
            // Student student = session.get(Student.class, 100001);
            // session.delete(student);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            int a=1;
            while (true){
                a++;
                if (a<1) break;
            }
            session.close();
            sessionFactory.close();
        }
    }
}
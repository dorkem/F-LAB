package Chapter01;

import java.sql.SQLException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import Chapter01.user.dao.DaoFactory;
import Chapter01.user.dao.UserDao;

@SpringBootApplication
public class TobyApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao1 = context.getBean(UserDao.class);
		UserDao dao2 = context.getBean(UserDao.class);

		System.out.println(dao1);
		System.out.println(dao2);
		// User user = new User();
		// user.setId("whiteship");
		// user.setName("Toby");
		// user.setPassword("123456");
		//
		// dao.add(user);
		//
		// System.out.println(user.getId() + " 등록 성공");
		//
		// User user2 = dao.get(user.getId());
		// System.out.println(user2.getName());
		// System.out.println(user2.getPassword());
		//
		// System.out.println(user2.getId() + " 조회 성공");
	}
}

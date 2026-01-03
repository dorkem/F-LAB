package Chapter01;

import java.sql.SQLException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import Chapter01.user.dao.DaoFactory;
import Chapter01.user.dao.UserDao;
import Chapter01.user.domain.User;

@SpringBootApplication
public class TobyApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean(UserDao.class);

		User user = new User();
		user.setId("whiteship");
		user.setName("Toby");
		user.setPassword("123456");

		dao.add(user);

		System.out.println(user.getId() + " 등록 성공");

		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());

		System.out.println(user2.getId() + " 조회 성공");
	}
}

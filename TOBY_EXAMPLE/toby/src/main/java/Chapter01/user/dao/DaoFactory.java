package Chapter01.user.dao;

public class DaoFactory {
	public UserDao userDao() {
		ConnectionMaker connectionMaker = new DConnectionMaker();
		UserDao dao = new UserDao(connectionMaker);
		return dao;
	}
}

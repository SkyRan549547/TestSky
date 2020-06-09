package mytestService;

public class myService {
	Connection con = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
	CallableStatment csm = con.getPrepareCall("");

}

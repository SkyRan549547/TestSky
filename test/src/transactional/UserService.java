@Service
public class UserService {
	@Autowired
	private RoncooUserDao roncooUserDao;
	@Autowired
	private RoncooUserLogDao roncooUserLogDao;
	/**
	* �û�ע��
	* 
	* @return
	Spring Boot �����̳�
	���ߣ�����ΰ
	3
	����ѧԺ��http://www.roncoo.com
	*/
	@Transactional
	public String register(String name, String ip) {
	// 1.����û�
	RoncooUser roncooUser = new RoncooUser();
	roncooUser.setName(name);
	roncooUser.setCreateTime(new Date());
	roncooUserDao.insert(roncooUser);
	// ����ʹ��
	boolean flag = true;
	if (flag) {
	throw new RuntimeException();
	}
	// 2.���ע����־
	RoncooUserLog roncooUserLog = new RoncooUserLog();
	roncooUserLog.setUserName(name);
	roncooUserLog.setUserIp(ip);
	roncooUserLog.setCreateTime(new Date());
	roncooUserLogDao.save(roncooUserLog);
	return "success";
	}
}
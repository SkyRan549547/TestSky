@Service
public class UserService {
	@Autowired
	private RoncooUserDao roncooUserDao;
	@Autowired
	private RoncooUserLogDao roncooUserLogDao;
	/**
	* 用户注册
	* 
	* @return
	Spring Boot 基础教程
	作者：冯永伟
	3
	龙果学院：http://www.roncoo.com
	*/
	@Transactional
	public String register(String name, String ip) {
	// 1.添加用户
	RoncooUser roncooUser = new RoncooUser();
	roncooUser.setName(name);
	roncooUser.setCreateTime(new Date());
	roncooUserDao.insert(roncooUser);
	// 测试使用
	boolean flag = true;
	if (flag) {
	throw new RuntimeException();
	}
	// 2.添加注册日志
	RoncooUserLog roncooUserLog = new RoncooUserLog();
	roncooUserLog.setUserName(name);
	roncooUserLog.setUserIp(ip);
	roncooUserLog.setCreateTime(new Date());
	roncooUserLogDao.save(roncooUserLog);
	return "success";
	}
}
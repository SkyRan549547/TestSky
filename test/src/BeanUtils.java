import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class BeanUtils {
	/**
	 * 
	 * 
	 * 
	 * @param source
	 *            被复制的实体类对象
	 * 
	 * @param to
	 *            复制完后的实体类对象
	 * 
	 * @throws Exception
	 */

	public static void Copy(Object source, Object to) throws Exception {  

		
		Class<?> srcClass = source.getClass();
		Class<?> toClass = to.getClass();
		Field[] sourceFies = srcClass.getDeclaredFields();
		Field[] toFies = toClass.getDeclaredFields();
		
		for(int i=0; i<sourceFies.length; i++){
			for(int n=0; n<toFies.length; n++){
				if(){
					
				}
			}
		}
//        // 获取属性
//        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(),java.lang.Object.class);
//        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();
//        BeanInfo destBean = Introspector.getBeanInfo(to.getClass(),java.lang.Object.class);
//        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();
//        try {  
//
//            for (int i = 0; i < sourceProperty.length; i++) {
//                for (int j = 0; j < destProperty.length; j++) {
//                	System.out.println("====sourceProperty=getName====="+sourceProperty[i].getName());
//                	System.out.println("====destProperty=getName====="+destProperty[j].getName());
//                	System.out.println("====sourceProperty=getReadMethod====="+sourceProperty[i].getReadMethod());
//                	System.out.println("====destProperty=getReadMethod====="+destProperty[j].getReadMethod());
//                    if (sourceProperty[i].getName().equals(destProperty[j].getName())) {
//                        // 调用source的getter方法和dest的setter方法 
//                        destProperty[j].getWriteMethod().invoke(to,sourceProperty[i].getReadMethod().invoke(source));
//                        break;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new Exception("属性复制失败:" + e.getMessage());
//        }
    }

	public class Demo02 {
		@SuppressWarnings("all")
		public void myTest(String[] args) throws Exception {
			// 另一个com.sg.myReflection.bean包下的User类
			String path = "com.sg.myReflection.bean.User";
			try {
				Class clazz = Class.forName(path);

				// 获取类名
				String strName01 = clazz.getName();// 获取完整类名com.sg.myReflection.bean.User
				String strName02 = clazz.getSimpleName();// 直接获取类名 User

				// 获取属性
				Field[] field01 = clazz.getFields(); // 返回属性为public的字段
				Field[] field02 = clazz.getDeclaredFields(); // 返回所有的属性
				Field field03 = clazz.getDeclaredField("id"); // 获取属性为id的字段

				// 获取普通方法
				Method[] Method01 = clazz.getDeclaredMethods(); // 返回public方法
				Method method = clazz.getDeclaredMethod("getId", null); // 返回getId这个方法，如果没有参数，就默认为null

				// // 获取构造方法
				// User u1 = (User) clazz.newInstance(); //
				// 获取无参的构造函数这里的前提的保证类中应该有无参的构造函数
				// // 获取参数为(int,String,int)的构造函数
				// Constructor c2 = clazz.getDeclaredConstructor(int.class,
				// String.class, int.class);
				// // 通过有参构造函数创建对象
				// User u2 = (User) c2.newInstance(1001, "小小", 18);
				//	  
				//	              
				// // 通过反射调用普通方法
				// User u3 = (User) clazz.newInstance();
				// Method method03 = clazz.getDeclaredMethod("setId",
				// int.class);
				// method.invoke(u3, 1002); // 把对象u3的id设置为1002
				//	  
				//	              
				//	              
				// // 通过反射操作普通的属性
				// User u4 = (User) clazz.newInstance();
				// Field f = clazz.getDeclaredField("name");
				// f.setAccessible(true); // 设置属性可以直接的进行访问
				// f.set(u4, "石头");

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static void getObjectValue(Object object) throws Exception {
		// 我们项目的所有实体类都继承BaseDomain （所有实体基类：该类只是串行化一下）
		// 不需要的自己去掉即可
		// if (object != null && object instanceof BaseDomain) {//if
		// (object!=null ) ----begin
		// 拿到该类
		Class<?> clz = object.getClass();
		// 获取实体类的所有属性，返回Field数组
		Field[] fields = clz.getDeclaredFields();

		for (Field field : fields) {// --for() begin
			System.out.println(field.getGenericType());// 打印该类的所有属性类型

			// 如果类型是String
			if (field.getGenericType().toString().equals(
					"class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
				// 拿到该属性的gettet方法
				/**
				 * 这里需要说明一下：他是根据拼凑的字符来找你写的getter方法的
				 * 在Boolean值的时候是isXXX（默认使用ide生成getter的都是isXXX）
				 * 如果出现NoSuchMethod异常 就说明它找不到那个gettet方法 需要做个规范
				 */
				Method m = (Method) object.getClass().getMethod(
						"get" + getMethodName(field.getName()));

				String val = (String) m.invoke(object);// 调用getter方法获取属性值
				if (val != null) {
					System.out.println("String type:" + val);
				}
			}

			// 如果类型是Integer
			if (field.getGenericType().toString().equals(
					"class java.lang.Integer")) {
				Method m = (Method) object.getClass().getMethod(
						"get" + getMethodName(field.getName()));
				Integer val = (Integer) m.invoke(object);
				if (val != null) {
					System.out.println("Integer type:" + val);
				}
			}

			// 如果类型是Double
			if (field.getGenericType().toString().equals(
					"class java.lang.Double")) {
				Method m = (Method) object.getClass().getMethod(
						"get" + getMethodName(field.getName()));
				Double val = (Double) m.invoke(object);
				if (val != null) {
					System.out.println("Double type:" + val);
				}
			}

			// 如果类型是Boolean 是封装类
			if (field.getGenericType().toString().equals(
					"class java.lang.Boolean")) {
				Method m = (Method) object.getClass()
						.getMethod(field.getName());
				Boolean val = (Boolean) m.invoke(object);
				if (val != null) {
					System.out.println("Boolean type:" + val);
				}
			}

			// 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
			// 反射找不到getter的具体名
			if (field.getGenericType().toString().equals("boolean")) {
				Method m = (Method) object.getClass()
						.getMethod(field.getName());
				Boolean val = (Boolean) m.invoke(object);
				if (val != null) {
					System.out.println("boolean type:" + val);
				}
			}
			// 如果类型是Date
			if (field.getGenericType().toString()
					.equals("class java.util.Date")) {
				Method m = (Method) object.getClass().getMethod(
						"get" + getMethodName(field.getName()));
				Date val = (Date) m.invoke(object);
				if (val != null) {
					System.out.println("Date type:" + val);
				}
			}
			// 如果类型是Short
			if (field.getGenericType().toString().equals(
					"class java.lang.Short")) {
				Method m = (Method) object.getClass().getMethod(
						"get" + getMethodName(field.getName()));
				Short val = (Short) m.invoke(object);
				if (val != null) {
					System.out.println("Short type:" + val);
				}
			}
			// 如果还需要其他的类型请自己做扩展

		}// for() --end

		// }//if (object!=null ) ----end
	}

	// 把一个字符串的第一个字母大写、效率是最高的、
	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
}

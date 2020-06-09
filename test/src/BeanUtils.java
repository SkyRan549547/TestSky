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
	 *            �����Ƶ�ʵ�������
	 * 
	 * @param to
	 *            ��������ʵ�������
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
//        // ��ȡ����
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
//                        // ����source��getter������dest��setter���� 
//                        destProperty[j].getWriteMethod().invoke(to,sourceProperty[i].getReadMethod().invoke(source));
//                        break;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new Exception("���Ը���ʧ��:" + e.getMessage());
//        }
    }

	public class Demo02 {
		@SuppressWarnings("all")
		public void myTest(String[] args) throws Exception {
			// ��һ��com.sg.myReflection.bean���µ�User��
			String path = "com.sg.myReflection.bean.User";
			try {
				Class clazz = Class.forName(path);

				// ��ȡ����
				String strName01 = clazz.getName();// ��ȡ��������com.sg.myReflection.bean.User
				String strName02 = clazz.getSimpleName();// ֱ�ӻ�ȡ���� User

				// ��ȡ����
				Field[] field01 = clazz.getFields(); // ��������Ϊpublic���ֶ�
				Field[] field02 = clazz.getDeclaredFields(); // �������е�����
				Field field03 = clazz.getDeclaredField("id"); // ��ȡ����Ϊid���ֶ�

				// ��ȡ��ͨ����
				Method[] Method01 = clazz.getDeclaredMethods(); // ����public����
				Method method = clazz.getDeclaredMethod("getId", null); // ����getId������������û�в�������Ĭ��Ϊnull

				// // ��ȡ���췽��
				// User u1 = (User) clazz.newInstance(); //
				// ��ȡ�޲εĹ��캯�������ǰ��ı�֤����Ӧ�����޲εĹ��캯��
				// // ��ȡ����Ϊ(int,String,int)�Ĺ��캯��
				// Constructor c2 = clazz.getDeclaredConstructor(int.class,
				// String.class, int.class);
				// // ͨ���вι��캯����������
				// User u2 = (User) c2.newInstance(1001, "СС", 18);
				//	  
				//	              
				// // ͨ�����������ͨ����
				// User u3 = (User) clazz.newInstance();
				// Method method03 = clazz.getDeclaredMethod("setId",
				// int.class);
				// method.invoke(u3, 1002); // �Ѷ���u3��id����Ϊ1002
				//	  
				//	              
				//	              
				// // ͨ�����������ͨ������
				// User u4 = (User) clazz.newInstance();
				// Field f = clazz.getDeclaredField("name");
				// f.setAccessible(true); // �������Կ���ֱ�ӵĽ��з���
				// f.set(u4, "ʯͷ");

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static void getObjectValue(Object object) throws Exception {
		// ������Ŀ������ʵ���඼�̳�BaseDomain ������ʵ����ࣺ����ֻ�Ǵ��л�һ�£�
		// ����Ҫ���Լ�ȥ������
		// if (object != null && object instanceof BaseDomain) {//if
		// (object!=null ) ----begin
		// �õ�����
		Class<?> clz = object.getClass();
		// ��ȡʵ������������ԣ�����Field����
		Field[] fields = clz.getDeclaredFields();

		for (Field field : fields) {// --for() begin
			System.out.println(field.getGenericType());// ��ӡ�����������������

			// ���������String
			if (field.getGenericType().toString().equals(
					"class java.lang.String")) { // ���type�������ͣ���ǰ�����"class "�����������
				// �õ������Ե�gettet����
				/**
				 * ������Ҫ˵��һ�£����Ǹ���ƴ�յ��ַ�������д��getter������
				 * ��Booleanֵ��ʱ����isXXX��Ĭ��ʹ��ide����getter�Ķ���isXXX��
				 * �������NoSuchMethod�쳣 ��˵�����Ҳ����Ǹ�gettet���� ��Ҫ�����淶
				 */
				Method m = (Method) object.getClass().getMethod(
						"get" + getMethodName(field.getName()));

				String val = (String) m.invoke(object);// ����getter������ȡ����ֵ
				if (val != null) {
					System.out.println("String type:" + val);
				}
			}

			// ���������Integer
			if (field.getGenericType().toString().equals(
					"class java.lang.Integer")) {
				Method m = (Method) object.getClass().getMethod(
						"get" + getMethodName(field.getName()));
				Integer val = (Integer) m.invoke(object);
				if (val != null) {
					System.out.println("Integer type:" + val);
				}
			}

			// ���������Double
			if (field.getGenericType().toString().equals(
					"class java.lang.Double")) {
				Method m = (Method) object.getClass().getMethod(
						"get" + getMethodName(field.getName()));
				Double val = (Double) m.invoke(object);
				if (val != null) {
					System.out.println("Double type:" + val);
				}
			}

			// ���������Boolean �Ƿ�װ��
			if (field.getGenericType().toString().equals(
					"class java.lang.Boolean")) {
				Method m = (Method) object.getClass()
						.getMethod(field.getName());
				Boolean val = (Boolean) m.invoke(object);
				if (val != null) {
					System.out.println("Boolean type:" + val);
				}
			}

			// ���������boolean �����������Ͳ�һ�� �����е�˵������������� isXXX�� �Ǿ�ȫ����isXXX��
			// �����Ҳ���getter�ľ�����
			if (field.getGenericType().toString().equals("boolean")) {
				Method m = (Method) object.getClass()
						.getMethod(field.getName());
				Boolean val = (Boolean) m.invoke(object);
				if (val != null) {
					System.out.println("boolean type:" + val);
				}
			}
			// ���������Date
			if (field.getGenericType().toString()
					.equals("class java.util.Date")) {
				Method m = (Method) object.getClass().getMethod(
						"get" + getMethodName(field.getName()));
				Date val = (Date) m.invoke(object);
				if (val != null) {
					System.out.println("Date type:" + val);
				}
			}
			// ���������Short
			if (field.getGenericType().toString().equals(
					"class java.lang.Short")) {
				Method m = (Method) object.getClass().getMethod(
						"get" + getMethodName(field.getName()));
				Short val = (Short) m.invoke(object);
				if (val != null) {
					System.out.println("Short type:" + val);
				}
			}
			// �������Ҫ�������������Լ�����չ

		}// for() --end

		// }//if (object!=null ) ----end
	}

	// ��һ���ַ����ĵ�һ����ĸ��д��Ч������ߵġ�
	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
}

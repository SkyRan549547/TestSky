import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class BeanUtils {

//	BeanUtils.copyProperties(to, from); 是全部属性copy类似于覆盖
	private static void copyProperties(Object o1, Object o2) {

		String fileName,str,getName,setName;
		List fields = new ArrayList();
		Method getMethod = null;
		Method setMethod = null;
		try {
		Class c1 = o1.getClass();
		Class c2 = o2.getClass();
		Field[] fs1 = c1.getDeclaredFields();
		Field[] fs2 = c2.getDeclaredFields();
		//两个类属性比较剔除不相同的属性，只留下相同的属性
		for(int i = 0;i < fs2.length;i++) {
			for(int j = 0;j < fs1.length;j++) {
				if(fs1[j].getName().equals(fs2[i].getName())) {
					fields.add(fs1[j]);
					break;
				}
			}
		}
		if(null != fields && fields.size() > 0){
			for (int i = 0;i < fields.size();i++) {
			//获取属性名称
			Field f = (Field) fields.get(i);
			fileName = f.getName();
			//属性名第一个字母大写
			str = fileName.substring(0, 1).toUpperCase();
			//拼凑getXXX和setXXX方法名
			getName = "get" + str + fileName.substring(1);
			setName = "set" + str + fileName.substring(1);
			//获取get、set方法
			getMethod = c1.getMethod(getName, new Class[]{});
            setMethod = c2.getMethod(setName, new Class[]{f.getType()});
           
            //获取属性值
            Object o = getMethod.invoke(o1, new Object[]{});
            System.out.println(fileName + " : " + o);
            //将属性值放入另一个对象中对应的属性
            if(null != o) {
	             System.out.println("o2.setMethod = " + setMethod);
	             setMethod.invoke(o2, new Object[] {o});
            	}
			}
		}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**

	 * 

	 * @param source 被复制的实体类对象

	 * @param to 复制完后的实体类对象  

	 * @throws Exception

	 */

	public static void Copy(Object source, Object to) throws Exception {  

        // 获取属性
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(),java.lang.Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();
        BeanInfo destBean = Introspector.getBeanInfo(to.getClass(),java.lang.Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();
        try {  

            for (int i = 0; i < sourceProperty.length; i++) {
                for (int j = 0; j < destProperty.length; j++) {
                    if (sourceProperty[i].getName().equals(destProperty[j].getName())) {
                        // 调用source的getter方法和dest的setter方法 
                        destProperty[j].getWriteMethod().invoke(to,sourceProperty[i].getReadMethod().invoke(source));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("属性复制失败:" + e.getMessage());
        }
    }  


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

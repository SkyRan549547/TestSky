package study.java8.lambda.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

import study.java8.lambda.apple.Apple;
import study.java8.lambda.apple.FilterApple;
import study.java8.lambda.apple.FindGreenApple;

public class AppleTest {
	
	public static void main(String[] args) {
		FindGreenApple find = new FindGreenApple();
		FilterApple filterApple = new FilterApple();
		List<Apple> apples =new ArrayList <Apple>();
		Apple apple0 = new Apple("Green", 150);
		Apple apple1 = new Apple("Red", 200);
		Apple apple2 = new Apple("Yellow", 350);
		Apple apple3 = new Apple("Black", 250);
		Apple apple5 = new Apple("Yellow", 350);
		apples.add(apple0);
		apples.add(apple1);
		apples.add(apple2);
		apples.add(apple3);
		apples.add(apple5);
		List<Apple> list =filterApple.getAplles(apples, find);
//		System.out.println(list);
//		System.out.println(apples.lastIndexOf(apple5));
//		
//		StringBuffer buf;
//		StringBuilder bui;
//		List link = new LinkedList ();
//		Iterator it = link.iterator();
//		while(it.hasNext()){
//			System.out.println(it.next());
//		}
//		String str ="1,2,3,4,5,6,7,8,9,0";
//		System.out.println(str.charAt(1));
//		System.out.println(str.indexOf(","));
//		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("111", "zhang");
		map.put("222", "Li");
		map.put("333", "Wang");
		map.put("444", "Yang");
		map.put("555", "Si");
		map.put("666", "San");
		Iterator entryIt =map.entrySet().iterator();
		while(entryIt.hasNext()){
			System.out.println(entryIt.next());
		}
		System.out.println("=========================");
		Iterator keyIt =map.keySet().iterator();
		while(keyIt.hasNext()){
			System.out.println(keyIt.next());
		}
	}
	
}

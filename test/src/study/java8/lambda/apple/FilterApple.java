package study.java8.lambda.apple;

import java.util.ArrayList;
import java.util.List;

import study.java8.lambda.inter.AppleFilter;

public class FilterApple {

	public List<Apple> getAplles(List<Apple> apples, AppleFilter filter) {
		List<Apple> results = new ArrayList<Apple>();
		for (Apple apple : apples) {
			if (filter.isApple(apple)) {
				results.add(apple);
			}
		}
		return results;

	}
}

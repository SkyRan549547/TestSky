package study.java8.lambda.apple;

import study.java8.lambda.inter.AppleFilter;

public class FindGreenApple implements AppleFilter {

	public boolean isApple(Apple apple) {
		if ("Green".equals(apple.getColor())) {
			return true;
		}
		return false;
	}

}

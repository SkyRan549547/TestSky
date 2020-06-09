package algorithm;

public class FastAlgorithm {

	/***
	 * 快速排序原理
	 * 
	 * @param args
	 * @return
	 */
	public int[] runkInts(int[] args) {
		int count = args.length;
		int[] right = new int[count];
		int[] left = new int[count];
		int leftIndex = 0;
		int rightIndex = 0;
		int end = count - 1;
		int endIndex = count - 1;
		int startIndex = 0;
		while (endIndex > startIndex) {
			if (args != null && count > 0) {
				int mid = args[end];
				rightIndex = count - 1;
				for (int i = 0; i < args.length - 1; i++) {
					if (mid >= args[i]) {
						int a;
						a = args[i];
						right[leftIndex] = a;
						rightIndex++;
						startIndex++;
					}
				}
				for (int j = 0; j < args.length; j++) {
					if (mid < args[j]) {
						int b;
						b = args[j];
						left[leftIndex] = b;
						leftIndex++;
						endIndex--;
					}
				}
				runkInts(right);
				runkInts(left);
			}
		}
		return null;
	}
}

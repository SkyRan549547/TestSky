package Nio;

public class mian {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String resFile="C:\\Users\\Administrator\\Desktop\\copy13.txt";
		String destFile="C:\\Users\\Administrator\\Desktop\\copy.txt";
		String destFile1="C:\\Users\\Administrator\\Desktop\\copy1.txt";
		long start =  System.currentTimeMillis();
		ReadFile rd = new ReadFile();
//		for(int i=0;i<1000; i++){
			rd.nioCope(resFile, destFile);
//		}
		long nioEnd=System.currentTimeMillis();
		System.out.println("NioºÄÊ±£º"+(nioEnd-start));
		
		for(int i=0;i<1000; i++){
			rd.ioCope(resFile, destFile1);
		}
		System.out.println("ioºÄÊ±£º"+(System.currentTimeMillis()-nioEnd));
	}

}

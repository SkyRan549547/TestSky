package Nio;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ReadFile extends Object {
	public void nioCope(String resFile, String destFile) throws Exception {
		FileInputStream fis = new FileInputStream(resFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		FileChannel readChennal = fis.getChannel();
		FileChannel writeChennal = fos.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (true) {
			buffer.clear();
			System.out.println("111111:" + buffer.toString());
			int len = readChennal.read(buffer);
			System.out.println("222222:" + buffer.toString());
			System.out.println("333333:" + len);
			if (len == -1) {
				break;
			}
			buffer.flip();
			System.out.println("444444:" + buffer.toString());
			writeChennal.write(buffer);
			System.out.println("555555:" + buffer.toString());
			System.out.println("");
		}
		readChennal.close();
		writeChennal.close();
	}

	public void ioCope(String resFile, String destFile) throws Exception {
		FileInputStream fis = new FileInputStream(resFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		byte[] buffer = new byte[1024 * 8];
		int count = 0;
		ArrayList array;
		Comparable<String> com;

		List a;
		Collections cons;
		while ((count = fis.read(buffer, 0, 1)) != -1) {
			fos.write(buffer, 0, count);
			fos.flush();
			// fos.write(buffer);
		}
		fis.close();
		fos.close();
	}
}

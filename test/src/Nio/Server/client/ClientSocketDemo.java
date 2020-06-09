package Nio.Server.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ClientSocketDemo {
	public static void clientStart() throws Exception {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("localhost", 8989));
		// socketChannel.configureBlocking(false);
		String request = "hello word ServerSocketChannel";
		ByteBuffer buf = ByteBuffer.wrap(request.getBytes("UTF-8"));
		socketChannel.write(buf);
		ByteBuffer responseByte = ByteBuffer.allocate(48);
		int size = socketChannel.read(responseByte);
		while (size > 0) {
			responseByte.flip();
			Charset charset = Charset.forName("UTF-8");
			System.out.println(charset.newDecoder().decode(responseByte));
			size = socketChannel.read(responseByte);
		}
		responseByte.clear();
		socketChannel.close();
	}

	public static void main(String[] args) {
		try {
			clientStart();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

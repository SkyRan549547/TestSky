package Nio.Socket.Server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ServerSocketDemo {

	public static void serverStart() throws Exception {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(8989));
		serverSocketChannel.configureBlocking(false);
		while (true) {
			SocketChannel socketChannel = serverSocketChannel.accept();
			if (null != socketChannel) {
				ByteBuffer buf = ByteBuffer.allocate(48);
				int size = socketChannel.read(buf);
				while (size > 0) {
					buf.flip();
					Charset charset = Charset.forName("UTF-8");
					System.out.println(charset.newDecoder().decode(buf));
					size = socketChannel.read(buf);
				}
				buf.clear();
				ByteBuffer response = ByteBuffer
						.wrap("response ServerSocketDemo".getBytes("UTF-8"));
				socketChannel.write(response);
				response.clear();
				socketChannel.close();
			}

		}

	}

	public static void main(String[] args) {
		try {
			serverStart();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

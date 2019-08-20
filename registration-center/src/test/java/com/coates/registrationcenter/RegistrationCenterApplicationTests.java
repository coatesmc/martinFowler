package com.coates.registrationcenter;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Scanner;


public class RegistrationCenterApplicationTests {

    @Test
    public void contextLoads() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        byteBuffer.put("31412421".getBytes());
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println(new String(bytes, 0, byteBuffer.limit()));
    }

    @Test
    public void Test2() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        byteBuffer.put("31412421".getBytes());

        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes, 0, 2);
        byteBuffer.mark();
        System.out.println(new String(bytes, 0, 2));
        System.out.println(byteBuffer.position());
        byteBuffer.mark();
        bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes, 2, 2);
        System.out.println(new String(bytes, 2, 2));
        System.out.println(byteBuffer.position());
    }

    @Test
    public void Test3() throws IOException {
        long start = System.currentTimeMillis();
        FileChannel inchannel = FileChannel.open(Paths.get("E:\\学习视频\\学习资料\\1期\\0027-每特教育(蚂蚁课堂)-深入理解Java虚拟机\\课前疑问.mp4"),
                StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("E:\\学习视频\\学习资料\\1期\\0027-每特教育(蚂蚁课堂)-深入理解Java虚拟机\\2.mp4"),
                StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        //内存映射
        MappedByteBuffer inMappedByteBuffer = inchannel.map(FileChannel.MapMode.READ_ONLY, 0, inchannel.size());
        MappedByteBuffer outMappedByteBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inchannel.size());
        //直接写入缓冲区
        byte[] dsf = new byte[inMappedByteBuffer.limit()];
        inMappedByteBuffer.get(dsf);
        outMappedByteBuffer.put(dsf);
        inchannel.close();
        outChannel.close();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void Test4() throws IOException {
        long start = System.currentTimeMillis();
        FileInputStream inputStream = new FileInputStream("E:\\学习视频\\学习资料\\1期\\0027-每特教育(蚂蚁课堂)-深入理解Java虚拟机\\课前疑问.mp4");
        FileOutputStream outputStream = new FileOutputStream("E:\\学习视频\\学习资料\\1期\\0027-每特教育(蚂蚁课堂)-深入理解Java虚拟机\\3.mp4");

        FileChannel inChannel = inputStream.getChannel();
        FileChannel outChannel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (inChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            outChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        outChannel.close();
        inChannel.close();
        inChannel.close();
        outChannel.close();
        long end = System.currentTimeMillis();

        System.out.println(end - start);

    }


    @Test
    public void Test5() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("E:\\学习视频\\学习资料\\2期\\0009-蚂蚁课堂(每特学院)-2期-NIO编程基础\\课前疑问.mp4", "rw");
        FileChannel channel = raf.getChannel();

        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        ByteBuffer[] bufs = {buf1, buf2};
        channel.read(bufs);
        for (ByteBuffer buf : bufs) {
            buf.flip();
        }

        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("------------------分算读取线分割--------------------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));


        RandomAccessFile raf2 = new RandomAccessFile("e:\\2.mp4", "rw");
        FileChannel channel1 = raf2.getChannel();
        channel1.write(bufs);

    }

    @Test
    public void Test006() throws CharacterCodingException {
        // 获取编码器
        Charset cs1 = Charset.forName("GBK");
        // 获取编码器
        CharsetEncoder ce = cs1.newEncoder();
        // 获取解码器
        CharsetDecoder cd = cs1.newDecoder();
        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("蚂蚁课堂牛逼!");
        cBuf.flip();
        // 编码
        ByteBuffer bBuf = ce.encode(cBuf);
        for (int i = 0; i < 12; i++) {
            System.out.println(bBuf.get());
        }
        // 解码
        bBuf.flip();
        CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());
        System.out.println("-------------------------------------");
        Charset cs2 = Charset.forName("GBK");
        bBuf.flip();
        CharBuffer cbeef = cs2.decode(bBuf);
        System.out.println(cbeef.toString());
    }

    public static void main(String[] args) throws IOException {
        System.out.println("socket tcp客户端。。。");
        //ServerSocket serverSocket = new ServerSocket(8080);
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        socketChannel.configureBlocking(false);
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.next();
            allocate.put((new Date().toString() + "\n" + str).getBytes());
            allocate.flip();
            socketChannel.write(allocate);
            allocate.clear();
        }
        socketChannel.close();
    }


}

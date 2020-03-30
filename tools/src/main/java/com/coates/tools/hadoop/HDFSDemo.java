package com.coates.tools.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * @ClassName HDFSDemo
 * @Description TODO
 * @Author mc
 * @Date 2019/11/5 15:52
 * @Version 1.0
 **/
public class HDFSDemo {

    private static FileSystem fs = null;
    private static Configuration config = null;

    static {
        try {
            config = new Configuration();
            config.set("fs.defaultFS", "hdfs://master:9000");
            fs = FileSystem.get(new URI("hdfs://master:9000"), config, "root");
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String fileUrl = "C:\\Users\\Administrator\\Desktop\\test.txt";
        //Job job = Job
        //createMkdirs();
        //uploadFile(config,fileUrl);
        //testDownload();
        //testReadData();
        //estLs();
        //testRandomReadData();
        deleteFile("/test.txt");
    }


    /**
     * 创建目录
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    private static void createMkdirs() throws IOException, InterruptedException, URISyntaxException {
        boolean mkdirs = fs.mkdirs(new Path("/testOne"));
        System.out.println(mkdirs);
    }

    /**
     * 上传文件
     *
     * @param fileUrl
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    private static void uploadFile(String fileUrl) throws IOException, URISyntaxException, InterruptedException {
        InputStream in = new FileInputStream(fileUrl);
        OutputStream outputStream = fs.create(new Path("/test.txt"), new Progressable() {
            @Override
            public void progress() {
                System.out.println("上传完一个设定缓存区大小容量的文件！");
            }
        });
        IOUtils.copyBytes(in, outputStream, config);
    }

    /**
     * 下载文件
     *
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    public static void testDownload() throws IOException, InterruptedException, URISyntaxException {
        //如果为null,重启myeclipse/eclipse即可
        System.out.println(System.getenv("HADOOP_HOME"));
        fs.copyToLocalFile(new Path("/测试/apache-maven-3.6.3-bin.tar.gzip"), new Path("d:/apache-maven-3.6.3-bin.tar.gzip"));
        fs.close();
    }

    /**
     * 获取文件内容
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public static void testReadData() throws URISyntaxException, IOException, InterruptedException {
        FSDataInputStream in = fs.open(new Path("/test.txt"));//hdfs自带流打开文件
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));//读入流并放在缓冲区
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        in.close();
        br.close();
        fs.close();
    }

    /**
     * 获取文件信息 ls
     * @throws IOException
     */
    public static void testLs() throws IOException {
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus status = listFiles.next();
            System.out.println("路径：" + status.getPath());
            System.out.println("块大小：" + status.getBlockSize());
            System.out.println("文件长度：" + status.getLen());
            System.out.println("副本数:" + status.getReplication());
            System.out.println("块的位置信息：" + Arrays.toString(status.getBlockLocations()) + "\n");
        }
        fs.close();
    }

    /**
     * 读取hdfs文件内容
     * @throws IOException
     */
    public static void testRandomReadData() throws IOException {
        FSDataInputStream in = fs.open(new Path("/test.txt"));
        in.seek(3);
        byte[] buf = new byte[16];
        in.read(buf);
        System.out.println(new String(buf));
        in.close();
        fs.close();
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @throws IOException
     */
    public static void deleteFile(String fileName) throws IOException {
        Path f = new Path(fileName);
        boolean isExists = fs.exists(f);
        if (isExists) { // if exists, delete
            boolean isDel = fs.delete(f, true);
        } else {
            System.out.println(fileName + "  exist? " + isExists);
        }
    }

}

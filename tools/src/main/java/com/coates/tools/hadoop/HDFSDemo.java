package com.coates.tools.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @ClassName HDFSDemo
 * @Description TODO
 * @Author mc
 * @Date 2019/11/5 15:52
 * @Version 1.0
 **/
public class HDFSDemo {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Configuration config = new Configuration();
        config.set("fs.defaultFS", "hdfs://node01:9000");
          String fileUrl = "C:\\Users\\Administrator\\Desktop\\线上数据备份\\company_b.zip";
        //Job job = Job
        //createMkdirs(config);
        //uploadFile(config,fileUrl);
        testDownload(config,fileUrl);

    }

    //创建目录
    private static void createMkdirs(Configuration config) throws IOException, InterruptedException, URISyntaxException {
        FileSystem fs = FileSystem.get(new URI("hdfs://node01:9000"), config, "root");
        boolean mkdirs = fs.mkdirs(new Path("/testOne"));
        System.out.println(mkdirs);
    }

    /**
     * 上传文件
     * @param config
     * @param fileUrl
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    private static void uploadFile(Configuration config,String fileUrl) throws IOException, URISyntaxException, InterruptedException {
        FileSystem fs = FileSystem.get(new URI("hdfs://node01:9000"), config, "root");
        InputStream in = new FileInputStream(fileUrl);
        OutputStream outputStream = fs.create(new Path("/testOne/upload"), new Progressable(){
            @Override
            public void progress() {
                System.out.println("上传完一个设定缓存区大小容量的文件！");
            }
        });
        IOUtils.copyBytes(in,outputStream,config);
         /*byte[] buffer = new byte[1024];
        int len = 0;
		while((len=in.read(buffer))>0){
			out.write(buffer, 0, len);
		}
		out.flush();
		in.close();
		out.close();*/

    }

    public static void testDownload(Configuration config,String fileUrl) throws IOException, InterruptedException, URISyntaxException {
        FileSystem fs = FileSystem.get(new URI("hdfs://node01:9000"), config, "root");
//		InputStream in = new FileInputStream("e:\\hadoop.avi");
//		OutputStream out = fs.create(new Path("/demo/b.avi"));
        InputStream in = fs.open(new Path("/testOne/upload/company_b.zip"));
        FileOutputStream out = new FileOutputStream("E:\\company_b.zip");
        IOUtils.copyBytes(in, out, 2048);

      /*byte[] buffer = new byte[1024];
		int len = 0;
		while((len=in.read(buffer))>0){
			out.write(buffer, 0, len);
		}
		out.flush();
		in.close();
		out.close();*/
    }


    private static void createDataSource(Configuration config) throws IOException {

    }


}

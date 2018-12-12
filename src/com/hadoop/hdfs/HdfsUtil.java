package com.hadoop.hdfs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.server.namenode.status_jsp;
import org.junit.Before;
import org.junit.Test;


public class HdfsUtil {
	FileSystem fs = null;
	@Before
	public void init() throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoop:9000/");
		fs = FileSystem.get(new URI("hdfs://hadoop:9000/"),conf,"root");
	}
	//上传文件
	@Test
	public void upload() throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoop:9000/");
		FileSystem fs = FileSystem.get(conf);
		Path sr = new Path("hdfs://hadoop:9000/test/test3.txt");
		FSDataOutputStream os = fs.create(sr);
		FileInputStream is = new FileInputStream("D:/hadoop/test/test.txt");
		IOUtils.copy(is, os);
	}
	//封装好的文件上传
	@Test
	public void upload2() throws Exception, IOException {
		fs.copyFromLocalFile(new Path("D:/hadoop/test/test.txt"),
				new Path("hdfs://hadoop:9000/test/test6.txt"));
	}
	//下载文件
	@Test
	public void download() throws Exception, IOException {
		fs.copyToLocalFile(new Path("hdfs://hadoop:9000/test/test2.txt"), 
				new Path("D:/hadoop/test/test4.txt"));
	}
	//查看文件信息
	@Test
	public void listFiles() throws FileNotFoundException, IllegalArgumentException, IOException {
		RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("/"), true);
		while(files.hasNext()) {
			LocatedFileStatus file = files.next();
			Path path = file.getPath();
			String filename = path.getName();
			System.out.println(filename);
		}
		System.out.println("------我是分割线------");
		FileStatus[] liststatus = fs.listStatus(new Path("/"));
		for (FileStatus fileStatus : liststatus) {
			String name = fileStatus.getPath().getName();
			System.out.println(name+(fileStatus.isDirectory()?" is dir":" is file"));
		}
	}
	//创建目录
	@Test
	public void mkdir() throws IllegalArgumentException, IOException {
		fs.mkdirs(new Path("/a/b/c"));
	}
	//删除目录
	@Test
	public void rm() throws IllegalArgumentException, IOException {
		fs.delete(new Path("/flow/output"), true);
	}
	
	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoop:9000/");
		FileSystem fs = FileSystem.get(conf);
		FSDataInputStream is = fs.open(new Path("/jdk-7u65-linux-i586.tar.gz"));
		FileOutputStream os = new FileOutputStream("D:/hadoop/test/jdk7.tgz");
		IOUtils.copy(is, os);
	}
}

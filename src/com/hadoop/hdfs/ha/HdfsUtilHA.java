package com.hadoop.hdfs.ha;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsUtilHA {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://ns1/"), conf, "root");
		fs.copyFromLocalFile(new Path("D:/hadoop/test/test.txt"), new Path("hdfs://ns1/"));
	}
}

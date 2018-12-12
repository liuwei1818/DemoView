package com.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class WordCountRunner {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job wcjob = Job.getInstance(conf);
		
		wcjob.setJarByClass(WordCountRunner.class);
		
		wcjob.setMapperClass(WordCountMapper.class);
		wcjob.setReducerClass(WordCountReduce.class);
		
		wcjob.setOutputKeyClass(Text.class);
		wcjob.setOutputValueClass(LongWritable.class);
		
		wcjob.setMapOutputKeyClass(Text.class);
		wcjob.setMapOutputValueClass(LongWritable.class);
		
		FileInputFormat.setInputPaths(wcjob, new Path("/wordcount/srcdata/"));
		FileOutputFormat.setOutputPath(wcjob, new Path("/wordcount/output"));
		
		wcjob.waitForCompletion(true);
	}
}

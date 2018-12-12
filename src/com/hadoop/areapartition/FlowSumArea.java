package com.hadoop.areapartition;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.hadoop.flowsum.FlowBean;

public class FlowSumArea {
	public static class FlowSumAreaMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {
			
			String line = value.toString();
			String[] fields = StringUtils.split(line, "\t");
			
			String phoneNB = fields[1];
			long u_flow = Long.parseLong(fields[7]);
			long d_flow = Long.parseLong(fields[8]);
			
			context.write(new Text(phoneNB), new FlowBean(phoneNB,u_flow,d_flow));
		}
	}
	
	public static class FlowSumAreaReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
		@Override
		protected void reduce(Text key, Iterable<FlowBean> values,Context context)
				throws IOException, InterruptedException {
			long up_flow_counter = 0;
			long d_flow_counter = 0;
			
			for (FlowBean flowBean : values) {
				up_flow_counter += flowBean.getUp_flow();
				d_flow_counter += flowBean.getD_flow();
			}
			
			context.write(key, new FlowBean(key.toString(), up_flow_counter, d_flow_counter));
		}
	} 
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(FlowSumArea.class);
		
		job.setMapperClass(FlowSumAreaMapper.class);
		job.setReducerClass(FlowSumAreaReducer.class);
		
		job.setPartitionerClass(AreaPartitioner.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);
		
		//����reduce�����񲢷�����Ӧ��������������һ��
		job.setNumReduceTasks(6);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true)?0:1);
	}
}

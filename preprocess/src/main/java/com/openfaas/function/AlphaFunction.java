package com.openfaas.function;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;

public class AlphaFunction {
    public static void main(String[] args) throws IOException {
        System.setProperty("hadoop.home.dir", "/");
        BasicConfigurator.configure();
        // Create a configuration object for the job
        Configuration jobConfig = new Configuration();
        Job jobClient = new Job(jobConfig);
        // Set a name of the Job
        jobClient.setJobName("FaresPerQuarter");

        // Specify data type of output key and value
        jobClient.setOutputKeyClass(Text.class);
        jobClient.setOutputValueClass(IntWritable.class);

        // Specify names of Mapper and Reducer Class
        jobClient.setMapperClass(AlphaMapper.class);
        jobClient.setReducerClass(AlphaReducer.class);

        // Specify formats of the data type of Input and output
        jobClient.setOutputKeyClass(TextInputFormat.class);
        jobClient.setOutputValueClass(TextOutputFormat.class);

        // Set input and output directories using command line arguments,
        //arg[0] = name of input directory on HDFS, and arg[1] =  name of output directory to be created to store the output file.

        FileInputFormat.addInputPath(jobClient, new Path(args[0]));
        FileOutputFormat.setOutputPath(jobClient, new Path(args[1]));

        try {
            // Run the job
            jobClient.waitForCompletion(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

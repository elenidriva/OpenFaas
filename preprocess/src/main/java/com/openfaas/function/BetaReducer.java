package com.openfaas.function;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class BetaReducer extends Reducer<Text, Iterator<Boolean>, Text, IntWritable> {

    public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Context context) throws IOException {
        int frequencyForCountry = 0;
        while (values.hasNext()) {
            // replace type of value with the actual type of our value
            IntWritable actualValue = values.next();
            frequencyForCountry += actualValue.get();
        }
        output.collect(key, new IntWritable(frequencyForCountry));
    }
}
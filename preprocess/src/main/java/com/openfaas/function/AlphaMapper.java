package com.openfaas.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

// A mapper class converting each line of input into a key/value pair
// Each character is turned to a key with value as 1
public class AlphaMapper extends Mapper<Text, Text, Integer, LongWritable> {

    private static final String COMMA_DELIMETER = ",";
    private final ObjectMapper objectMapper;
    private final static LongWritable one = new LongWritable(1);

    public AlphaMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        String jsonString = value.toString();
        Fare fare = objectMapper.convertValue(jsonString, Fare.class);
/*        fares.forEach(fare -> {
            generatedKey.set(fare.getId());
            try {
                context.write(generatedKey, new Text(fare.getPickupLatitude() + COMMA_DELIMETER + fare.getPickupLongitude()));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });*/
        double latitude = fare.getPickupLatitude();
        double longitude = fare.getPickupLongitude();

        if (latitude > 25.00 && longitude < 25.00) {
            context.write(1, one);
        } else if (latitude > 25.00 && longitude > 25.00) {
            context.write(2, one);
        } else if (latitude < 25.00 && longitude < 25.00) {
            context.write(3, one);
        } else {
            context.write(4, one);
        }
    }
}
package com.openfaas.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.lucene.util.SloppyMath;

import java.io.IOException;

// A mapper class converting each line of input into a key/value pair
// Each character is turned to a key with value as 1
public class BetaMapper extends Mapper<Text, Text, Text, Text> {

    private final ObjectMapper objectMapper;
    private final static LongWritable one = new LongWritable(1);

    public BetaMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        String jsonString = value.toString();
        Fare fare = objectMapper.convertValue(jsonString, Fare.class);
        // https://timepasstechies.com/map-reduce-filter-data/
        // all business logic is applied here.
        // reducer gets used only to post data :)
        // map <key: id, val: true>
        // reduction List of id-bool -> print only true.

        // anapoda key true/false
        // reduction: keep true only...
        double distance = SloppyMath.haversinMeters(fare.getPickupLatitude(), fare.getPickupLongitude(), fare.getDropoffLatitude(), fare.getDropoffLongitude());
        if (distance > 1 || distance < 1 && fare.getTripDuration() > 600 && fare.getPassengerCount() > 2) {
            context.write(new Text(fare.getId()), new Text(objectMapper.writeValueAsString(fare)));
        }
    }
}
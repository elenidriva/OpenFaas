package com.openfaas.function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PreProcess {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String COMMA_DELIMETER = ",";

    private final ObjectMapper objectMapper;

    public static void main(String[] args) throws IOException {
        createFares();
    }

    /*
    *
        ● id - a unique identifier for each trip
        ● vendor_id - a code indicating the provider associated with the trip record
        ● pickup_datetime - date and time when the meter was engaged
        ● dropoff_datetime - date and time when the meter was disengaged
        ● passenger_count - the number of passengers in the vehicle (driver entered value)
        ● pickup_longitude - the longitude where the meter was engaged
        ● pickup_latitude - the latitude where the meter was engaged
        ● dropoff_longitude - the longitude where the meter was disengaged
        ● dropoff_latitude - the latitude where the meter was disengaged
        ● store_and_fwd_flag - This flag indicates whether the trip record was held in vehicle
        memory before sending to the vendor because the vehicle did not have a connection
        to the server - Y=store and forward; N=not a store and forward trip
        ● trip_duration - duration of the trip in seconds
    *
    * */

    static List<Fare> createFares() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("fares.csv"));
        String[] columns = new String[11];
        in.readLine(); // read first line to get rid of it.
        String line = "";
        String[] tempArr;
        List<Fare> faresList = new ArrayList<>();
        while ((line = in.readLine()) != null) {
            int columnCount = 0;
            tempArr = line.split(COMMA_DELIMETER);
            for (String tempStr : tempArr) {
                columns[columnCount] = tempStr;
                columnCount++;
            }
            faresList.add(Fare.builder()
                    .id(columns[0])
                    .vendorId(Integer.parseInt(columns[1]))
                    .pickupDateTime(formatDate(columns[2]))
                    .dropoffDateTime(formatDate(columns[3]))
                    .passengerCount(Integer.parseInt(columns[4]))
                    .pickupLongitude(Double.parseDouble(columns[5]))
                    .pickupLatitude(Double.parseDouble(columns[6]))
                    .dropoffLongitude(Double.parseDouble(columns[7]))
                    .dropoffLatitude(Double.parseDouble(columns[8]))
                    .storeAndForwardFlag(columns[9])
                    .tripDuration(Long.parseLong(columns[10]))
                    .build());

        }
        in.close();
        return faresList;
    }

    private static LocalDateTime formatDate(String localDateTimeString) {
        return LocalDateTime.parse(localDateTimeString, formatter);
    }


}

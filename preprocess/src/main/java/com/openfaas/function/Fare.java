package com.openfaas.function;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Fare {
    private String id;
    private int vendorId;
    private LocalDateTime pickupDateTime;
    private LocalDateTime dropoffDateTime;
    private int passengerCount;
    private double pickupLongitude;
    private double pickupLatitude;
    private double dropoffLongitude;
    private double dropoffLatitude;
    private String storeAndForwardFlag;
    private long tripDuration;
}

package com.example.ticketing.pojo.request;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PurchaseTicketRequest {
    private Integer eventId;
    private Integer userId;
    private String seatIdsString;
}

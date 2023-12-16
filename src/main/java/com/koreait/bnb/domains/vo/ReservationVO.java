package com.koreait.bnb.domains.vo;

import com.koreait.bnb.domains.enums.ReserveState;
import com.koreait.bnb.domains.enums.RoomState;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@ToString
public class ReservationVO {
    private int no;
    private String userId;
    @NotNull
    private int roomNo;
    @FutureOrPresent @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @Future @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private int days;
    @Min(1) @NotNull
    private int personCount;
    @Min(0) @NotNull
    private int price;
    private int totalPrice;
    private ReserveState state;
}

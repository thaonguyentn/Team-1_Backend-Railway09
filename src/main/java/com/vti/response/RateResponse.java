package com.vti.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateResponse {
    private int id;
    private Double star;
    private String comment;
    private AccountResponse account;
    private Date rDate;
}

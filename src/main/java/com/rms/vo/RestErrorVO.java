package com.rms.vo;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VO for readable error throw from server.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestErrorVO {

    private HttpStatus status;
    private String message;
    private String date;

}

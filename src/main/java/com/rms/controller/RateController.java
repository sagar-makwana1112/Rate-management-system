package com.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rms.service.RateService;
import com.rms.vo.RateSurchargeVO;
import com.rms.vo.RateVO;

/**
 * Manage rest call for rate management system.
 */
@RestController
// @DefaultProperties(
// commandProperties = {
// @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")},
// defaultFallback = "com.rms.handler.HystrinxFallbackHandler.requestTimeOutHandler")
public class RateController {

    @Autowired
    private RateService rateService;

    /**
     * Get all surcharges.
     *
     * @return list of rate
     */
    @GetMapping("/surcharges")
    public ResponseEntity<List<RateSurchargeVO>> getAllSurchrge() {
        return new ResponseEntity<>(rateService.getAllRates(), HttpStatus.OK);
    }

    /**
     * Get surcharge.
     *
     * @return list of rate
     */
    @GetMapping("/surcharge/{id}")
    public ResponseEntity<RateSurchargeVO> getSurchrge(@PathVariable Long id) {
        return new ResponseEntity<>(rateService.getRate(id), HttpStatus.OK);
    }

    /**
     * Save rate.
     *
     * @param rateVO details of rate.
     * @return RateVO with status CREATED (201)
     */
    @PostMapping("/surcharge")
    public ResponseEntity<RateSurchargeVO> saveRate(@RequestBody RateVO rateVO) {
        return new ResponseEntity<>(rateService.saveRate(rateVO), HttpStatus.CREATED);
    }

    /**
     * Update rate.
     *
     * @param rateVO details of rate.
     * @return void with status OK (200)
     */
    @PutMapping("/surcharge")
    public ResponseEntity<RateSurchargeVO> updateRate(@RequestBody RateVO rateVO) {
        return new ResponseEntity<>(rateService.updateRate(rateVO), HttpStatus.NO_CONTENT);
    }

    /**
     * Delete rate.
     *
     * @param rate id for delete rate.
     * @return void with status OK (200)
     */
    @DeleteMapping("/surcharge/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable Long id) {
        rateService.deleteRate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

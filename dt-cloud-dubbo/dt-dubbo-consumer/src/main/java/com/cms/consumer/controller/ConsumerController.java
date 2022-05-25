package com.cms.consumer.controller;

import com.cms.consumer.service.ConsumerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DT辰白
 * @date 2022/5/17 20:43
 */
@RestController
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping("/getInfo")
    public String getInfo() {
        return consumerService.getInfo();
    }
}

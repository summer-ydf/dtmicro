package com.cms.item.controller;

import com.cms.common.tool.result.ResultUtil;
import com.cms.item.service.TestOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2021/12/23 15:21
 */
@RestController
@RequestMapping(value = "item")
public class TestOrderController {

    @Autowired
    private TestOrderService testOrderService;

    @GetMapping(value = "/save")
    public ResultUtil<?> save(@RequestParam Integer a){
        return testOrderService.insert(a);
    }

}

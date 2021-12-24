package com.mall.sku.controller;

import com.mall.sku.service.MallSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ydf Created by 2021/12/24 11:28
 */
@RestController
@RequestMapping(value = "sku")
public class MallSkuController {

    @Autowired
    private MallSkuService mallSkuService;

    @GetMapping(value = "reduceStock")
    public void reduceStock(@RequestParam Integer id) {
        mallSkuService.reduceStock(id);
    }
}

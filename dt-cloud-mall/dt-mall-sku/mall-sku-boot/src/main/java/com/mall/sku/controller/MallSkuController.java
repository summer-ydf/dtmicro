package com.mall.sku.controller;


import com.cms.common.tool.result.ResultUtil;
import com.mall.sku.service.MallSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DT辰白 Created by 2021/12/24 11:28
 */
@RestController
@RequestMapping(value = "sku")
public class MallSkuController {

    @Autowired
    private MallSkuService mallSkuService;

    @GetMapping(value = "reduceStock")
    public ResultUtil<?> reduceStock(@RequestParam Integer id) {
        return mallSkuService.reduceStock(id);
    }
}

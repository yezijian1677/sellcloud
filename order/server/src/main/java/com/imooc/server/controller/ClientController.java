package com.imooc.server.controller;

import com.imooc.server.client.ProductClient;
import com.imooc.server.dataobject.ProductInfo;
import com.imooc.server.dto.CartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author augenye
 * @date 2019/12/4 5:40 下午
 */
@RestController
@Slf4j
public class ClientController {

    @Autowired
    private ProductClient productClient;

    @GetMapping("/listForOrder")
    public String getProductList() {
        List<ProductInfo> list = productClient.findByProductIdList(Arrays.asList("157875196366160022"));
        log.info("response={}", list);

        return "ok";
    }

    @GetMapping("/decreaseStock")
    public String decreaseStock() {
        productClient.decreaseStock(Arrays.asList(new CartDTO("157875196366160022", 2)));

        return "ok";
    }
}

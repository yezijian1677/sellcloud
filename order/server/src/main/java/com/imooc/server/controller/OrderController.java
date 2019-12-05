package com.imooc.server.controller;

import com.imooc.server.VO.ResultVO;
import com.imooc.server.converter.OrderForm2OrderDTOConverter;
import com.imooc.server.dto.OrderDTO;
import com.imooc.server.enums.ResultEnum;
import com.imooc.server.exception.OrderException;
import com.imooc.server.form.OrderForm;
import com.imooc.server.service.OrderService;
import com.imooc.server.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author augenye
 * @date 2019/12/4 9:04 上午
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[创建订单]，参数不正确，orderForm={}", orderForm);
            throw new OrderException(1, bindingResult.getFieldError().getDefaultMessage());
        }
        // orderForm -> orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车信息为空");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }

        OrderDTO result = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        System.out.println(result.getOrderId());
        map.put("orderId", result.getOrderId());
        return ResultVOUtil.success(map);
    }
}

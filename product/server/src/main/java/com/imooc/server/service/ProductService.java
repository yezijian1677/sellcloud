package com.imooc.server.service;

import com.imooc.common.DecreaseStockInput;
import com.imooc.server.dataobject.ProductInfo;

import java.util.List;

/**
 * @author augenye
 * @date 2019/12/3 9:15 下午
 */
public interface ProductService {

    /**
     * 寻找所有上架的商品
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 根据商品id查找商品信息
     * @param productIdList
     * @return
     */
    List<ProductInfo> findByProductIdList(List<String> productIdList);

    /**
     * 扣库存
     */
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}

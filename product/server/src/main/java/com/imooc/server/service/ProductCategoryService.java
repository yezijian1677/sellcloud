package com.imooc.server.service;

import com.imooc.server.dataobject.ProductCategory;

import java.util.List;

/**
 * @author augenye
 * @date 2019/12/3 9:15 下午
 */
public interface ProductCategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}

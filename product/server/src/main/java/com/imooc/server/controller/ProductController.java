package com.imooc.server.controller;

import com.imooc.common.DecreaseStockInput;
import com.imooc.server.VO.ProductInfoVO;
import com.imooc.server.VO.ProductVO;
import com.imooc.server.VO.ResultVO;
import com.imooc.server.dataobject.ProductCategory;
import com.imooc.server.dataobject.ProductInfo;
import com.imooc.server.service.ProductCategoryService;
import com.imooc.server.service.ProductService;
import com.imooc.server.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author augenye
 * @date 2019/12/3 8:35 下午
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService categoryService;

    /**
     * 1. 查询所有在架商品
     * 2. 获取所有类目列表
     * 3. 查询类目
     * 4. 构造数据
     *
     * @return
     */
    @GetMapping("/list")
    public ResultVO<ProductVO> list() {
        // 1.获取所有在架商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        // 2.获取类目type
        List<Integer> categoryTypelList = productInfoList.stream()
                .map(ProductInfo::getCategoryType).collect(Collectors.toList());
        // 3. 从数据库中查询类目
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypelList);

        // 4. 构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory category : categoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(category.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }


        return ResultVOUtil.success(productVOList);
    }

    @PostMapping("/listForOrder")
    public List<ProductInfo> ListForOrder(@RequestBody List<String> productIdList) {
        return productService.findByProductIdList(productIdList);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList) {
        productService.decreaseStock(decreaseStockInputList);
    }
}

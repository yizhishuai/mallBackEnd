package com.imooc.mall.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductIUpdateForm {

    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private String mainImage;

    private String subImages;

}

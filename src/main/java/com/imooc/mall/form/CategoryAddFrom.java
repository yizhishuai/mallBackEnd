package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryAddFrom {


    private int parentId;

    @NotBlank
    private String categoryName;
}

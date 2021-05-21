package com.imooc.mall.form;

import lombok.Data;
import lombok.NonNull;

@Data
public class CategoryDeleteForm {

    @NonNull
    private Integer categoryId;
}

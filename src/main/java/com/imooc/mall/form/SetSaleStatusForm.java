package com.imooc.mall.form;

import lombok.Data;
import lombok.NonNull;

@Data
public class SetSaleStatusForm {

    @NonNull
    private Integer productId;


    @NonNull
    private Integer status;
}

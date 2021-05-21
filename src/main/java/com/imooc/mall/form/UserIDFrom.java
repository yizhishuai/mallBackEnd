package com.imooc.mall.form;

import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserIDFrom {

    @NotNull
    private Integer userId;
}

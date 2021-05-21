package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserListForm {

    @NotBlank
    private Integer pageSize;

    @NotBlank
    private Integer pageNum;
}

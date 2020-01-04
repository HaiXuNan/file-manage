package com.file.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResultVo {

    private String username;

    private String token;

    private int roleId;

}

package com.file.manage.dao.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_base")
public class UserBaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    private String username;

    private String password;

    private LocalDateTime createTime;

    private String roleId;

    private boolean status;

}

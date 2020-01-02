package com.file.manage.dao.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("base_info")
@Data
public class BaseInfoEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String fileName;

    private String fileExtend;

    private String operator;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;

    private int version;

}


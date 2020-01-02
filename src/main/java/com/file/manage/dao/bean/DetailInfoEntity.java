package com.file.manage.dao.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("detail_info")
public class DetailInfoEntity {

    @TableId(type = IdType.AUTO)
    private int id;

    private int base_id;

    private String filePath;

    private LocalDate createTime;

    private LocalDate updateTime;

    private int version;
}

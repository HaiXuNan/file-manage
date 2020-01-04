package com.file.manage.dao.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("detail_info")
public class DetailInfoEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    private int baseId;

    private String filePath;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @Version
    private int version;
}

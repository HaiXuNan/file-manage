package com.file.manage.dao.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_file_info")
public class UserFileInfoEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    private int userId;

    private int fileId;

    private LocalDateTime createTime;
}

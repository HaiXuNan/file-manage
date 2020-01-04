package com.file.manage.vo;

import com.file.manage.dao.bean.BaseInfoEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FindAllResultVo {
    /**
     * 当前页数据
     */
    private List<BaseInfoEntity> baseInfoEntityList;
    /**
     * 总记录数
     */
    private long total;
    /**
     * 当前页
     */
    private long current;
    /**
     * 当前页记录数
     */
    private long size;
    /**
     * 总页数
     */
    private long page;

}

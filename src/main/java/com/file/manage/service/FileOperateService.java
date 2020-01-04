package com.file.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.file.manage.dao.bean.BaseInfoEntity;
import com.file.manage.dao.bean.DetailInfoEntity;
import com.file.manage.dao.bean.UserFileInfoEntity;
import com.file.manage.dao.mapper.BaseInfoMapper;
import com.file.manage.dao.mapper.DetailInfoMapper;
import com.file.manage.dao.mapper.UserFileInfoMapper;
import com.file.manage.entity.RequestEntity;
import com.file.manage.entity.ResponseEntity;
import com.file.manage.entity.StatusCode;
import com.file.manage.exception.ServerException;
import com.file.manage.vo.FindAllResultVo;
import com.file.manage.vo.FindResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FileOperateService {

    @Value("${system.file.root.path}")
    private String filePath;

    private final BaseInfoMapper baseInfoMapper;
    private final DetailInfoMapper detailInfoMapper;
    private final UserFileInfoMapper userFileInfoMapper;

    public FileOperateService(BaseInfoMapper baseInfoMapper, DetailInfoMapper detailInfoMapper, UserFileInfoMapper userFileInfoMapper) {
        this.baseInfoMapper = baseInfoMapper;
        this.detailInfoMapper = detailInfoMapper;
        this.userFileInfoMapper = userFileInfoMapper;
    }
    /*@Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    TransactionDefinition transactionDefinition;*/

    @Transactional
    public ResponseEntity<String> add(RequestEntity requestEntity) {

        /*TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        dataSourceTransactionManager.commit(transactionStatus);//提交
        dataSourceTransactionManager.rollback(transactionStatus);//最好是放在catch 里面,防止程序异常而事务一直卡在哪里未提交*/

        BaseInfoEntity baseInfoEntity = new BaseInfoEntity();
        baseInfoEntity.setFileName(requestEntity.getFileName());
        baseInfoEntity.setFileExtend(requestEntity.getFileExtend());
        baseInfoEntity.setCreateTime(LocalDateTime.now());
        baseInfoEntity.setUpdateTime(LocalDateTime.now());
        baseInfoEntity.setOperator("");
        baseInfoEntity.setVersion(1);
        baseInfoMapper.insert(baseInfoEntity);

        DetailInfoEntity detailInfoEntity = new DetailInfoEntity();
        detailInfoEntity.setBaseId(baseInfoEntity.getId());
        detailInfoEntity.setFilePath(requestEntity.getFilePath());
        detailInfoEntity.setCreateTime(LocalDateTime.now());
        detailInfoEntity.setUpdateTime(LocalDateTime.now());
        detailInfoEntity.setVersion(1);
        detailInfoMapper.insert(detailInfoEntity);


        UserFileInfoEntity userFileInfoEntity = new UserFileInfoEntity();
        userFileInfoEntity.setFileId(baseInfoEntity.getId());
        userFileInfoEntity.setUserId(1);
        userFileInfoEntity.setCreateTime(LocalDateTime.now());
        userFileInfoMapper.insert(userFileInfoEntity);

        return ResponseEntity.success();
    }

    @Transactional
    public ResponseEntity<String> delete(int id) {
        // 查询文件
        BaseInfoEntity baseInfoEntity = baseInfoMapper.selectById(id);
        // 查询用户id
        QueryWrapper<UserFileInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_id", id);
        UserFileInfoEntity userFileInfoEntity = userFileInfoMapper.selectOne(queryWrapper);
        // 执行删除操作
        if (baseInfoEntity != null && userFileInfoEntity != null) {
            baseInfoMapper.deleteById(id);
            UpdateWrapper<UserFileInfoEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("file_id", id);
            userFileInfoMapper.delete(updateWrapper);
        }
        return ResponseEntity.success();
    }

    @Transactional
    public ResponseEntity<String> update(RequestEntity requestEntity, int id) {
        // 查询文件
        BaseInfoEntity baseInfoEntity = baseInfoMapper.selectById(id);
        QueryWrapper<DetailInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("base_id", id);
        DetailInfoEntity detailInfoEntity = detailInfoMapper.selectOne(queryWrapper);
        // 执行更新操作
        if (baseInfoEntity != null && detailInfoEntity != null) {
            baseInfoEntity.setFileName(requestEntity.getFileName());
            baseInfoEntity.setFileExtend(requestEntity.getFileExtend());
            baseInfoEntity.setUpdateTime(LocalDateTime.now());
//            baseInfoEntity.setVersion(baseInfoEntity.getVersion() + 1);
            baseInfoMapper.updateById(baseInfoEntity);

            detailInfoEntity.setFilePath(requestEntity.getFilePath());
            detailInfoEntity.setUpdateTime(LocalDateTime.now());
//            detailInfoEntity.setVersion(detailInfoEntity.getVersion() + 1);
            detailInfoMapper.updateById(detailInfoEntity);

            return ResponseEntity.success();
        }
        return ResponseEntity.failure(StatusCode.UPDATE_FILE_FAIL);
    }


    public ResponseEntity<FindResultVo> findById(int id) {
        // 查询文件
        BaseInfoEntity baseInfoEntity = baseInfoMapper.selectById(id);
        QueryWrapper<DetailInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("base_id", id);
        DetailInfoEntity detailInfoEntity = detailInfoMapper.selectOne(queryWrapper);

        Optional.ofNullable(baseInfoEntity).orElseThrow(() -> new ServerException(StatusCode.RESULT_FAIL));
        Optional.ofNullable(detailInfoEntity).orElseThrow(() -> new ServerException(StatusCode.RESULT_FAIL));

        FindResultVo findResultVo = FindResultVo.builder()
                .fileName(baseInfoEntity.getFileName())
                .operator(baseInfoEntity.getOperator())
                .createTime(baseInfoEntity.getCreateTime())
                .updateTime(baseInfoEntity.getUpdateTime())
                .filePath(detailInfoEntity.getFilePath())
                .build();

        return ResponseEntity.success(findResultVo);
    }

    public ResponseEntity<FindAllResultVo> findAll(int size, int index) {
        Page<BaseInfoEntity> page = new Page<>(index, size);
        IPage<BaseInfoEntity> selectPage = baseInfoMapper.selectPage(page, null);

        FindAllResultVo findAllResultVo = FindAllResultVo.builder()
                .baseInfoEntityList(selectPage.getRecords())
                .current(selectPage.getCurrent())
                .size(selectPage.getSize())
                .page(selectPage.getPages())
                .total(selectPage.getTotal())
                .build();

        return ResponseEntity.success(findAllResultVo);
    }

}

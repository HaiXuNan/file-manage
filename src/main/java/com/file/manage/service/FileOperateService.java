package com.file.manage.service;

import com.file.manage.vo.FindResultVo;
import com.file.manage.dao.bean.BaseInfoEntity;
import com.file.manage.dao.bean.DetailInfoEntity;
import com.file.manage.dao.bean.UserFileInfoEntity;
import com.file.manage.dao.mapper.BaseInfoMapper;
import com.file.manage.dao.mapper.DetailInfoMapper;
import com.file.manage.dao.mapper.UserFileInfoMapper;
import com.file.manage.entity.RequestEntity;
import com.file.manage.entity.ResponseEntity;
import com.file.manage.vo.FindAllResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        baseInfoEntity.setOperator("");
        baseInfoEntity.setVersion(1);
        baseInfoMapper.insert(baseInfoEntity);

        DetailInfoEntity detailInfoEntity = new DetailInfoEntity();
        detailInfoEntity.setBaseId(baseInfoEntity.getId());
        detailInfoEntity.setFilePath("");
        detailInfoEntity.setCreateTime(LocalDateTime.now());
        detailInfoEntity.setVersion(1);
        detailInfoMapper.insert(detailInfoEntity);


        UserFileInfoEntity userFileInfoEntity = new UserFileInfoEntity();
        userFileInfoEntity.setFileId(baseInfoEntity.getId());
        userFileInfoEntity.setUserId(1);
        userFileInfoEntity.setCreateTime(LocalDateTime.now());
        userFileInfoMapper.insert(userFileInfoEntity);

        return ResponseEntity.success();
    }

    public ResponseEntity<String> delete(int id) {
        return null;
    }

    public ResponseEntity<String> update(RequestEntity requestEntity, int id) {
        return null;
    }

    public ResponseEntity<FindResultVo> findById(int id) {
        return null;
    }

    public ResponseEntity<FindAllResultVo> findAll() {
        return null;
    }

}

package com.file.manage.controller;

import com.file.manage.annotation.AccessLimit;
import com.file.manage.entity.RequestEntity;
import com.file.manage.entity.ResponseEntity;
import com.file.manage.service.FileOperateService;
import com.file.manage.vo.FindAllResultVo;
import com.file.manage.vo.FindResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Api(tags = "文件操作")
@RestController
@RequestMapping("/file")
public class FileOperateController {

    private final FileOperateService fileOperateService;

    public FileOperateController(FileOperateService fileOperateService) {
        this.fileOperateService = fileOperateService;
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody @NotNull RequestEntity requestEntity) {

        return fileOperateService.add(requestEntity);
    }

    /**
     * 删除文件
     *
     * @param id 文件id
     * @return
     */
    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {

        return fileOperateService.delete(id);
    }

    @ApiOperation("修改")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody @NotNull RequestEntity requestEntity,
                                         @PathVariable("id") @NotNull int id) {

        return fileOperateService.update(requestEntity, id);
    }

    @ApiOperation("根据id查询")
    @AccessLimit(max = 5, second = 10)
    @GetMapping("/{id}")
    public ResponseEntity<FindResultVo> findById(@PathVariable("id") @NotNull int id) {
        return fileOperateService.findById(id);
    }

    @ApiOperation("查询全部")
    @AccessLimit(max = 5, second = 10)
    @GetMapping("/")
    public ResponseEntity<FindAllResultVo> findAll(@RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "0") int index) {
        return fileOperateService.findAll(size, index);
    }
}

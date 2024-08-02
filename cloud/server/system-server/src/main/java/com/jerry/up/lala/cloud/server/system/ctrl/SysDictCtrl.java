package com.jerry.up.lala.cloud.server.system.ctrl;

import com.jerry.up.lala.cloud.api.system.constant.AccessDictConstant;
import com.jerry.up.lala.cloud.server.system.service.SysDictService;
import com.jerry.up.lala.cloud.server.system.vo.SysDictInfoVO;
import com.jerry.up.lala.cloud.server.system.vo.SysDictSaveVO;
import com.jerry.up.lala.cloud.server.system.vo.SysDictVO;
import com.jerry.up.lala.framework.boot.api.Api;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.model.DataIdBody;
import com.jerry.up.lala.framework.common.r.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: 系统字典
 *
 * @author FMJ
 * @date 2024/4/17 17:46
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictCtrl {

    @Autowired
    private SysDictService sysDictService;

    @GetMapping("/list")
    @Api(value = "字典管理-查询", accessCodes = {AccessDictConstant.DICT, AccessDictConstant.DICT_QUERY})
    public R<List<SysDictVO>> list(DataBody<String> query) {
        List<SysDictVO> result = sysDictService.list(query);
        return R.succeed(result);
    }

    @GetMapping("/info/{id}")
    @Api(value = "字典管理-详情", accessCodes = {AccessDictConstant.DICT_UPDATE})
    public R<SysDictInfoVO> info(@PathVariable("id") Long id) {
        SysDictInfoVO result = sysDictService.info(id);
        return R.succeed(result);
    }

    @GetMapping("/verify-dict-name")
    @Api(value = "字典管理-校验字典名称", accessCodes = {AccessDictConstant.DICT_ADD, AccessDictConstant.DICT_UPDATE})
    public R verifyDictName(DataIdBody<Long, String> dataIdBody) {
        sysDictService.verifyDictName(dataIdBody);
        return R.empty();
    }

    @GetMapping("/verify-dict-key")
    @Api(value = "字典管理-校验字典标识", accessCodes = {AccessDictConstant.DICT_ADD, AccessDictConstant.DICT_UPDATE})
    public R verifyDictKey(DataIdBody<Long, String> dataIdBody) {
        sysDictService.verifyDictKey(dataIdBody);
        return R.empty();
    }

    @PostMapping
    @Api(value = "字典管理-新增", accessCodes = {AccessDictConstant.DICT_ADD})
    public R add(@RequestBody SysDictSaveVO sysDictSaveVO) {
        sysDictService.add(sysDictSaveVO);
        return R.empty();
    }

    @PutMapping("/{id}")
    @Api(value = "字典管理-编辑", accessCodes = {AccessDictConstant.DICT_UPDATE})
    public R update(@PathVariable("id") Long id, @RequestBody SysDictSaveVO sysDictSaveVO) {
        sysDictService.update(id, sysDictSaveVO);
        return R.empty();
    }

    @DeleteMapping("/{id}")
    @Api(value = "字典管理-删除", accessCodes = {AccessDictConstant.DICT_DELETE})
    public R delete(@PathVariable("id") Long id) {
        sysDictService.delete(id);
        return R.empty();
    }

    @PostMapping("/refresh-cache")
    @Api(value = "字典管理-刷新缓存", accessCodes = {AccessDictConstant.DICT_REFRESH_CACHE})
    public R refreshCache() {
        sysDictService.refreshCache();
        return R.empty();
    }

}

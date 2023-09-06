package cloud.cstream.chat.admin.controller;

import cloud.cstream.chat.common.domain.R;
import cloud.cstream.chat.common.util.StpAdminUtil;
import cloud.cstream.chat.common.valid.ValidGroup;
import cloud.cstream.chat.core.domain.entity.PromptLibDO;
import cloud.cstream.chat.core.domain.query.PromptPageQuery;
import cloud.cstream.chat.core.domain.request.PromptLibRequest;
import cloud.cstream.chat.core.domain.vo.PromptLibVO;
import cloud.cstream.chat.core.service.PromptLibService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * admin-提示词库
 *
 * @author evans
 * @description
 * @date 2023/6/3
 */
@AllArgsConstructor
@RestController
@RequestMapping("admin/prompt_lib")
public class PromptLibController {


    private final PromptLibService promptLibService;


    /**
     * 分页查询提示词
     *
     * @param pageQuery
     * @return
     */
    @PostMapping("page_query")
    public R<Page<PromptLibVO>> pageQuery(@RequestBody PromptPageQuery pageQuery){
        return R.data(promptLibService.pageQuery(pageQuery));
    }


    /**
     * 新增提示词
     *
     * @param request
     * @return
     */
    @PostMapping("add_prompt")
    public R<Page<PromptLibVO>> addPrompt(@RequestBody PromptLibRequest request){
        promptLibService.addPrompt(request, StpAdminUtil.getLoginId());
        return R.success();
    }

    /**
     * 文件导入提示词
     *
     * @param file
     * @return
     */
    @PostMapping("import_file")
    public R<Void> importJsonFileToBatchInsert(@RequestParam("file")MultipartFile file){
        promptLibService.analysisAndBatchInsert(file, StpAdminUtil.getLoginId());
        return R.success();
    }

    /**
     * 更新提示词
     * @param request
     * @return
     */
    @PostMapping("update")
    public R<Void> update(@RequestBody PromptLibRequest request){
        PromptLibDO promptLibDO = new PromptLibDO();
        BeanUtil.copyProperties(request, promptLibDO);
        promptLibService.updateById(promptLibDO);
        return R.success();
    }


    @PostMapping("delete")
    public R<Void> delete(@RequestBody @Validated(ValidGroup.Del.class) PromptLibRequest request){
        PromptLibDO promptLibDO = promptLibService.getById(request.getId());
        if (Objects.nonNull(promptLibDO)){
            promptLibService.removeById(request.getId());
        }
        return R.success();
    }
}

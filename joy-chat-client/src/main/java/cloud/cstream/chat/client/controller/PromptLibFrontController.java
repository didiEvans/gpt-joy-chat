package cloud.cstream.chat.client.controller;

import cloud.cstream.chat.client.utils.ClientUserLoginUtil;
import cloud.cstream.chat.common.domain.R;
import cloud.cstream.chat.common.valid.ValidGroup;
import cloud.cstream.chat.core.domain.query.PromptPageQuery;
import cloud.cstream.chat.core.domain.request.PromptContributeRequest;
import cloud.cstream.chat.core.domain.vo.PromptLibVO;
import cloud.cstream.chat.core.service.PromptLibService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * client-提示词库
 *
 * @author evans
 * @description
 * @date 2023/6/3
 */
@AllArgsConstructor
@RestController
@RequestMapping("client/prompt_lib")
public class PromptLibFrontController {


    private PromptLibService promptLibService;

    /**
     * 获取工具箱提示词
     *
     * @return
     */
    @GetMapping("toolbox")
    public R<List<PromptLibVO>> selectToolbox() {
        return R.data(promptLibService.selectToolbox());
    }


    /**
     * 客户端用户贡献提示词
     *
     * @param request 请求参数
     */
    @PostMapping("contribute")
    public R<Void> contributePrompt(@RequestBody @Validated(ValidGroup.Add.class) PromptContributeRequest request) {
        promptLibService.contributePrompt(request, ClientUserLoginUtil.getUserId());
        return R.success();
    }

    /**
     * 获取提示词分页对象
     *
     * @return 分页对象
     */
    @PostMapping("list")
    public R<Page<PromptLibVO>> selectPromptLibPages(@RequestBody PromptPageQuery query) {
        return R.data(promptLibService.pageQuery(query));
    }

    /**
     * 获取我的提示词页面分页
     *
     * @param query
     * @return
     */
    @PostMapping("get_mine_tool_page")
    public R<Page<PromptLibVO>> getMineToolPage(@RequestBody @Validated PromptPageQuery query) {
        query = Objects.isNull(query) ? new PromptPageQuery() : query;
        query.setUid(ClientUserLoginUtil.getUserId());
        return R.data(promptLibService.getMineToolPage(query));
    }

    /**
     * 编辑我的提示词
     *
     * @param request
     * @return
     */
    @PostMapping("edit_mine_prompt")
    public R<Void> editMinePrompt(@RequestBody @Validated(ValidGroup.Update.class) PromptContributeRequest request) {
        promptLibService.editMinePrompt(request, ClientUserLoginUtil.getUserId());
        return R.success();
    }

    /**
     * 删除我的提示词
     *
     * @param request
     * @return
     */
    @PostMapping("delete_mine_prompt")
    public R<Void> deleteMinePrompt(@RequestBody @Validated(ValidGroup.Del.class) PromptContributeRequest request) {
        promptLibService.deleteMinePrompt(request, ClientUserLoginUtil.getUserId());
        return R.success();
    }
}

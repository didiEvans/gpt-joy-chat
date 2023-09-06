package cloud.cstream.chat.admin.controller;

import cloud.cstream.chat.core.domain.query.SensitiveWordPageQuery;
import cloud.cstream.chat.core.domain.vo.SensitiveWordVO;
import cloud.cstream.chat.admin.service.SensitiveWordService;
import cloud.cstream.chat.common.domain.R;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * admin-敏感词相关接口
 * @author Anker
 * @date 2023/3/28 20:59
 *
 */
@AllArgsConstructor
@RestController
@RequestMapping("admin/sensitive_word")
public class SensitiveWordController {

    private final SensitiveWordService sensitiveWordService;

    /**
     * 敏感词列表分页
     * @param sensitiveWordPageQuery
     * @return
     */
    @GetMapping("/page")
    public R<IPage<SensitiveWordVO>> page(@Validated SensitiveWordPageQuery sensitiveWordPageQuery) {
        return R.data(sensitiveWordService.pageSensitiveWord(sensitiveWordPageQuery));
    }

    /**
     * 新增敏感词
     * @param word  敏感词
     * @return
     */
    @GetMapping("/add")
    public R<IPage<SensitiveWordVO>> add(@RequestParam("word") String word) {
        sensitiveWordService.add(word);
        return R.success();
    }


    /**
     * 删除敏感词
     * @param id 记录id
     * @return
     */
    @GetMapping("/delete/{id}")
    public R<IPage<SensitiveWordVO>> delete(@PathVariable("id")Integer id) {
        sensitiveWordService.delete(id);
        return R.success();
    }
}

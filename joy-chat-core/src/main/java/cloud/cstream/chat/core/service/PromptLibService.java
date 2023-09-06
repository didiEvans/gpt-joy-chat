package cloud.cstream.chat.core.service;

import cloud.cstream.chat.core.domain.dto.SysPromptInfo;
import cloud.cstream.chat.core.domain.entity.PromptLibDO;
import cloud.cstream.chat.core.domain.query.PromptPageQuery;
import cloud.cstream.chat.core.domain.request.PromptContributeRequest;
import cloud.cstream.chat.core.domain.request.PromptLibRequest;
import cloud.cstream.chat.core.domain.vo.PromptLibVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author evans
 * @description
 * @date 2023/6/3
 */
public interface PromptLibService extends IService<PromptLibDO> {

    Page<PromptLibVO> pageQuery(PromptPageQuery pageQuery);

    /**
     * 新增提示词
     *
     * @param request
     * @param sysUserId
     */
    void addPrompt(PromptLibRequest request, Integer sysUserId);

    List<PromptLibVO> selectToolbox();

    /**
     *
     * @param file
     * @param loginId
     */
    void analysisAndBatchInsert(MultipartFile file, Integer loginId);

    /**
     * 获取系统提示词内容
     *
     * @param sysPromptLibId
     * @return
     */
    SysPromptInfo getSysPromptInfo(String sysPromptLibId);

    /**
     * 贡献提示词
     *
     * @param request 请求参数
     * @param userId    用户id
     */
    void contributePrompt(PromptContributeRequest request, Integer userId);

    Page<PromptLibVO> getMineToolPage(PromptPageQuery query);

    void editMinePrompt(PromptContributeRequest request, Integer userId);

    void deleteMinePrompt(PromptContributeRequest request, Integer userId);
}

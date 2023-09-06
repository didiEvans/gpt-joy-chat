package cloud.cstream.chat.client.service;

import cloud.cstream.chat.core.domain.query.PageQuery;
import cloud.cstream.chat.core.domain.request.draw.YiJCreateDrawRequest;
import cloud.cstream.chat.core.domain.request.draw.YiJTaskProgressRequest;
import cloud.cstream.chat.core.domain.response.YiJAiDrawResponse;
import cloud.cstream.chat.core.domain.response.YiJAiDrawStyleSelectResponse;
import cloud.cstream.chat.core.domain.response.YiJAiDrawTaskProgressResponse;
import cloud.cstream.chat.core.domain.vo.DrawResultRecordVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface DrawAiService {
    /**
     * 意间绘画任务请求
     *
     * @param request
     * @return
     */
    String createDrawTask(YiJCreateDrawRequest request);


    /**
     * 获取任务响应结果集
     *
     * @param requestId 请求id
     * @param userId    用户id
     * @return
     */
    List<YiJAiDrawResponse> getTaskResponses(String requestId, Integer userId);

    /**
     * 获取文生图样式选择下拉
     *
     * @return
     */
    YiJAiDrawStyleSelectResponse getDrawStyleSelect(Integer handlerCode);

    /**
     * @param request
     * @return
     */
    YiJAiDrawTaskProgressResponse getTaskProcess(YiJTaskProgressRequest request);

    /**
     * 批量获取任务进度
     *
     * @param request
     * @return
     */
    List<YiJAiDrawTaskProgressResponse> batchGetProgress(YiJTaskProgressRequest request);

    Page<DrawResultRecordVO> selectDrawHistoryPage(PageQuery query, Integer userId);

    List<String> queryUserUmCompleteTask(Integer userId);
}

package cloud.cstream.chat.core.handler;

import cloud.cstream.chat.common.constants.RedisKeyConstant;
import cloud.cstream.chat.common.enums.BaiDuTextReviewHitEnum;
import cloud.cstream.chat.common.enums.EnableDisableStatusEnum;
import cloud.cstream.chat.core.config.BaiDuTextReviewAppConf;
import cloud.cstream.chat.core.domain.entity.SensitiveWordDO;
import cloud.cstream.chat.core.service.SensitiveWordService;
import cloud.cstream.chat.core.utils.RedisClient;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cloud.cstream.chat.common.constants.StrConstants.*;

/**
 * 百度文本审核
 *
 * @author evans
 * @description
 * @date 2023/5/19
 */
@Slf4j
@Component
public class BaiDuTextReviewHandler {

    @Autowired
    private SensitiveWordService sensitiveWordService;
    @Autowired
    private BaiDuTextReviewAppConf appConf;
    @Autowired
    private RedisClient redisClient;

    /**
     * 获取accessToken
     *
     * @return accessToken
     * @throws IOException
     */
    private String getAccessToken() throws IOException {
        String accessToken = redisClient.get(RedisKeyConstant.BD_TEXT_REVIEW_APP_TOKEN_CACHE);
        if (CharSequenceUtil.isBlankOrUndefined(accessToken)) {
            // 调用百度接口获取访问令牌
            accessToken = postAccessToken();
            redisClient.setEx(RedisKeyConstant.BD_TEXT_REVIEW_APP_TOKEN_CACHE, accessToken, 20, TimeUnit.DAYS);
        }
        return accessToken;
    }

    /**
     * 请求获取accessToken
     *
     * @return
     * @throws IOException
     */
    private String postAccessToken() throws IOException {
        // 构建请求地址
        String requestUrl = StrUtil.builder(appConf.getAccessTokenUrl())
                .append("?client_id=")
                .append(appConf.getClientId())
                .append("&client_secret=")
                .append(appConf.getClientSecret())
                .append("&grant_type=client_credentials").toString();
        log.info("<百度文本审核> 获取 accessToken:{}", requestUrl);
        // 执行请求
        String resp = HttpRequest.post(requestUrl)
                .contentType("application/x-www-form-urlencoded")
                .execute().body();
        return new JSONObject(resp).getStr("access_token");
    }

    /**
     * 执行文本检测
     *
     * @param content
     * @return
     * @throws IOException
     */
    public List<String> doCheck(String content) throws IOException {
        String requestUrl = StrUtil.builder(appConf.getDoCheckActionUrl())
                .append("?access_token=")
                .append(getAccessToken()).toString();
        log.info("<百度文本审核> --- 审核接口请求:{}", requestUrl);
        // 执行请求
        String resp = HttpRequest.post(requestUrl)
                .contentType("application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .form("text", content)
                .execute()
                .body();
        log.info("<百度文本审核> --- 审核接口响应:{}", resp);
        // 解析审核结果
        JSONObject respObj = new JSONObject(resp);
        // 获取是否命中标识
        Integer hitCode = respObj.getInt(CONCLUSION_TYPE);
        // 未命中则返回空数组
        List<String> hitTxt = Lists.newArrayList();
        if (!BaiDuTextReviewHitEnum.isHit(hitCode)) {
            return hitTxt;
        }
        // 获取命中文本
        List<JSONObject> hitList = respObj.getBeanList(DATA, JSONObject.class);
        // 因为是单条文本检测, 所以直接取第一个就行
        JSONObject hitEl = hitList.get(0);
        // 获取命中的文本
        List<JSONObject> hitWords = hitEl.getBeanList(HITS, JSONObject.class);
        for (JSONObject hitWord : hitWords) {
            List<String> wordList = hitWord.getBeanList(WORDS, String.class);
            if (CollUtil.isEmpty(wordList)) {
                hitTxt.add(content);
            } else {
                hitTxt.addAll(wordList);
            }
        }
        return hitTxt;
    }


    public List<String> checkWords(String content){
        List<String> hitWord = Lists.newArrayList();
        try {
            // 执行检测
             hitWord = this.doCheck(content);
            if (CollUtil.isNotEmpty(hitWord)){
                // 入库
                List<SensitiveWordDO> dos = hitWord.stream().map(SensitiveWordDO::new).toList();
                List<SensitiveWordDO> filterR = Lists.newArrayList();
                for (SensitiveWordDO aDo : dos) {
                    Long exists = sensitiveWordService.count(Wrappers.<SensitiveWordDO>lambdaQuery()
                            .eq(SensitiveWordDO::getWord, aDo.getWord())
                            .eq(SensitiveWordDO::getStatus, EnableDisableStatusEnum.ENABLE));
                    if (exists == 0){
                        filterR.add(aDo);
                    }
                }
                sensitiveWordService.batchInsert(filterR);
            }
            return hitWord;
        } catch (Exception ex){
            log.error("执行百度文本审核异常 >> :", ex);
        }
        return hitWord;
    }
}

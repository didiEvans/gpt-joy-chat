package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.constants.NumConstant;
import cloud.cstream.chat.common.constants.StrConstants;
import cloud.cstream.chat.common.enums.BoolEnum;
import cloud.cstream.chat.common.enums.SysTypeEnum;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.core.domain.dto.SysPromptInfo;
import cloud.cstream.chat.core.domain.entity.PromptLibDO;
import cloud.cstream.chat.core.domain.pojo.PromptTransPOJO;
import cloud.cstream.chat.core.domain.query.PromptPageQuery;
import cloud.cstream.chat.core.domain.request.PromptContributeRequest;
import cloud.cstream.chat.core.domain.request.PromptLibRequest;
import cloud.cstream.chat.core.domain.vo.PromptLibVO;
import cloud.cstream.chat.core.handler.SensitiveWordHandler;
import cloud.cstream.chat.core.mapper.PromptLibMapper;
import cloud.cstream.chat.core.service.PromptLibService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static cloud.cstream.chat.common.constants.CoreConstants.DEFAULT_SYS_PROMPT;
import static cloud.cstream.chat.common.constants.StrConstants.PROMPT_IRON_PREFIX;

/**
 * 提示词库服务
 *
 * @author evans
 * @description
 * @date 2023/6/3
 */
@Service
public class PromptLibServiceImpl extends ServiceImpl<PromptLibMapper, PromptLibDO> implements PromptLibService {


    @Override
    public Page<PromptLibVO> pageQuery(PromptPageQuery pageQuery) {
        return baseMapper.pageQuery(pageQuery.toPage(), pageQuery);
    }


    @Override
    public void addPrompt(PromptLibRequest request, Integer sysUserId) {
        baseMapper.insert(new PromptLibDO(request.getTitle(), request.getPromptContent(), BoolEnum.FALSE.getVar(), sysUserId));
    }

    @Override
    public List<PromptLibVO> selectToolbox() {
        return baseMapper.selectToolboxPrompt();
    }

    @Override
    public void analysisAndBatchInsert(MultipartFile file, Integer loginId) {
        String fileName = file.getName();
        if (fileName.endsWith(StrConstants.JSON_FILE_SUFFIX)) {
            throw new ServiceException("不能识别的文件");
        }
        // 将MultipartFile转换为JSON对象
        List<PromptLibDO> promptList = Lists.newArrayList();
        try {
            String fileStr = new String(file.getBytes());
            if (CharSequenceUtil.isBlankOrUndefined(fileStr)) {
                throw new ServiceException("文件内容不能为空");
            }
            List<PromptTransPOJO> jsonList = JSONUtil.toList(fileStr, PromptTransPOJO.class);
            if (CollUtil.isEmpty(jsonList)){
                throw new ServiceException("没有符合格式的Json数据");
            }
            for (PromptTransPOJO pt : jsonList) {
                try {
                    String title = pt.getKey();
                    String prompt = pt.getValue();
                    promptList.add(new PromptLibDO(title, prompt, BoolEnum.FALSE.getVar(), loginId));
                } catch (Exception ex) {
                    log.error("json 解析异常:", ex);
                }
            }
            if (CollUtil.isEmpty(promptList)){
                throw new ServiceException("没有符合格式的Json数据");
            }
            this.baseMapper.batchInsert(promptList);
        } catch (ServiceException bizEx) {
            throw bizEx;
        } catch (Exception ex) {
            log.error("[解析json文件异常] >>> exception:", ex);
            throw new ServiceException("解析json文件异常");
        }
    }

    @Override
    public SysPromptInfo getSysPromptInfo(String sysPromptLibId) {
        String sysPrompt = DEFAULT_SYS_PROMPT;
        PromptLibDO promptLibDO = this.baseMapper.selectById(sysPromptLibId);
        boolean reviewContent = false;
        if (Objects.nonNull(promptLibDO) && CharSequenceUtil.isNotBlank(promptLibDO.getPromptContent())) {
            sysPrompt = promptLibDO.getPromptContent();
            // 如果是用户共享,则需要对系统消息进行审核
            reviewContent = NumConstant.INT_ZERO.equals(promptLibDO.getType());
        }
        sysPrompt = PROMPT_IRON_PREFIX.concat(sysPrompt);
        return SysPromptInfo.builder()
                .sysPromptContent(sysPrompt)
                .reviewSysPrompt(reviewContent).build();
    }

    @Override
    public void contributePrompt(PromptContributeRequest request, Integer userId) {
        List<String> contentHitWords = SensitiveWordHandler.checkWord(request.getPromptContent());
        Assert.isTrue(CollUtil.isEmpty(contentHitWords), StrUtil.format("提示词包含敏感内容{},请修改后再行提交", JSONUtil.toJsonStr(contentHitWords)));
        List<String> titleHitWords = SensitiveWordHandler.checkWord(request.getTitle());
        Assert.isTrue(CollUtil.isEmpty(titleHitWords), StrUtil.format("标题包含敏感内容{},请修改后再行提交", JSONUtil.toJsonStr(titleHitWords)));
        PromptLibDO promptLibDO = new PromptLibDO(request.getTitle(), request.getPromptContent(), SysTypeEnum.CLIENT.getCode(), userId);
        long count = this.count(Wrappers.<PromptLibDO>lambdaQuery().eq(PromptLibDO::getContributeUid, userId).eq(PromptLibDO::getType, SysTypeEnum.CLIENT.getCode()));
        if (count >= NumConstant.FIFTY){
            throw new ServiceException("个人贡献提示词数量已达上限");
        }
        this.save(promptLibDO);
    }

    @Override
    public Page<PromptLibVO> getMineToolPage(PromptPageQuery query) {
        return baseMapper.selectClientUserToolPage(query.toPage(), query);
    }

    @Override
    public void editMinePrompt(PromptContributeRequest request, Integer userId) {
        PromptLibDO prompt = baseMapper.selectByUserIdAndPk(request.getId(), userId);
        if (CharSequenceUtil.isNotBlank(request.getTitle()) && !StrUtil.equals(request.getTitle(), prompt.getTitle())){
            List<String> titleHitWords = SensitiveWordHandler.checkWord(request.getTitle());
            Assert.isTrue(CollUtil.isEmpty(titleHitWords), StrUtil.format("标题包含敏感内容{},请修改后再行提交", JSONUtil.toJsonStr(titleHitWords)));
            prompt.setTitle(request.getTitle());
        }
        if (CharSequenceUtil.isNotBlank(request.getPromptContent()) && !StrUtil.equals(request.getPromptContent(), prompt.getPromptContent())){
            List<String> contentHitWords = SensitiveWordHandler.checkWord(request.getPromptContent());
            Assert.isTrue(CollUtil.isEmpty(contentHitWords), StrUtil.format("提示词包含敏感内容{},请修改后再行提交", JSONUtil.toJsonStr(contentHitWords)));
            prompt.setPromptContent(request.getPromptContent());
            prompt.setSummary(StrUtil.sub(request.getPromptContent(),0, 168));
        }
        this.updateById(prompt);
    }

    @Override
    public void deleteMinePrompt(PromptContributeRequest request, Integer userId) {
        PromptLibDO prompt = baseMapper.selectByUserIdAndPk(request.getId(), userId);
        if (prompt == null){
            return;
        }
        this.removeById(prompt.getId());
    }
}

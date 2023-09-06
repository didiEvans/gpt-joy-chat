package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.constants.CoreConstants;
import cloud.cstream.chat.core.domain.entity.OpenaiChatConfigDO;
import cloud.cstream.chat.core.domain.request.OpenaiChatConfigRequest;
import cloud.cstream.chat.core.mapper.OpenaiChatConfigMapper;
import cloud.cstream.chat.core.ob.servable.OpenAiConfigPropertiesObserved;
import cloud.cstream.chat.core.service.OpenaiChatConfigService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author evans
 * @description
 * @date 2023/6/3
 */
@Service
public class OpenaiChatConfigServiceImpl extends ServiceImpl<OpenaiChatConfigMapper, OpenaiChatConfigDO> implements OpenaiChatConfigService {


    @Autowired
    private OpenAiConfigPropertiesObserved observed;

    @Override
    public OpenaiChatConfigDO getOpenAiConfig() {
        return baseMapper.selectOne(Wrappers.<OpenaiChatConfigDO>lambdaQuery().last(CoreConstants.LIMIT_1));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateConf(OpenaiChatConfigRequest request) {
        OpenaiChatConfigDO openaiChatConfigDO = new OpenaiChatConfigDO();
        BeanUtil.copyProperties(request, openaiChatConfigDO,"updateTime");
        boolean updated = this.updateById(openaiChatConfigDO);
        if (updated){
            // 更新成功,刷新配置
            observed.refreshConf(openaiChatConfigDO);
        }
    }

    @Override
    public List<ChatCompletion.Model> getModelSelector() {
        return Lists.newArrayList(ChatCompletion.Model.values());
    }
}

package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.enums.BoolEnum;
import cloud.cstream.chat.common.enums.EnableDisableStatusEnum;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.core.domain.entity.SysAdConfigDO;
import cloud.cstream.chat.core.domain.pojo.CosFileResourcePair;
import cloud.cstream.chat.core.domain.query.SysAdConfigQuery;
import cloud.cstream.chat.core.domain.request.SysAdConfigRequest;
import cloud.cstream.chat.core.domain.vo.SysAdClientViewVO;
import cloud.cstream.chat.core.domain.vo.SysAdConfigVO;
import cloud.cstream.chat.core.mapper.SysAdConfigMapper;
import cloud.cstream.chat.core.service.FileService;
import cloud.cstream.chat.core.service.SysAdConfigService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SysAdConfigServiceImpl extends ServiceImpl<SysAdConfigMapper, SysAdConfigDO> implements SysAdConfigService {

    @Autowired
    private FileService fileService;

    @Override
    public Page<SysAdConfigVO> queryPage(SysAdConfigQuery pageQuery) {
        return baseMapper.queryPage(pageQuery.toPage(), pageQuery);
    }

    @Override
    public void add(SysAdConfigRequest request) {
        SysAdConfigDO sysAdConfigDO = new SysAdConfigDO();
        BeanUtil.copyProperties(request, sysAdConfigDO);
        this.save(sysAdConfigDO);
    }

    @Override
    public void updateConfig(SysAdConfigRequest request) {
        SysAdConfigDO sysAdConfigDO = new SysAdConfigDO();
        BeanUtil.copyProperties(request, sysAdConfigDO);
        this.updateById(sysAdConfigDO);
    }

    @Override
    public void enableSwitch(SysAdConfigRequest request) {
        SysAdConfigDO sysAdConfigDO = baseMapper.selectById(request.getId());
        if (Objects.isNull(sysAdConfigDO)) {
            throw new ServiceException("广告配置不存在");
        }
        Integer enableVar = BoolEnum.isTrue(sysAdConfigDO.getEnable()) ? EnableDisableStatusEnum.DISABLE.getCode() : EnableDisableStatusEnum.ENABLE.getCode();
        sysAdConfigDO.setEnable(enableVar);
        this.updateById(sysAdConfigDO);
    }


    @Override
    public List<SysAdClientViewVO> getAdList() {
        List<SysAdClientViewVO> adList = baseMapper.selectAdList();
        if (CollUtil.isEmpty(adList)){
            return adList;
        }
        for (SysAdClientViewVO ad : adList) {
            if (CharSequenceUtil.isNotBlank(ad.getCoverPic())){
                ad.setCoverPic(fileService.getPic(ad.getCoverPic()));
            }
        }
        return adList;
    }

    @Override
    public List<CosFileResourcePair> getAdCoverPicResources() {
        List<SysAdClientViewVO> adConfigs = baseMapper.selectAdList();
        List<CosFileResourcePair> pairs = this.buildPairs(adConfigs);
        return fileService.batchGetResource(pairs);
    }

    private List<CosFileResourcePair> buildPairs(List<SysAdClientViewVO> adConfigs) {
        return adConfigs.stream().map(a -> CosFileResourcePair.builder().fileKeys(Lists.newArrayList(a.getCoverPic())).field(a.getId().toString()).build()).collect(Collectors.toList());
    }
}

package cloud.cstream.chat.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_param")
public class SysParamDO {


    @TableId(type = IdType.AUTO)
    private Integer id;


    private String confKey;


    private String confValue;


    private Date createTime;


    private Date updateTime;



}

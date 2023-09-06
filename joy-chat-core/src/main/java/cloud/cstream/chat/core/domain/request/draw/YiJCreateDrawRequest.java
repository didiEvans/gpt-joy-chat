package cloud.cstream.chat.core.domain.request.draw;

import cloud.cstream.chat.common.constants.NumConstant;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class YiJCreateDrawRequest extends AiDrawBaseRequest {
    /**
     * 生成几张
     */
    private Integer size = NumConstant.INT_ONE;
    /**
     * 提示词
     */
    private String prompt;
    /**
     * 0是4 : 3, 1是3 : 4, 2是正方形, 3是16 : 9, 4是9 : 16。1:1(1024x1024), 4:3(1200x900), 3:4(900x1200), 16:9(1280x720), 9:16(720x1280)
     */
    private Integer ratio;
    /**
     * 风格字段，字符串，风格可以有很多。我们提供的风格在获取风格画家这个接口里。
     */
    private String style;
    /**
     * 引导力，默认为7.5,取值范围：3-25
     */
    private Double guidence_scale;
    /**
     * 最新的引擎不在罗列，参见风格画家接口，这个接口里的数据包含引擎类型。。
     * anything4_diffusion / stable_diffusion / lora_cod_diffusion / Gf_style2_diffusion / best_colorful_diffusion / protothing_diffusion / counterfeit_diffusion
     * anygen_diffusion / meina_diffusion/ flat_anime_diffusion / meiman_diffusion / lucky_real_diffusion / photoreal_engine / pvc_diffusion
     */
    private String engine;
    /**
     * 回调地址
     */
    private String callback_url;
    /**
     * progress和end,end任务结束的时候(失败也会)回调,progress有进度（失败也会）就会回调
     */
    private String callback_type;
    /**
     * 面部强化，不需要可以不传此字段
     */
    private Boolean enable_face_enhance;
    /**
     * 色彩狂化，不需要可以不传此字段
     */
    private Boolean is_last_layer_skip;
    /**
     * 参考图，不需要可以不传此字段,http图片和https图片均可。上传图片接口需要自己搞定，图片分辨率不要超过1000px*1000px，体积尽量小。不能传null!!!
     */
    private String init_image;
    /**
     * 图片相关性，0-70，不需要可以不传此字段
     */
    private String init_strength;
    /**
     * 步长参数，可选值：0：常规步长（默认）. 1: 加长步长
     */
    private String steps_mode;

    /**
     * 0 普通, 1 高清 + 1 积分, 2 精绘 +4 积分, 3 超精绘 + 15 积分
     */
    @NotNull(message = "请选择绘图精度")
    private Integer res_level;


}

package cloud.cstream.chat.core.domain.response;

import lombok.Data;

import java.util.List;

/**
 * 意间绘画样式选择列表下拉响应
 *
 * @author Anker
 */
@Data
public class YiJAiDrawStyleSelectResponse {
    /**
     * 图片尺寸列表
     */
    private List<ImageSize> imageSizes;
    /**
     * 稳定的画图方式
     */
    private List<StableArtists> stableArtists;
    /**
     * 样式类型
     */
    private List<StyleDetails> styleDetails;
    /**
     * 系统体诗词
     */
    private List<SystemPrompts> systemPrompts;






    @Data
    static class ImageSize{
        private String text;

        private String value;
    }

    @Data
    static class StableArtists{
        /*
             "id": "0",
                "img_words": "",
                "poster": "https://yijian-Draw-prod.cdn.bcebos.com/static/styles/style31.png",
                "text": "不限定",
                "value": ""
         */
        private Integer id;

        private String img_words;

        private String poster;

        private String text;

        private String value;
    }

    @Data
    static class StyleDetails{
        /*
          {
                "default_guide_scale": 7.5,
                "engine": "default_dreamer_diffusion",
                "group_name": "通用设计",
                "id": 0,
                "is_color_enhance": false,
                "poster": "https://yijian-Draw-prod.cdn.bcebos.com/static/styles/style31.png",
                "text": "智能",
                "value": ""
            },
         */
        private String default_guide_scale;

        private String engine;

        private String group_name;

        private Integer id;

        private Boolean is_color_enhance;

        private String poster;

        private String text;

        private String value;
    }

    @Data
    static class  SystemPrompts{
        /*
         {
                "artist_id": "0",
                "engine_id": "2",
                "text": "故乡的原野",
                "value": "Anime screenshot wide-shot landscape with house in the apple garden, beautiful ambiance, golden hour, studio ghibli style, by hayao miyazaki, highly detailed"
            }
         */
        private Integer artist_id;

        private Integer engine_id;

        private String text;

        private String value;
    }

}

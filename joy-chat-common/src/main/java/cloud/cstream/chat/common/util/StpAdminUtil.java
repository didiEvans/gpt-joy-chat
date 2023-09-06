package cloud.cstream.chat.common.util;

import cn.dev33.satoken.stp.StpLogic;
import cn.hutool.core.text.StrPool;

/**
 * @author Anker
 * @date 2023/4/16 23:20
 * 填写注释
 */
public class StpAdminUtil {

    /**
     * 账号类型标识
     */
    public static final String TYPE = "admin";

    /**
     * 底层的 StpLogic 对象
     */
    public static StpLogic stpLogic = new StpLogic(TYPE) {
        // 重写 StpLogic 类下的 `splicingKeyTokenName` 函数，返回一个与 `StpUtil` 不同的token名称, 防止冲突
        @Override
        public String splicingKeyTokenName() {
            return super.splicingKeyTokenName().concat(StrPool.DASHED).concat(TYPE);
        }
    }; ;

    /**
     * 检验当前会话是否已经登录，如未登录，则抛出异常
     */
    public static void checkLogin() {
        stpLogic.checkLogin();
    }

    // ------------------- 登录相关操作 -------------------

    // --- 登录

    /**
     * 会话登录
     * @param id 账号id，建议的类型：（long | int | String）
     */
    public static void login(Object id) {
        stpLogic.login(id);
    }



    /**
     * 会话登录
     * @param id 账号id，建议的类型：（long | int | String）
     */
    public static Integer getLoginId() {
        return Integer.parseInt(stpLogic.getLoginId().toString());
    }
}

package cloud.cstream.chat.common.util;


import cloud.cstream.chat.common.exception.ServiceException;

/**
 * @author Anker
 * @date 2023/3/28 22:47
 * 异常抛出工具类
 */
public class ThrowExceptionUtil {

    /**
     * 如果参数为 false 抛出异常
     *
     * @param result true/false
     * @return ThrowExceptionFunction
     **/
    public static ThrowExceptionFunction isFalse(boolean result) {
        return (errorMessage) -> {
            if (!result) {
                throw new ServiceException(errorMessage);
            }
        };
    }

    @FunctionalInterface
    public interface ThrowExceptionFunction {

        /**
         * 抛出异常信息
         *
         * @param message 异常信息
         **/
        void throwMessage(String message);
    }
}

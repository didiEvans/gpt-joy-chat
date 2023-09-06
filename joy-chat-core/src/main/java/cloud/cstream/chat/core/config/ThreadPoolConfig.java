package cloud.cstream.chat.core.config;

import cloud.cstream.chat.common.exception.ServiceException;
import cn.hutool.core.util.StrUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.Buffer;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ThreadPoolConfig {
    /**
     * 核心线程数
     */
    private final Integer corePoolSize = Runtime.getRuntime().availableProcessors();
    /**
     * 最大线程数
     */
    private final Integer maxSize = corePoolSize * 2;



    @Bean(name = "drawTaskThreadPool")
    public ThreadPoolExecutor DrawTaskThreadPool() {
        return new ThreadPoolExecutor(corePoolSize, maxSize, 1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(200, false),
                new BizThreadFactory("DrawTaskThreadPool"),
                new DrawTaskRejectHandler());
    }


    static class BizThreadFactory implements ThreadFactory {
        /**
         * 线程名称前缀
         */
        private final String namePrefix;

        private final AtomicInteger serialNo = new AtomicInteger(0);

        public BizThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(@NotNull Runnable r) {
            int threadNo = serialNo.incrementAndGet();
            String threadName = StrUtil.format("{}-{}", namePrefix, threadNo);
            return new Thread(r, threadName);
        }
    }

    static class DrawTaskRejectHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            throw new ServiceException("绘画任务队列已满, 请稍候再试");
        }
    }
}

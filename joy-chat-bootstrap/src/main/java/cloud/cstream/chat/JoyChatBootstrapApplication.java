package cloud.cstream.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

/**
 * 引导启动
 *
 * @author Anker
 */
@MapperScan(value = {"cloud.cstream.**.mapper"})
@SpringBootApplication( scanBasePackages = "cloud.cstream",
        nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
public class JoyChatBootstrapApplication {
    public static void main(String[] args) {
        SpringApplication.run(JoyChatBootstrapApplication.class, args);
    }
}
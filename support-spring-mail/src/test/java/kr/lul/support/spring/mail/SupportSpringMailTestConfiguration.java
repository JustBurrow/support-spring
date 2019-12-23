package kr.lul.support.spring.mail;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author justburrow
 * @since 2019/12/19
 */
@SpringBootApplication
@EnableAsync
public class SupportSpringMailTestConfiguration {
  @Bean
  public Executor executor() {
    return new ThreadPoolTaskExecutor();
  }
}

package kr.lul.support.spring.mail;

import org.springframework.scheduling.annotation.Async;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author justburrow
 * @since 2019/12/19
 */
public interface MailService {
  /**
   * 동기로 메일을 보낸다.
   *
   * @param params 메일 정보.
   *
   * @return 메일 발송 결과.
   */
  MailResult send(MailParams params);

  /**
   * 비동기로 메일을 보낸다.
   *
   * @param params 메일 정보.
   *
   * @return 메일 발송 결과.
   */
  @Async
  Future<MailResult> asyncSend(MailParams params);

  /**
   * 템플릿에 변수를 바인딩한다.
   *
   * @param template   템플릿 이름.
   * @param attributes 변수.
   *
   * @return 메일 본문.
   */
  String buildMessage(String template, Map<String, Object> attributes);
}

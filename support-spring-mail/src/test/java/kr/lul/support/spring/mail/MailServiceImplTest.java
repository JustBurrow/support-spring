package kr.lul.support.spring.mail;

import kr.lul.common.data.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/12/19
 */
@SpringBootTest(classes = SupportSpringMailTestConfiguration.class)
public class MailServiceImplTest {
  private static final Logger log = getLogger(MailServiceImplTest.class);

  @Autowired
  private ApplicationContext applicationContext;

  private MailService service;
  private Instant before;

  @BeforeEach
  public void setUp() throws Exception {
    assertThat(this.applicationContext).isNotNull();

    this.service = new MailServiceImpl();
    this.applicationContext.getAutowireCapableBeanFactory().autowireBean(this.service);

    this.before = Instant.now();
  }

  @Test
  public void test_send_with_null() throws Exception {
    assertThatThrownBy(() -> this.service.send(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasNoCause()
        .hasMessage("params is null.");
  }

  @Test
  public void test_send() throws Exception {
    // GIVEN
    MailParams params = new MailParams(new Context(randomUUID()), "Dev Test<just.burrow@lul.kr>", "just.burrow@lul.kr",
        "test_send title", "mail", true,
        Map.of("textAttr", "test_send content."));
    log.info("GIVEN - params={}", params);

    // WHEN
    MailResult result = this.service.send(params);
    log.info("WHEN - result={}", result);

    // THEN
    assertThat(result)
        .isNotNull()
        .extracting(MailResult::isSuccess, MailResult::getException)
        .containsSequence(true, null);
    assertThat(result.getStart())
        .isNotNull()
        .isAfterOrEqualTo(this.before)
        .isBefore(result.getEnd());
    assertThat(result.getEnd())
        .isNotNull();
    assertThat(result.responseTime())
        .isNotNull()
        .isEqualTo(Duration.between(result.getStart(), result.getEnd()));
  }

  @Test
  public void test_asyncSend_with_null() throws Exception {
    assertThatThrownBy(() -> this.service.asyncSend(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasNoCause()
        .hasMessage("params is null.");
  }

  public void test_asyncSend() throws Exception {
    // GIVEN
    MailParams params = new MailParams(new Context(randomUUID()), "Dev Test<just.burrow@lul.kr>", "just.burrow@lul.kr",
        "test_asyncSend title", "mail", true,
        Map.of("textAttr", "test_asyncSend content."));
    log.info("GIVEN - params={}", params);

    // WHEN
    log.info("WHEN - before call : {}", Instant.now());
    Future<MailResult> future = this.service.asyncSend(params);
    Instant after = Instant.now();
    log.info("WHEN - after call : {}", after);
    log.info("WHEN - future={}", future);
    MailResult result = future.get();

    // THEN
    assertThat(result)
        .isNotNull()
        .extracting(MailResult::isSuccess, MailResult::getException)
        .containsSequence(true, null);
    assertThat(result.getStart())
        .isNotNull()
        .isAfterOrEqualTo(this.before)
        .isBefore(result.getEnd())
        .isBefore(after);
    assertThat(result.getEnd())
        .isNotNull()
        .isAfterOrEqualTo(after);
    assertThat(result.responseTime())
        .isNotNull()
        .isEqualTo(Duration.between(result.getStart(), result.getEnd()));
  }

  @Test
  public void test_buildMessage_with_null_template() throws Exception {
    assertThatThrownBy(() -> this.service.buildMessage(null, new HashMap<>()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasNoCause()
        .hasMessage("template is null.");
  }

  @Test
  public void test_buildMessage_with_empty_template() throws Exception {
    assertThatThrownBy(() -> this.service.buildMessage("", new HashMap<>()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasNoCause()
        .hasMessage("template is empty.");
  }

  @Test
  public void test_buildMessage_with_null_attributes() throws Exception {
    assertThatThrownBy(() -> this.service.buildMessage("mail", null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasNoCause()
        .hasMessage("attributes is null.");
  }

  @Test
  public void test_buildMessage() throws Exception {
    // GIVEN
    String textAttr = "test_buildMessage";
    log.info("GIVEN - textAttr={}", textAttr);

    // WHEN
    String message = this.service.buildMessage("mail", Map.of("textAttr", textAttr));
    log.info("WHEN - message={}", message);

    // THEN
    assertThat(message)
        .isNotNull()
        .isEqualTo("<!DOCTYPE html>\n" +
                       "<html lang=\"ko_JP\">\n" +
                       "<head>\n" +
                       "    \n" +
                       "    \n" +
                       "    <meta charset=\"UTF-8\"/>\n" +
                       "    <title>Mail Template</title>\n" +
                       "</head>\n" +
                       "<body>\n" +
                       "<main>\n" +
                       "    <h1>Mail Template</h1>\n" +
                       "    \n" +
                       "    <p>test_buildMessage</p>\n" +
                       "</main>\n" +
                       "</body>\n" +
                       "</html>\n");
  }
}

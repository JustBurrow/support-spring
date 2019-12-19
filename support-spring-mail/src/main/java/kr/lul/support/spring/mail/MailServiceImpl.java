package kr.lul.support.spring.mail;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.AsyncResult;
import org.thymeleaf.TemplateEngine;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.Future;

import static java.util.Objects.requireNonNull;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/12/19
 */
public class MailServiceImpl implements MailService {
  private static final Logger log = getLogger(MailServiceImpl.class);

  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private TemplateEngine templateEngine;

  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.mailSender, "mailSender is not autowired.");
    requireNonNull(this.templateEngine, "templateEngine is not autowired.");
  }

  private MailResult doSend(MailParams params) {
    if (log.isTraceEnabled())
      log.trace("#doSend args : params={}", params);
    notNull(params, "params");

    final MimeMessagePreparator preparator = mimeMessage -> {
      final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
      helper.setFrom(params.getFrom());
      helper.setTo(params.getTo());
      helper.setSubject(params.getTitle());
      helper.setText(
          buildMessage(params.getTemplate(), params.getAttributes()),
          params.isHtml());
    };

    final MailResult result;
    Exception exception = null;
    final Instant start = Instant.now();
    Instant end = null;
    try {
      if (log.isInfoEnabled())
        log.info("#doSend (context={}) start={}", params.getContext(), start);
      this.mailSender.send(preparator);
      end = Instant.now();
      final Duration mailResponseTime = Duration.between(start, end);
      if (log.isInfoEnabled())
        log.info("#doSend (context={}) end={}, mailResponseTime={}", params.getContext(), end, mailResponseTime);
    } catch (final MailException e) {
      log.warn("#doSend (context=" + params.getContext() + ") fail to send email.", e);
      exception = e;
    } finally {
      result = null == exception
          ? new MailResult(start, Instant.now())
          : new MailResult(start, Instant.now(), exception);
    }

    if (log.isTraceEnabled())
      log.trace("#doSend (context={}) return : {}", params.getContext(), result);
    return result;
  }

  @Override
  public MailResult send(MailParams params) {
    return doSend(params);
  }

  @Override
  public Future<MailResult> asyncSend(MailParams params) {
    return new AsyncResult<>(doSend(params));
  }

  @Override
  public String buildMessage(final String template, final Map<String, Object> attributes) {
    if (log.isTraceEnabled())
      log.trace("#buildMessage args : template={}, attributes={}", template, attributes);
    notEmpty(template, "template");
    notNull(attributes, "attributes");

    final org.thymeleaf.context.Context ctx = new org.thymeleaf.context.Context();
    attributes.forEach(ctx::setVariable);

    final String message = this.templateEngine.process(template, ctx);

    if (log.isTraceEnabled())
      log.trace("#buildMessage return : {}", message);
    return message;
  }
}

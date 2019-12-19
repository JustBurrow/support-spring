package kr.lul.support.spring.mail;

import java.time.Duration;
import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019/12/19
 */
public class MailResult {
  /**
   * 메일 발송 요청 시각.
   */
  private Instant start;
  /**
   * 메일 발송 완료 시각.
   */
  private Instant end;
  /**
   * 메일 발송 성공 여부.
   */
  private boolean success;
  /**
   * 메일 발송 실패시 원인. 성공시에는 {@code null}.
   */
  private Exception exception;

  public MailResult(Instant start, Instant end) {
    notNull(start, "start");
    notNull(end, "end");

    if (!start.isBefore(end))
      throw new IllegalArgumentException(format("start is not before than end : start=%s, end=%s", start, end));

    this.start = start;
    this.end = end;
    this.success = true;
  }

  public MailResult(Instant start, Instant end, Exception exception) {
    notNull(start, "start");
    notNull(end, "end");
    notNull(exception, "exception");

    if (!start.isBefore(end))
      throw new IllegalArgumentException(format("start is not before than end : start=%s, end=%s", start, end));

    this.start = start;
    this.end = end;
    this.success = false;
    this.exception = exception;
  }

  public Instant getStart() {
    return this.start;
  }

  public Instant getEnd() {
    return this.end;
  }

  public boolean isSuccess() {
    return this.success;
  }

  public Exception getException() {
    return this.exception;
  }

  public Duration responseTime() {
    return Duration.between(this.start, this.end);
  }

  @Override
  public String toString() {
    return new StringBuilder(MailResult.class.getSimpleName())
        .append("{start=").append(this.start)
        .append(", end=").append(this.end)
        .append(", success=").append(this.success)
        .append(", exception=").append(this.exception)
        .append('}').toString();
  }
}

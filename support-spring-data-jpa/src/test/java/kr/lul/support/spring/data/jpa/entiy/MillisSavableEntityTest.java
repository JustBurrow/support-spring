package kr.lul.support.spring.data.jpa.entiy;

import kr.lul.common.util.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.Instant;

import static java.time.temporal.ChronoField.MICRO_OF_SECOND;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static kr.lul.support.spring.data.jpa.entiy.CreatableEntity.ATTR_CREATED_AT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/09
 */
public class MillisSavableEntityTest {
  private static final Logger log = getLogger(MillisSavableEntityTest.class);

  private Instant before;

  @BeforeEach
  void setUp() {
    this.before = Instant.now().with(NANO_OF_SECOND, 123_456_789L);
    log.info("SETUP - before={}", this.before);
  }

  @Test
  public void test_new() throws Exception {
    // WHEN
    MillisSavableEntity actual = new MillisSavableEntity() {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual.createdAt)
        .isNull();
  }

  @Test
  public void test_new_with_now() throws Exception {
    // WHEN
    MillisSavableEntity actual = new MillisSavableEntity(this.before) {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual.createdAt)
        .isAfterOrEqualTo(this.before.with(MICRO_OF_SECOND, 0L));
    assertThat(actual.updatedAt)
        .isEqualTo(actual.createdAt);
  }

  @Test
  public void test_new_with_null() throws Exception {
    // WHEN
    //noinspection ConstantConditions
    ValidationException ex = catchThrowableOfType(() -> new MillisSavableEntity(null) {
    }, ValidationException.class);
    log.info("WHEN - ex=" + ex);

    // THEN
    assertThat(ex)
        .isNotNull()
        .extracting("targetName", "target", "message")
        .containsSequence(ATTR_CREATED_AT, null, "createdAt is null.");
  }
}

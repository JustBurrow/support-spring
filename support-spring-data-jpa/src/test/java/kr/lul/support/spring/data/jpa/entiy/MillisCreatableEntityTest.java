package kr.lul.support.spring.data.jpa.entiy;

import kr.lul.common.util.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.Instant;

import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static kr.lul.support.spring.data.jpa.entiy.CreatableEntity.ATTR_CREATED_AT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/09
 */
public class MillisCreatableEntityTest {
  private static final Logger log = getLogger(MillisCreatableEntityTest.class);

  private Instant before;

  @BeforeEach
  void setUp() {
    this.before = Instant.now().with(NANO_OF_SECOND, 123_456_789L);
    log.info("SETUP - before={}", this.before);
  }

  @Test
  public void test_new() throws Exception {
    // WHEN
    MillisCreatableEntity actual = new MillisCreatableEntity() {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual.createdAt)
        .isNull();
  }

  @Test
  public void test_new_with_now() throws Exception {
    // WHEN
    MillisCreatableEntity actual = new MillisCreatableEntity(this.before) {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(MillisCreatableEntity::getCreatedAt)
        .isNotEqualTo(this.before);

    assertThat(actual.createdAt.getEpochSecond())
        .isEqualTo(this.before.getEpochSecond());
    assertThat(actual.createdAt.getNano())
        .isNotEqualTo(this.before.getNano())
        .isEqualTo((this.before.getNano() / 1_000_000L) * 1_000_000L);
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  public void test_new_with_null() throws Exception {
    // WHEN
    ValidationException ex = catchThrowableOfType(() -> new MillisCreatableEntity(null) {
    }, ValidationException.class);
    log.info("WHEN - ex=" + ex);

    // THEN
    assertThat(ex)
        .isNotNull()
        .extracting("targetName", "target", "message")
        .containsSequence(ATTR_CREATED_AT, null, "createdAt is null.");
  }
}

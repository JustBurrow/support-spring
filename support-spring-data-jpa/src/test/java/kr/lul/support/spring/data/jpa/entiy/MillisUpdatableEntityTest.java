package kr.lul.support.spring.data.jpa.entiy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.Instant;

import static java.time.temporal.ChronoField.MICRO_OF_SECOND;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class MillisUpdatableEntityTest {
  private static final Logger log = getLogger(MillisUpdatableEntityTest.class);

  private Instant before;

  @BeforeEach
  void setUp() {
    this.before = Instant.now().with(NANO_OF_SECOND, 123_456_789L);
    log.info("GIVEN - before={}", this.before);
  }

  @Test
  public void test_new() throws Exception {
    // WHEN
    MillisUpdatableEntity actual = new MillisUpdatableEntity() {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .extracting(MillisUpdatableEntity::getUpdatedAt)
        .isNull();
  }

  @Test
  public void test_new_with_updatedAt() throws Exception {
    // WHEN
    MillisUpdatableEntity actual = new MillisUpdatableEntity(this.before) {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual.updatedAt)
        .isNotEqualTo(this.before)
        .isAfterOrEqualTo(this.before.with(MICRO_OF_SECOND, 0L));
  }
}

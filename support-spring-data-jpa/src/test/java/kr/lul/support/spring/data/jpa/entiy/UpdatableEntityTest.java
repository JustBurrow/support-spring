package kr.lul.support.spring.data.jpa.entiy;

import org.junit.Test;
import org.slf4j.Logger;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class UpdatableEntityTest {
  private static final Logger log = getLogger(UpdatableEntityTest.class);

  @Test
  public void test_new() throws Exception {
    // WHEN
    UpdatableEntity actual = new UpdatableEntity() {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .extracting(UpdatableEntity::getUpdatedAt)
        .isNull();
  }

  @Test
  public void test_new_with_updatedAt() throws Exception {
    // GIVEN
    Instant updatedAt = Instant.now();
    log.info("GIVEN - updatedAt={}", updatedAt);

    // WHEN
    UpdatableEntity actual = new UpdatableEntity() {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .extracting(UpdatableEntity::getUpdatedAt)
        .isEqualTo(actual);
  }
}

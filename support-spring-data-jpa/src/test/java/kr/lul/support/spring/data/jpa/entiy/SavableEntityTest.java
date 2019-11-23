package kr.lul.support.spring.data.jpa.entiy;

import org.junit.Test;
import org.slf4j.Logger;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/09
 */
public class SavableEntityTest {
  private static final Logger log = getLogger(SavableEntityTest.class);

  @Test
  public void test_new() throws Exception {
    // WHEN
    SavableEntity actual = new SavableEntity() {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual.createdAt)
        .isNull();
  }

  @Test
  public void test_new_with_now() throws Exception {
    // GIVEN
    Instant createdAt = Instant.now();
    log.info("GIVEN - createdAt={}", createdAt);

    // WHEN
    SavableEntity actual = new SavableEntity(createdAt) {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(SavableEntity::getCreatedAt, SavableEntity::getUpdatedAt)
        .containsSequence(createdAt, createdAt);
  }
}

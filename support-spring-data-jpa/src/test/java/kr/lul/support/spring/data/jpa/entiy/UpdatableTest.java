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
public class UpdatableTest {
  private static final Logger log = getLogger(UpdatableTest.class);

  @Test
  public void test_constructor() throws Exception {
    // GIVEN
    Instant before = Instant.now();
    log.info("GIVEN - before={}", before);

    // WHEN
    Updatable actual = new Updatable() {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull();
    assertThat(actual.createdAt)
        .isNotNull()
        .isAfter(before)
        .isEqualTo(actual.updatedAt);
  }

  @Test
  public void test_constructor_with_now() throws Exception {
    // GIVEN
    Instant before = Instant.now();
    log.info("GIVEN - before={}", before);

    // WHEN
    Updatable actual = new Updatable(before) {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(Updatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(before, before);
  }
}

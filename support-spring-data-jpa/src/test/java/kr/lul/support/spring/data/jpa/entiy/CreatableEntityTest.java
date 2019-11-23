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
public class CreatableEntityTest {
  private static final Logger log = getLogger(CreatableEntityTest.class);

  @Test
  public void test_new() throws Exception {
    // WHEN
    CreatableEntity actual = new CreatableEntity() {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual.createdAt)
        .isNull();
  }

  @Test
  public void test_new_with_now() throws Exception {
    // GIVEN
    Instant before = Instant.now();
    log.info("GIVEN - before={}", before);

    // WHEN
    CreatableEntity actual = new CreatableEntity(before) {
    };
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(CreatableEntity::getCreatedAt)
        .isEqualTo(before);
  }
}

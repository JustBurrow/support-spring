package kr.lul.support.spring.data.jpa.entiy;

import kr.lul.common.util.ValidationException;
import org.junit.Test;
import org.slf4j.Logger;

import java.time.Instant;

import static kr.lul.support.spring.data.jpa.entiy.CreatableEntity.ATTR_CREATED_AT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
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

  @Test
  public void test_new_with_null() throws Exception {
    // WHEN
    ValidationException ex = catchThrowableOfType(() -> new CreatableEntity(null) {
    }, ValidationException.class);
    log.info("WHEN - ex=" + ex);

    // THEN
    assertThat(ex)
        .isNotNull()
        .extracting("targetName", "target", "message")
        .containsSequence(ATTR_CREATED_AT, null, "createdAt is null.");
  }
}

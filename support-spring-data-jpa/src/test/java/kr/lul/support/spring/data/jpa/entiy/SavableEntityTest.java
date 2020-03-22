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

  @Test
  public void test_new_with_null() throws Exception {
    // WHEN
    ValidationException ex = catchThrowableOfType(() -> new SavableEntity(null) {
    }, ValidationException.class);
    log.info("WHEN - ex=" + ex);

    // THEN
    assertThat(ex)
        .isNotNull()
        .extracting("targetName", "target", "message")
        .containsSequence(ATTR_CREATED_AT, null, "createdAt is null.");
  }
}

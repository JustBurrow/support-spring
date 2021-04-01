package kr.lul.support.spring.data.jpa.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/09
 */
public class InstantLongAttributeConverterTest {
  private static final Logger log = getLogger(InstantLongAttributeConverterTest.class);

  private InstantLongAttributeConverter converter;

  @BeforeEach
  public void setUp() throws Exception {
    this.converter = new InstantLongAttributeConverter();
  }

  @Test
  public void test_convertToDatabaseColumn_with_null() throws Exception {
    // WHEN
    Long actual = this.converter.convertToDatabaseColumn(null);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNull();
  }

  @Test
  public void test_convertToDatabaseColumn_with_now() throws Exception {
    // GIVEN
    Instant expected = Instant.now();
    log.info("GIVEN - expected={}", expected);

    // WHEN
    Long actual = this.converter.convertToDatabaseColumn(expected);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isEqualTo(expected.toEpochMilli());
  }

  @Test
  public void test_convertToEntityAttribute_with_null() throws Exception {
    // WHEN
    Instant actual = this.converter.convertToEntityAttribute(null);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNull();
  }

  @Test
  public void test_convertToEntityAttribute_with_current() throws Exception {
    // GIVEN
    long expected = System.currentTimeMillis();
    log.info("GIVEN - expected={}", expected);

    // WHEN
    Instant actual = this.converter.convertToEntityAttribute(expected);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull();
    assertThat(actual.toEpochMilli())
        .isEqualTo(expected);
  }

  @Test
  public void test_convertToEntityAttribute_with_0() throws Exception {
    // WHEN
    Instant actual = this.converter.convertToEntityAttribute(0L);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isEqualTo(Instant.EPOCH);
  }
}

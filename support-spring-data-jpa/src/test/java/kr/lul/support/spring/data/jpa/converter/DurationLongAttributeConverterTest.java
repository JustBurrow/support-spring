package kr.lul.support.spring.data.jpa.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.math.BigInteger;
import java.time.Duration;

import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.support.spring.data.jpa.converter.Constants.NANO_FOR_SECOND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/09
 */
public class DurationLongAttributeConverterTest {
  private static final Logger log = getLogger(DurationLongAttributeConverterTest.class);
  private DurationLongAttributeConverter converter;

  @BeforeEach
  public void setUp() throws Exception {
    this.converter = new DurationLongAttributeConverter();
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
  public void test_convertToDatabaseColumn_with_0sec() throws Exception {
    // GIVEN
    long expected = 0L;
    Duration duration = Duration.ofSeconds(expected);
    log.info("GIVEN - expected={}, duration={}", expected, duration);

    // WHEN
    Long actual = this.converter.convertToDatabaseColumn(duration);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  public void test_convertToDatabaseColumn_with_random() throws Exception {
    // GIVEN
    long day = 1000L * 60L * 60L * 24L;
    long second = current().nextLong(-day, day);
    long nano = current().nextLong(-NANO_FOR_SECOND + 1L, NANO_FOR_SECOND);
    Duration duration = Duration.ofSeconds(second, nano);
    log.info("GIVEN - duration={}, second={}, nano={}", duration, second, nano);

    // WHEN
    Long actual = this.converter.convertToDatabaseColumn(duration);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isEqualTo(Long.parseLong(new BigInteger("" + second)
                                      .multiply(new BigInteger("" + NANO_FOR_SECOND))
                                      .add(new BigInteger("" + nano))
                                      .divide(new BigInteger("" + (NANO_FOR_SECOND / 1_000L)))
                                      .toString()));
  }

  @Test
  public void test_convertToEntityAttribute_with_null() throws Exception {
    // WHEN
    Duration actual = this.converter.convertToEntityAttribute(null);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNull();
  }

  @Test
  public void test_convertToEntityAttribute_with_0() throws Exception {
    // WHEN
    Duration actual = this.converter.convertToEntityAttribute(0L);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isEqualTo(Duration.ZERO);
  }

  @Test
  public void test_convertToEntityAttribute_with_random() throws Exception {
    // GIVEN
    long expected = current().nextLong();
    log.info("GIVEN - expected={}", expected);

    // WHEN
    Duration actual = this.converter.convertToEntityAttribute(expected);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isEqualTo(Duration.ofMillis(expected));
  }
}

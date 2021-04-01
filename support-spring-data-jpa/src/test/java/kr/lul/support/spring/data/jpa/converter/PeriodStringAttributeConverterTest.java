package kr.lul.support.spring.data.jpa.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.Period;
import java.time.format.DateTimeParseException;

import static java.lang.String.format;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/09
 */
public class PeriodStringAttributeConverterTest {
  private static final Logger log = getLogger(PeriodStringAttributeConverterTest.class);

  private PeriodStringAttributeConverter converter;

  @BeforeEach
  public void setUp() throws Exception {
    this.converter = new PeriodStringAttributeConverter();
  }

  @Test
  public void test_convertToDatabaseColumn_with_null() throws Exception {
    // WHEN
    String actual = this.converter.convertToDatabaseColumn(null);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNull();
  }

  @Test
  public void test_convertToDatabaseColumn_with_0() throws Exception {
    // GIVEN
    Period expected = Period.of(0, 0, 0);
    log.info("GIVEN - expected={}", expected);

    // WHEN
    String actual = this.converter.convertToDatabaseColumn(expected);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotEmpty()
        .isEqualTo("P0D");
  }

  @Test
  public void test_convertToDatabaseColumn_with_random() throws Exception {
    // GIVEN
    int year = current().nextInt(-1000, 1000);
    int month = current().nextInt(-100, 100);
    int day = current().nextInt(-365, 365);
    Period expected = Period.of(year, month, day);
    log.info("GIVEN - expected={}, year={}, month={}, day={}", expected, year, month, day);

    // WHEN
    String actual = this.converter.convertToDatabaseColumn(expected);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isEqualTo(format("P%dY%dM%dD", year, month, day));
  }

  @Test
  public void test_convertToEntityAttribute_with_null() throws Exception {
    // WHEN
    Period actual = this.converter.convertToEntityAttribute(null);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNull();
  }

  @Test
  public void test_convertToEntityAttribute_with_0() throws Exception {
    // WHEN
    Period actual = this.converter.convertToEntityAttribute("P0D");
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isEqualTo(Period.ZERO);
  }

  @Test
  public void test_convertToEntityAttribute_with_empty() throws Exception {
    assertThatThrownBy(() -> this.converter.convertToEntityAttribute(""))
        .isInstanceOf(DateTimeParseException.class)
        .isNotNull();
  }

  @Test
  public void test_convertToEntityAttribute_with_random() throws Exception {
    // GIVEN
    int year = current().nextInt(-1000, 1000);
    int month = current().nextInt(-24, 24);
    int day = current().nextInt(-365, 365);
    String expected = format("P%dY%dM%dD", year, month, day);
    log.info("GIVEN - expected={}, year={}, month={}, day={}", expected, year, month, day);

    // WHEN
    Period actual = this.converter.convertToEntityAttribute(expected);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isEqualTo(Period.of(year, month, day));
  }
}

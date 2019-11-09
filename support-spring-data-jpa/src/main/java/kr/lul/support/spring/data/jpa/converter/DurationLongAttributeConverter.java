package kr.lul.support.spring.data.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;

/**
 * {@link Duration}을  milliseconds 단위로 변환한다.
 *
 * @author justburrow
 * @since 2019/11/06
 */
@Converter(autoApply = true)
public class DurationLongAttributeConverter implements AttributeConverter<Duration, Long> {
  @Override
  public Long convertToDatabaseColumn(Duration duration) {
    return null == duration
        ? null
        : duration.toMillis();
  }

  @Override
  public Duration convertToEntityAttribute(Long millis) {
    return null == millis
        ? null
        : Duration.ofMillis(millis);
  }
}

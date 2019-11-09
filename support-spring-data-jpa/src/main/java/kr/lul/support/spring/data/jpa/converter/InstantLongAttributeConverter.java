package kr.lul.support.spring.data.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;

/**
 * {@link Instant} &lt;=&gt; {@link Long}
 *
 * @author justburrow
 * @since 2019/11/06
 */
@Converter(autoApply = true)
public class InstantLongAttributeConverter implements AttributeConverter<Instant, Long> {
  @Override
  public Long convertToDatabaseColumn(Instant instant) {
    return null == instant
        ? null
        : instant.toEpochMilli();
  }

  @Override
  public Instant convertToEntityAttribute(Long epochMilli) {
    return null == epochMilli
        ? null
        : Instant.ofEpochMilli(epochMilli);
  }
}

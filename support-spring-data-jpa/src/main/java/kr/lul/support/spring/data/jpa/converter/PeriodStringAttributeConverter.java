package kr.lul.support.spring.data.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Period;

/**
 * @author justburrow
 * @since 2019/11/06
 */
@Converter(autoApply = true)
public class PeriodStringAttributeConverter implements AttributeConverter<Period, String> {
  @Override
  public String convertToDatabaseColumn(Period period) {
    return null == period
        ? null
        : period.toString();
  }

  @Override
  public Period convertToEntityAttribute(String text) {
    return null == text
        ? null
        : Period.parse(text);
  }
}

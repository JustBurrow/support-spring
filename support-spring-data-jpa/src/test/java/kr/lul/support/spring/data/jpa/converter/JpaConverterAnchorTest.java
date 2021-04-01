package kr.lul.support.spring.data.jpa.converter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author justburrow
 * @since 2019/11/09
 */
public class JpaConverterAnchorTest {
  @Test
  public void test_constructor() throws Exception {
    assertThatThrownBy(JpaConverterAnchor::new)
        .isInstanceOf(UnsupportedOperationException.class);
  }
}

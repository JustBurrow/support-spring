package kr.lul.support.spring.data.jpa.entiy;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author justburrow
 * @since 2019/11/09
 */
public class JpaEntityAnchorTest {
  @Test
  public void test_constructor() throws Exception {
    assertThatThrownBy(JpaEntityAnchor::new)
        .isInstanceOf(UnsupportedOperationException.class);
  }
}

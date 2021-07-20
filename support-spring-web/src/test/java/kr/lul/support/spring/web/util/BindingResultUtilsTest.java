package kr.lul.support.spring.web.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.constraints.NotEmpty;

import static kr.lul.support.spring.web.util.BindingResultUtils.validate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2021/07/20
 */
@SpringBootTest
@SpringBootApplication
@EnableWebMvc
public class BindingResultUtilsTest {
  private static final Logger LOGGER = getLogger(BindingResultUtilsTest.class);

  @Autowired
  private Validator validator;

  @Test
  public void test_new() {
    assertThatThrownBy(() -> new BindingResultUtils() {
    }).isInstanceOf(UnsupportedOperationException.class);
  }

  public static class A {
    @NotEmpty
    private String prop;

    public String getProp() {
      return this.prop;
    }

    public void setProp(String prop) {
      this.prop = prop;
    }
  }

  @Test
  public void test_validate_target_name_model_validator() {
    // GIVEN
    A a = new A();
    String name = "a";
    Model model = new ExtendedModelMap();
    LOGGER.info("[GIVEN] validator={}, model={}", this.validator, model);

    // WHEN
    BindingResult bindingResult = validate(a, name, model, this.validator);
    LOGGER.info("[WHEN] bindingResult={}", bindingResult);

    // THEN
    assertThat(bindingResult)
        .isNotNull();
    assertThat(model.getAttribute(name))
        .isNotNull();
    assertThat(model.getAttribute(BindingResult.class.getName() + "." + name))
        .isNotNull();

    FieldError error = bindingResult.getFieldError("prop");
    LOGGER.info("[THEN] error={}", error);

    assertThat(error)
        .isNotNull();
    assertThat(error.getObjectName())
        .isEqualTo(name);
    assertThat(error.getRejectedValue())
        .isNull();
  }
}
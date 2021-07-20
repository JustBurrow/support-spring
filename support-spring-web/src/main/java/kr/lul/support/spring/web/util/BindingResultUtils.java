package kr.lul.support.spring.web.util;

import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/07/18
 */
public abstract class BindingResultUtils {
  public static Model validate(Object target, String name, Validator validator) {
    Model model = new ExtendedModelMap();
    validate(target, name, model, validator);
    return model;
  }

  public static BindingResult validate(Object target, String name, Model model, Validator validator) {
    notNull(model, "model");

    DataBinder binder = new DataBinder(notNull(target, "target"), notEmpty(name, "name"));
    binder.setValidator(notNull(validator, "validator"));
    binder.validate();
    BindingResult bindingResult = binder.getBindingResult();

    model.addAttribute(name, target);
    model.addAttribute(format("%s.%s", BindingResult.class.getName(), name), bindingResult);

    return bindingResult;
  }

  protected BindingResultUtils() {
    throw new UnsupportedOperationException();
  }
}
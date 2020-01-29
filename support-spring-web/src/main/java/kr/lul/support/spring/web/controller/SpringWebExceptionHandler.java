package kr.lul.support.spring.web.controller;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/30
 */
@ControllerAdvice
public class SpringWebExceptionHandler {
  protected static final Logger log = getLogger(SpringWebExceptionHandler.class);

  public static final String VIEW_GROUP = "error";
  public static final String VIEW_400 = VIEW_GROUP + "/client/400";

  @ExceptionHandler(ServletException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String servletException(ServletException e, HttpServletRequest request, Model model) {
    if (log.isTraceEnabled())
      log.trace("#servletException args : e={}, request={}, model={}", e, request, model);

    String method = request.getMethod();
    StringBuffer url = request.getRequestURL();
    log.info(format("%s %s", method, url), e);

    if (log.isTraceEnabled())
      log.trace("#servletException result : template={}, model={}", VIEW_400, model);
    return VIEW_400;
  }
}

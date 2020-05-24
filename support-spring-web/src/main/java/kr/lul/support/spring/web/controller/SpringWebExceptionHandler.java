package kr.lul.support.spring.web.controller;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

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
  public static final String VIEW_404 = VIEW_GROUP + "/client/404";

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

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String methodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request, Model model) {
    if (log.isTraceEnabled())
      log.trace("#methodNotSupportedException args : e=" + e.getMessage() + ", request={}, model={}", request, model);

    return VIEW_404;
  }

  @ExceptionHandler(HttpClientErrorException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String badRequest(HttpClientErrorException e, HttpServletRequest request, Model model) {
    log.info("#badRequest args : e=" + e.getMessage() + ", request={}, model={}", request, model);

    return VIEW_400;
  }

  @ExceptionHandler({HttpClientErrorException.NotFound.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String notFound(HttpClientErrorException.NotFound e, HttpServletRequest request, Model model) {
    if (log.isDebugEnabled())
      log.debug("#notFound e=" + e.getMessage() + ", request={} {}, model={}",
          request.getMethod(), request.getRequestURL(), model);

    return VIEW_404;
  }
}

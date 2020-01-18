package kr.lul.support.spring.web.controller;

import kr.lul.common.web.http.status.exception.*;
import kr.lul.common.web.http.status.exception.client.*;
import kr.lul.common.web.http.status.exception.informationalresponse.Continue;
import kr.lul.common.web.http.status.exception.informationalresponse.EarlyHints;
import kr.lul.common.web.http.status.exception.informationalresponse.Processing;
import kr.lul.common.web.http.status.exception.informationalresponse.SwitchingProtocols;
import kr.lul.common.web.http.status.exception.redirection.*;
import kr.lul.common.web.http.status.exception.server.*;
import kr.lul.common.web.http.status.exception.success.*;
import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.*;

/**
 * {@link kr.lul.common.web.http.status.exception.HttpException} 에러 핸들러.
 *
 * @author justburrow
 * @since 2020/01/15
 */
@ControllerAdvice
public class CommonWebHttpExceptionHandler {
  protected static final Logger log = getLogger(CommonWebHttpExceptionHandler.class);

  public static final String VIEW_GROUP = "error";
  public static final String VIEW_100 = VIEW_GROUP + "/informational/1xx";
  public static final String VIEW_200 = VIEW_GROUP + "/success/2xx";
  public static final String VIEW_300 = VIEW_GROUP + "/redirection/3xx";
  public static final String VIEW_400 = VIEW_GROUP + "/client/4xx";
  public static final String VIEW_500 = VIEW_GROUP + "/server/5xx";

  /**
   * 1XX Informational response 공용 핸들러
   *
   * @param e       예외
   * @param request 리퀘스트
   * @param model   모델
   *
   * @return 뷰
   */

  protected String doInformationalResponse(Http1xxException e, WebRequest request, Model model) {
    if (log.isTraceEnabled())
      log.trace("#doInformationalResponse args : e={}, request={}, model={}", e, request, model);

    log.info(format("#doInformationalResponse result : view=%s, e=%s, model=%s", VIEW_100, e, model), e);
    return VIEW_100;
  }

  @ExceptionHandler(Http1xxException.class)
  @ResponseStatus(CONTINUE)
  public String informationalResponse(Http1xxException e, WebRequest request, Model model) {
    return doInformationalResponse(e, request, model);
  }

  @ExceptionHandler(Continue.class)
  @ResponseStatus(CONTINUE)
  public String doContinue(Continue e, WebRequest request, Model model) {
    return doInformationalResponse(e, request, model);
  }

  @ExceptionHandler(SwitchingProtocols.class)
  @ResponseStatus(SWITCHING_PROTOCOLS)
  public String switchingProtocols(SwitchingProtocols e, WebRequest request, Model model) {
    return doInformationalResponse(e, request, model);
  }

  @ExceptionHandler(Processing.class)
  @ResponseStatus(PROCESSING)
  public String processing(Processing e, WebRequest request, Model model) {
    return doInformationalResponse(e, request, model);
  }

  @ExceptionHandler(EarlyHints.class)
  @ResponseStatus(CHECKPOINT)
  public String earlyHints(EarlyHints e, WebRequest request, Model model) {
    return doInformationalResponse(e, request, model);
  }

  /**
   * 2XX Success 공용 핸들러.
   *
   * @param e       예외
   * @param request 리퀘스트
   * @param model   모델
   *
   * @return 뷰
   */
  protected String doSuccess(Http2xxException e, WebRequest request, Model model) {
    if (log.isTraceEnabled())
      log.trace("#doSuccess args : e={}, request={}, model={}", e, request, model);

    log.info(format("#doSuccess result : view=%s, e=%s, model=%s", VIEW_200, e, model), e);
    return VIEW_200;
  }

  @ExceptionHandler(Http2xxException.class)
  @ResponseStatus(OK)
  public String success(Http2xxException e, WebRequest request, Model model) {
    return doSuccess(e, request, model);
  }

  @ExceptionHandler(kr.lul.common.web.http.status.exception.success.OK.class)
  @ResponseStatus(OK)
  public String ok(kr.lul.common.web.http.status.exception.success.OK e, WebRequest request, Model model) {
    return doSuccess(e, request, model);
  }

  @ExceptionHandler(Created.class)
  @ResponseStatus(CREATED)
  public String created(Created e, WebRequest request, Model model) {
    return doSuccess(e, request, model);
  }

  @ExceptionHandler(Accepted.class)
  @ResponseStatus(ACCEPTED)
  public String accepted(Accepted e, WebRequest request, Model model) {
    return doSuccess(e, request, model);
  }

  @ExceptionHandler(NonAuthoritativeInformation.class)
  @ResponseStatus(NON_AUTHORITATIVE_INFORMATION)
  public String nonAuthoritativeInformation(NonAuthoritativeInformation e, WebRequest request, Model model) {
    return doSuccess(e, request, model);
  }

  @ExceptionHandler(NoContent.class)
  @ResponseStatus(NO_CONTENT)
  public String noContent(NoContent e, WebRequest request, Model model) {
    return doSuccess(e, request, model);
  }

  @ExceptionHandler(ResetContent.class)
  @ResponseStatus(RESET_CONTENT)
  public String resetContent(ResetContent e, WebRequest request, Model model) {
    return doSuccess(e, request, model);
  }

  @ExceptionHandler(PartialContent.class)
  @ResponseStatus(PARTIAL_CONTENT)
  public String partialContent(PartialContent e, WebRequest request, Model model) {
    return doSuccess(e, request, model);
  }

  @ExceptionHandler(MultiStatus.class)
  @ResponseStatus(MULTI_STATUS)
  public String multiStatus(MultiStatus e, WebRequest request, Model model) {
    return doSuccess(e, request, model);
  }

  @ExceptionHandler(AlreadyReported.class)
  @ResponseStatus(ALREADY_REPORTED)
  public String alreadyReported(AlreadyReported e, WebRequest request, Model model) {
    return doSuccess(e, request, model);
  }

  @ExceptionHandler(ImUsed.class)
  @ResponseStatus(IM_USED)
  public String imUsed(ImUsed e, WebRequest request, Model model) {
    return doSuccess(e, request, model);
  }

  /**
   * 3XX Redirection 공용 핸들러.
   *
   * @param e       예외
   * @param request 리퀘스트
   * @param model   모델
   *
   * @return 뷰
   */
  protected String doRedirection(Http3xxException e, WebRequest request, Model model) {
    if (log.isTraceEnabled())
      log.trace("#doRedirection args : e={}, request={}, model={}", e, request, model);

    log.info(format("#doRedirection result : view=%s, e=%s, model=%s", VIEW_300, e, model), e);
    return VIEW_300;
  }

  @ExceptionHandler(Http3xxException.class)
  @ResponseStatus(FOUND)
  public String redirection(Http3xxException e, WebRequest request, Model model) {
    return doRedirection(e, request, model);
  }

  @ExceptionHandler(MultipleChoices.class)
  @ResponseStatus(MULTIPLE_CHOICES)
  public String multipleChoices(MultipleChoices e, WebRequest request, Model model) {
    return doRedirection(e, request, model);
  }

  @ExceptionHandler(MovedPermanently.class)
  @ResponseStatus(MOVED_PERMANENTLY)
  public String movedPermanently(MovedPermanently e, WebRequest request, Model model) {
    return doRedirection(e, request, model);
  }

  @ExceptionHandler(Found.class)
  @ResponseStatus(FOUND)
  public String found(Found e, WebRequest request, Model model) {
    return doRedirection(e, request, model);
  }

  @ExceptionHandler(SeeOther.class)
  @ResponseStatus(SEE_OTHER)
  public String seeOther(SeeOther e, WebRequest request, Model model) {
    return doRedirection(e, request, model);
  }

  @ExceptionHandler(NotModified.class)
  @ResponseStatus(NOT_MODIFIED)
  public String notModified(NotModified e, WebRequest request, Model model) {
    return doRedirection(e, request, model);
  }

  @Deprecated
  @ExceptionHandler(UseProxy.class)
  @ResponseStatus(USE_PROXY)
  public String useProxy(UseProxy e, WebRequest request, Model model) {
    return doRedirection(e, request, model);
  }

  @ExceptionHandler(TemporaryRedirect.class)
  @ResponseStatus(TEMPORARY_REDIRECT)
  public String temporaryRedirect(TemporaryRedirect e, WebRequest request, Model model) {
    return doRedirection(e, request, model);
  }

  @ExceptionHandler(PermanentRedirect.class)
  @ResponseStatus(PERMANENT_REDIRECT)
  public String permanentRedirect(PermanentRedirect e, WebRequest request, Model model) {
    return doRedirection(e, request, model);
  }

  /**
   * 4XX Client error 공용 핸들러.
   *
   * @param e       예외
   * @param request 리퀘스트
   * @param model   모델
   *
   * @return 뷰
   */
  protected String doClientError(Http4xxException e, WebRequest request, Model model) {
    if (log.isTraceEnabled())
      log.trace("#doClientError args : e={}, request={}, model={}", e, request, model);

    log.info(format("#doClientError result : view=%s, e=%s, model=%s", VIEW_400, e, model), e);
    return VIEW_400;
  }

  @ExceptionHandler(Http4xxException.class)
  @ResponseStatus(BAD_REQUEST)
  public String clientError(Http4xxException e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(BadRequest.class)
  @ResponseStatus(BAD_REQUEST)
  public String badRequest(BadRequest e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(Unauthorized.class)
  @ResponseStatus(UNAUTHORIZED)
  public String unauthorized(Unauthorized e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(PaymentRequired.class)
  @ResponseStatus(PAYMENT_REQUIRED)
  public String paymentRequired(PaymentRequired e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(Forbidden.class)
  @ResponseStatus(FORBIDDEN)
  public String forbidden(Forbidden e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(NotFound.class)
  @ResponseStatus(NOT_FOUND)
  public String notFound(NotFound e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(MethodNotAllowed.class)
  @ResponseStatus(METHOD_NOT_ALLOWED)
  public String methodNotAllowed(MethodNotAllowed e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(NotAcceptable.class)
  @ResponseStatus(NOT_ACCEPTABLE)
  public String notAcceptable(NotAcceptable e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(ProxyAuthenticationRequired.class)
  @ResponseStatus(PROXY_AUTHENTICATION_REQUIRED)
  public String proxyAuthenticationRequired(ProxyAuthenticationRequired e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(RequestTimeout.class)
  @ResponseStatus(REQUEST_TIMEOUT)
  public String requestTimeout(RequestTimeout e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(Conflict.class)
  @ResponseStatus(CONFLICT)
  public String conflict(Conflict e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(Gone.class)
  @ResponseStatus(GONE)
  public String gone(Gone e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(LengthRequired.class)
  @ResponseStatus(LENGTH_REQUIRED)
  public String lengthRequired(LengthRequired e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(PreconditionFailed.class)
  @ResponseStatus(PRECONDITION_FAILED)
  public String preconditionFailed(PreconditionFailed e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(PayloadTooLarge.class)
  @ResponseStatus(PAYLOAD_TOO_LARGE)
  public String payloadTooLarge(PayloadTooLarge e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(UriTooLong.class)
  @ResponseStatus(URI_TOO_LONG)
  public String uriTooLong(UriTooLong e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(UnsupportedMediaType.class)
  @ResponseStatus(UNSUPPORTED_MEDIA_TYPE)
  public String unsupportedMediaType(UnsupportedMediaType e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(RangeNotSatisfiable.class)
  @ResponseStatus(REQUESTED_RANGE_NOT_SATISFIABLE)
  public String rangeNotSatisfiable(RangeNotSatisfiable e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(ExpectationFailed.class)
  @ResponseStatus(EXPECTATION_FAILED)
  public String expectationFailed(ExpectationFailed e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(MisdirectedRequest.class)
  @ResponseStatus(DESTINATION_LOCKED)
  @Deprecated
  public String misdirectedRequest(MisdirectedRequest e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(UnprocessableEntity.class)
  @ResponseStatus(UNPROCESSABLE_ENTITY)
  public String unprocessableEntity(UnprocessableEntity e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(Locked.class)
  @ResponseStatus(LOCKED)
  public String locked(Locked e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(FailedDependency.class)
  @ResponseStatus(FAILED_DEPENDENCY)
  public String failedDependency(FailedDependency e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(TooEarly.class)
  @ResponseStatus(TOO_EARLY)
  public String tooEarly(TooEarly e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(UpgradeRequired.class)
  @ResponseStatus(UPGRADE_REQUIRED)
  public String upgradeRequired(UpgradeRequired e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(PreconditionRequired.class)
  @ResponseStatus(PRECONDITION_REQUIRED)
  public String preconditionRequired(PreconditionRequired e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(TooManyRequests.class)
  @ResponseStatus(TOO_MANY_REQUESTS)
  public String tooManyRequests(TooManyRequests e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(RequestHeaderFieldsTooLarge.class)
  @ResponseStatus(REQUEST_HEADER_FIELDS_TOO_LARGE)
  public String requestHeaderFieldsTooLarge(RequestHeaderFieldsTooLarge e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  @ExceptionHandler(UnavailableForLegalReasons.class)
  @ResponseStatus(UNAVAILABLE_FOR_LEGAL_REASONS)
  public String unavailableForLegalReasons(UnavailableForLegalReasons e, WebRequest request, Model model) {
    return doClientError(e, request, model);
  }

  /**
   * 5XX Server errors
   *
   * @param e       에러
   * @param request 리퀘스트
   * @param model   모델
   *
   * @return 뷰
   */
  protected String doServerError(Http5xxException e, WebRequest request, Model model) {
    if (log.isTraceEnabled())
      log.trace("#doServerError args : e={}, request={}, model={}", e, request, model);

    log.info(format("#doServerError view=%s, e=%s, model=%s", VIEW_500, e, model), e);
    return VIEW_500;
  }

  @ExceptionHandler(Http5xxException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public String serverError(Http5xxException e, WebRequest request, Model model) {
    return doServerError(e, request, model);
  }

  @ExceptionHandler(InternalServerError.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public String internalServerError(InternalServerError e, WebRequest request, Model model) {
    return doServerError(e, request, model);
  }

  @ExceptionHandler(NotImplemented.class)
  @ResponseStatus(NOT_IMPLEMENTED)
  public String notImplemented(NotImplemented e, WebRequest request, Model model) {
    return doServerError(e, request, model);
  }

  @ExceptionHandler(BadGateway.class)
  @ResponseStatus(BAD_GATEWAY)
  public String badGateway(BadGateway e, WebRequest request, Model model) {
    return doServerError(e, request, model);
  }

  @ExceptionHandler(ServiceUnavailable.class)
  @ResponseStatus(SERVICE_UNAVAILABLE)
  public String serviceUnavailable(ServiceUnavailable e, WebRequest request, Model model) {
    return doServerError(e, request, model);
  }

  @ExceptionHandler(GatewayTimeout.class)
  @ResponseStatus(GATEWAY_TIMEOUT)
  public String gatewayTimeout(GatewayTimeout e, WebRequest request, Model model) {
    return doServerError(e, request, model);
  }

  @ExceptionHandler(HttpVersionNotSupported.class)
  @ResponseStatus(HTTP_VERSION_NOT_SUPPORTED)
  public String httpVersionNotSupported(HttpVersionNotSupported e, WebRequest request, Model model) {
    return doServerError(e, request, model);
  }

  @ExceptionHandler(VariantAlsoNegotiates.class)
  @ResponseStatus(VARIANT_ALSO_NEGOTIATES)
  public String variantAlsoNegotiates(VariantAlsoNegotiates e, WebRequest request, Model model) {
    return doServerError(e, request, model);
  }

  @ExceptionHandler(InsufficientStorage.class)
  @ResponseStatus(INSUFFICIENT_STORAGE)
  public String insufficientStorage(InsufficientStorage e, WebRequest request, Model model) {
    return doServerError(e, request, model);
  }

  @ExceptionHandler(LoopDetected.class)
  @ResponseStatus(LOOP_DETECTED)
  public String loopDetected(LoopDetected e, WebRequest request, Model model) {
    return doServerError(e, request, model);
  }

  @ExceptionHandler(NotExtended.class)
  @ResponseStatus(NOT_EXTENDED)
  public String notExtended(NotExtended e, WebRequest request, Model model) {
    return doServerError(e, request, model);
  }

  @ExceptionHandler(NetworkAuthenticationRequired.class)
  @ResponseStatus(NETWORK_AUTHENTICATION_REQUIRED)
  public String networkAuthenticationRequired(NetworkAuthenticationRequired e, WebRequest request, Model model) {
    return doServerError(e, request, model);
  }
}

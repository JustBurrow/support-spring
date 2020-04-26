package kr.lul.support.spring.common.context;

import kr.lul.common.data.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.UUID.randomUUID;

/**
 * <ol>
 * <li>유효한 컨텍스트가 있을 경우에는 {@link #issue()}가 {@link IllegalStateException}을 던진다.</li>
 * <li>유효한 컨텍스트가 없으면 {@link #get()}, {@link #clear()}가 {@link IllegalStateException}을 던진다.</li>
 * </ol>
 *
 * @author justburrow
 * @since 2019/12/07
 */
public class StrictContextService implements ContextService {
  private static final Logger log = LoggerFactory.getLogger(StrictContextService.class);
  private static final String MSG_CONTEXT_DOES_NOT_EXIST = "context does not exist.";

  private final Object lock = new Object();
  private final ThreadLocal<Context> holder = new ThreadLocal<>();

  @Override
  public Context issue() {
    synchronized (this.lock) {
      Context context = this.holder.get();
      if (null != context) {
        String msg = "context already exists : " + context;
        log.warn(msg);
        throw new IllegalStateException(msg);
      }

      context = new Context(randomUUID());
      this.holder.set(context);

      if (log.isInfoEnabled())
        log.info("#issue return : {}", context);
      return context;
    }
  }

  @Override
  public Context get() {
    synchronized (this.lock) {
      Context context = this.holder.get();
      if (null == context) {
        log.warn("#get " + MSG_CONTEXT_DOES_NOT_EXIST);
        throw new IllegalStateException(MSG_CONTEXT_DOES_NOT_EXIST);
      }

      if (log.isTraceEnabled())
        log.trace("#get return : {}", context);
      return context;
    }
  }

  @Override
  public boolean clear() {
    synchronized (this.lock) {
      Context context = this.holder.get();
      if (null == context) {
        log.warn("#clear " + MSG_CONTEXT_DOES_NOT_EXIST);
        throw new IllegalStateException(MSG_CONTEXT_DOES_NOT_EXIST);
      }

      this.holder.remove();
      return true;
    }
  }
}

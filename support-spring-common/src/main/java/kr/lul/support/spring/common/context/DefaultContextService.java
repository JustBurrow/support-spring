package kr.lul.support.spring.common.context;

import kr.lul.common.data.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author justburrow
 * @since 2019/12/07
 */
public class DefaultContextService implements ContextService {
  private static final Logger log = LoggerFactory.getLogger(DefaultContextService.class);

  private final Object lock = new Object();
  private ThreadLocal<Context> holder = new ThreadLocal<>();

  @Override
  public Context issue() {
    synchronized (this.lock) {
      Context context = new Context();
      if (log.isInfoEnabled())
        log.info("#issue context={}", context);
      this.holder.set(context);
      return context;
    }
  }

  @Override
  public Context get() {
    synchronized (this.lock) {
      Context context = this.holder.get();
      if (log.isInfoEnabled())
        log.info("#get current : context={}", context);
      if (null == context) {
        context = new Context();
        if (log.isInfoEnabled())
          log.info("#get new : context={}", context);
        this.holder.set(context);
      }
      return context;
    }
  }

  @Override
  public boolean clear() {
    synchronized (this.lock) {
      Context context = this.holder.get();
      if (log.isInfoEnabled())
        log.info("#clear current : context={}", context);

      if (null == context)
        this.holder.remove();
      return null != context;
    }
  }
}

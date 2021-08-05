package kr.lul.support.spring.data.jpa.listener;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2021/08/01
 */
public class InjectListener implements ApplicationContextAware {
  private static final Logger LOGGER = getLogger(InjectListener.class);
  private static final Object[] LOCK = new Object[0];

  private static AutowireCapableBeanFactory BEAN_FACTORY;

  @PostPersist
  @PostLoad
  @PostUpdate
  public void inject(Object entity) {
    BEAN_FACTORY.autowireBean(entity);
  }

  @Override
  public void setApplicationContext(ApplicationContext context) throws BeansException {
    synchronized (LOCK) {
      if (null == BEAN_FACTORY) {
        LOGGER.info("#setApplicationContext set : context={}", context);
        BEAN_FACTORY = context.getAutowireCapableBeanFactory();
      } else {
        LOGGER.warn("#setApplicationContext already set : BEAN_FACTORY={}, context={}", BEAN_FACTORY, context);
      }
    }
  }
}
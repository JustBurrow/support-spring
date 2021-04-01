package kr.lul.support.spring.common.context;

import kr.lul.common.data.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author justburrow
 * @since 2019/12/11
 */
public class StrictContextServiceTest {
  private static final Logger log = LoggerFactory.getLogger(StrictContextServiceTest.class);

  private ContextService service;

  @BeforeEach
  public void setUp() throws Exception {
    this.service = new StrictContextService();
  }

  @Test
  public void test_issue() throws Exception {
    // WHEN
    Context context = this.service.issue();
    log.info("WHEN - context={}", context);

    // THEN
    assertThat(context)
        .isNotNull()
        .extracting(Context::getId)
        .isNotNull();
  }

  @Test
  public void test_issue_twice() throws Exception {
    // GIVEN
    Context context = this.service.issue();
    log.info("GIVEN - context={}", context);

    // WHEN & THEN
    assertThatThrownBy(() -> this.service.issue())
        .isInstanceOf(IllegalStateException.class)
        .hasMessageStartingWith("context already exists")
        .hasMessageContaining(context.getId().toString());
  }

  @Test
  public void test_get_before_issue() throws Exception {
    assertThatThrownBy(() -> this.service.get())
        .isInstanceOf(IllegalStateException.class)
        .hasMessageStartingWith("context does not exist.");
  }

  @Test
  public void test_get() throws Exception {
    // GIVEN
    Context expected = this.service.issue();
    log.info("GIVEN - expected={}", expected);

    // WHEN
    Context actual = this.service.get();
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isEqualTo(expected)
        .extracting(Context::getId)
        .isNotNull()
        .isEqualTo(expected.getId());
  }

  @Test
  public void test_clear_before_issue() throws Exception {
    assertThatThrownBy(() -> this.service.clear())
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("context does not exist.");
  }

  @Test
  public void test_clear() throws Exception {
    // GIVEN
    Context context = this.service.issue();
    log.info("GIVEN - context={}", context);

    // WHEN
    boolean clear = this.service.clear();
    log.info("WHEN - clear={}", clear);

    // THEN
    assertThat(clear)
        .isTrue();
    assertThatThrownBy(() -> this.service.get())
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("context does not exist.");
  }

  @Test
  public void test_clear_and_issue() throws Exception {
    // GIVEN
    Context expected = this.service.issue();
    log.info("GIVEN - expected={}", expected);

    boolean clear = this.service.clear();
    log.info("GIVEN - clear={}", clear);

    // WHEN
    Context actual = this.service.issue();
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isNotEqualTo(expected)
        .extracting(Context::getId)
        .isNotNull()
        .isNotEqualTo(expected.getId());
  }

  class ContextRunnable implements Runnable {
    final Object lock;
    Context context;
    boolean clear;

    ContextRunnable(Object lock) {
      this.lock = lock;
    }

    @Override
    public void run() {
      synchronized (this.lock) {
        try {
          this.context = StrictContextServiceTest.this.service.issue();
          this.lock.wait();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      this.clear = StrictContextServiceTest.this.service.clear();
    }

    @Override
    public String toString() {
      return String.format("%s{context=%s, clear=%b}", ContextRunnable.class.getSimpleName(), this.context, this.clear);
    }
  }

  @Test
  public void test_issue_multi_thread() throws Exception {
    // GIVEN
    final Object lock = new Object();
    ContextRunnable cr1 = new ContextRunnable(lock);
    log.info("GIVEN - cr1={}", cr1);
    ContextRunnable cr2 = new ContextRunnable(lock);
    log.info("GIVEN - cr2={}", cr2);

    Thread t1 = new Thread(cr1);
    log.info("GIVEN - t1={}", t1);
    Thread t2 = new Thread(cr2);
    log.info("GIVEN - t2={}", t2);

    // WHEN
    t1.start();
    t2.start();
    sleep(10L);
    log.info("WHEN - t1={}, cr1={}", t1, cr1.context);
    log.info("WHEN - t2={}, cr2={}", t2, cr2.context);

    // THEN
    synchronized (lock) {
      assertThat(cr1.context)
          .isNotNull()
          .isNotEqualTo(cr2.context)
          .extracting(Context::getId)
          .isNotEqualTo(cr2.context.getId());
      lock.notifyAll();
    }
    sleep(10L);
    assertThat(cr1.clear)
        .isTrue();
    assertThat(cr2.clear)
        .isTrue();
  }
}

package kr.lul.support.spring.data.common;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Integer.MIN_VALUE;
import static java.util.stream.Collectors.toList;
import static kr.lul.support.spring.data.common.PageUtils.toPage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2021/08/21
 */
class PageUtilsTest {
  private static final Logger LOGGER = getLogger(PageUtilsTest.class);

  @Test
  void test_new() {
    assertThatThrownBy(() -> new PageUtils() {
    })
        .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void test_toPage_with_illegal_params() {
    assertThatThrownBy(() -> toPage(MIN_VALUE, 1, List.of(1)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("page is negative");
    assertThatThrownBy(() -> toPage(-1, 1, List.of(2)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("page is negative");
    assertThatThrownBy(() -> toPage(0, MIN_VALUE, List.of(3)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("size is not positive");
    assertThatThrownBy(() -> toPage(0, -1, List.of(4)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("size is not positive");
    assertThatThrownBy(() -> toPage(0, 0, List.of(5)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("size is not positive");
    assertThatThrownBy(() -> toPage(0, 10, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("list is null.");
  }

  @Test
  void test_toPage_with_empty_list() {
    // GIVEN
    List<String> list = List.of();

    // WHEN
    Page<String> p1 = toPage(0, 1, list);
    Page<String> p2 = toPage(0, 10, list);
    Page<String> p3 = toPage(1, 1, list);
    Page<String> p4 = toPage(1, 10, list);
    LOGGER.info("[WHEN] p1={}, p2={}, p3={}, p4={}", p1, p2, p3, p4);

    // THEN
    assertThat(p1).isNotNull();
    assertThat(p1.getTotalElements()).isEqualTo(0);
    assertThat(p1.getTotalPages()).isEqualTo(0);
    assertThat(p1.getNumber()).isEqualTo(0);
    assertThat(p1.getSize()).isEqualTo(1);
    assertThat(p1.getNumberOfElements()).isEqualTo(0);
    assertThat(p1.getContent()).isEmpty();

    assertThat(p2).isNotNull();
    assertThat(p2.getTotalElements()).isEqualTo(0);
    assertThat(p2.getTotalPages()).isEqualTo(0);
    assertThat(p2.getNumber()).isEqualTo(0);
    assertThat(p2.getSize()).isEqualTo(10);
    assertThat(p2.getNumberOfElements()).isEqualTo(0);
    assertThat(p2.getContent()).isEmpty();

    assertThat(p3).isNotNull();
    assertThat(p3.getTotalElements()).isEqualTo(0);
    assertThat(p3.getTotalPages()).isEqualTo(0);
    assertThat(p3.getNumber()).isEqualTo(1);
    assertThat(p3.getSize()).isEqualTo(1);
    assertThat(p3.getNumberOfElements()).isEqualTo(0);
    assertThat(p3.getContent()).isEmpty();

    assertThat(p4).isNotNull();
    assertThat(p4.getTotalElements()).isEqualTo(0);
    assertThat(p4.getTotalPages()).isEqualTo(0);
    assertThat(p4.getNumber()).isEqualTo(1);
    assertThat(p4.getSize()).isEqualTo(10);
    assertThat(p4.getNumberOfElements()).isEqualTo(0);
    assertThat(p4.getContent()).isEmpty();
  }

  @Test
  void test_toPage() {
    // GIVEN
    List<Integer> list = IntStream.range(0, 12).boxed().collect(toList());
    LOGGER.info("[GIVEN] list={}", list);

    // WHEN
    Page<Integer> p1 = toPage(0, 10, list);
    Page<Integer> p2 = toPage(1, 10, list);
    Page<Integer> p3 = toPage(2, 10, list);
    LOGGER.info("[WHEN] p1={}, p2={}, p3={}", p1, p2, p3);

    // THEN
    assertThat(p1).isNotNull();
    assertThat(p1.getTotalPages()).isEqualTo(2);
    assertThat(p1.getNumber()).isEqualTo(0);
    assertThat(p1.getTotalElements()).isEqualTo(12);
    assertThat(p1.getSize()).isEqualTo(10);
    assertThat(p1.getNumberOfElements()).isEqualTo(10);
    assertThat(p1.getContent())
        .hasSize(10)
        .containsSequence(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    assertThat(p2).isNotNull();
    assertThat(p2.getTotalPages()).isEqualTo(2);
    assertThat(p2.getNumber()).isEqualTo(1);
    assertThat(p2.getTotalElements()).isEqualTo(12);
    assertThat(p2.getSize()).isEqualTo(10);
    assertThat(p2.getNumberOfElements()).isEqualTo(2);
    assertThat(p2.getContent())
        .hasSize(2)
        .containsSequence(10, 11);

    assertThat(p3).isNotNull();
    assertThat(p3.getTotalPages()).isEqualTo(2);
    assertThat(p3.getNumber()).isEqualTo(2);
    assertThat(p3.getTotalElements()).isEqualTo(12);
    assertThat(p3.getSize()).isEqualTo(10);
    assertThat(p3.getNumberOfElements()).isEqualTo(0);
    assertThat(p3.getContent()).isEmpty();
  }
}
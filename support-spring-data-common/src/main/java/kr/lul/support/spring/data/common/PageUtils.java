package kr.lul.support.spring.data.common;

import org.springframework.data.domain.Page;

import java.util.List;

import static kr.lul.common.util.Arguments.*;

/**
 * @author justburrow
 * @since 2021/08/21
 */
public abstract class PageUtils {
  /**
   * 리스트에서 지정한 영역을 가진 {@link Page}로 변환한다.
   *
   * @param page 페이지 번호. 0-based.
   * @param size 페이지의 최대 데이터 갯수.
   * @param list 원본 리스트.
   * @param <T>  데이터 타입.
   *
   * @return 페이지.
   */
  public static <T> Page<T> toPage(int page, int size, List<T> list) {
    notNegative(page, "page");
    positive(size, "size");
    notNull(list, "list");

    return null;
  }

  protected PageUtils() {
    throw new UnsupportedOperationException();
  }
}
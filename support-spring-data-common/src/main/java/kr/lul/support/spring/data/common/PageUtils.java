package kr.lul.support.spring.data.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static kr.lul.common.util.Arguments.*;

/**
 * 직접 {@link Page}를 만들어 사용할 수 있는 유틸리티 모음.
 *
 * @author justburrow
 * @see Page
 * @since 2021/08/21
 */
public abstract class PageUtils {
  /**
   * 리스트에서 지정한 서브리스트를 가진 {@link Page}로 변환한다.
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

    Pageable pageable = Pageable.ofSize(size)
        .withPage(page);
    int start = page * size;
    List<T> contents = ( list.size() <= start )
        ? List.of()
        : list.subList(start, Math.min(( page + 1 ) * size, list.size()));

    return new PageImpl<>(contents, pageable, list.size());
  }

  protected PageUtils() {
    throw new UnsupportedOperationException();
  }
}
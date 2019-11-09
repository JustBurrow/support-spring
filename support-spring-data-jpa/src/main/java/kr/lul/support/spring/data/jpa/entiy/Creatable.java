package kr.lul.support.spring.data.jpa.entiy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;

/**
 * 생성 가능한 엔티티의 기본 코드.
 *
 * @author justburrow
 * @since 2019/11/09
 */
@MappedSuperclass
public abstract class Creatable {
  public static final String ATTR_CREATED_AT = "createdAt";
  public static final String COL_CREATED_AT = "created_at";

  @Column(name = COL_CREATED_AT, nullable = false, updatable = false)
  protected Instant createdAt;

  public Creatable() {
    this(Instant.now());
  }

  public Creatable(Instant createdAt) {
    notNull(createdAt, ATTR_CREATED_AT);

    this.createdAt = createdAt;
  }

  /**
   * 게으른 엔티티 생성시각 설정 등의 처리.
   */
  @PrePersist
  protected void prePersist() {
  }

  public Instant getCreatedAt() {
    return this.createdAt;
  }

  @Override
  public String toString() {
    return format("%s=%s", ATTR_CREATED_AT, this.createdAt);
  }
}

package kr.lul.support.spring.data.jpa.entiy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;

/**
 * 생성과 갱신이 가능한 엔티티의 기본 코드.
 *
 * @author justburrow
 * @since 2019/11/09
 */
@MappedSuperclass
public abstract class Updatable {
  public static final String ATTR_CREATED_AT = "createdAt";
  public static final String ATTR_UPDATED_AT = "updatedAt";

  public static final String COL_CREATED_AT = "created_at";
  public static final String COL_UPDATED_AT = "updated_at";

  @Column(name = COL_CREATED_AT, nullable = false, updatable = false)
  protected Instant createdAt;

  @Column(name = COL_UPDATED_AT, nullable = false)
  protected Instant updatedAt;

  public Updatable() {
    this(Instant.now());
  }

  public Updatable(Instant createdAt) {
    notNull(createdAt, ATTR_CREATED_AT);

    this.createdAt = createdAt;
    this.updatedAt = createdAt;
  }

  /**
   * 게으른 엔티티 생성시각 설정 등의 처리.
   */
  @PrePersist
  protected void prePersist() {
  }

  /**
   * 게으른 엔티티 갱신시각 설정 등의 처리.
   */
  @PreUpdate
  protected void preUpdate() {
  }

  public Instant getCreatedAt() {
    return this.createdAt;
  }

  public Instant getUpdatedAt() {
    return this.updatedAt;
  }

  @Override
  public String toString() {
    return format("%s=%s, %s=%s", ATTR_CREATED_AT, this.createdAt, ATTR_UPDATED_AT, this.updatedAt);
  }
}

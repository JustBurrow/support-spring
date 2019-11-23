package kr.lul.support.spring.data.jpa.entiy;

import kr.lul.common.data.Creatable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
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
public abstract class CreatableEntity implements Creatable<Instant> {
  public static final String ATTR_CREATED_AT = "createdAt";
  public static final String COL_CREATED_AT = "created_at";

  @Column(name = COL_CREATED_AT, nullable = false, updatable = false)
  protected Instant createdAt;

  public CreatableEntity() {
  }

  public CreatableEntity(Instant createdAt) {
    notNull(createdAt, ATTR_CREATED_AT);

    this.createdAt = createdAt;
  }

  @Override
  public Instant getCreatedAt() {
    return this.createdAt;
  }

  @Override
  public String toString() {
    return format("createdAt=%s", this.createdAt);
  }
}

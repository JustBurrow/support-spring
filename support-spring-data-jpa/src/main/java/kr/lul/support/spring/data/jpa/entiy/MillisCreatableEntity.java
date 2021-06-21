package kr.lul.support.spring.data.jpa.entiy;

import kr.lul.common.data.Creatable;
import kr.lul.common.util.ValidationException;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.TemporalUtils.LEAVE_MILLISECONDS;

/**
 * 생성 가능한 엔티티의 기본 코드.
 *
 * @author justburrow
 * @since 2019/11/09
 */
@MappedSuperclass
public abstract class MillisCreatableEntity implements Creatable<Instant> {
  public static final String ATTR_CREATED_AT = "createdAt";
  public static final String COL_CREATED_AT = "created_at";

  @Column(name = COL_CREATED_AT, nullable = false, updatable = false)
  protected Instant createdAt;

  public MillisCreatableEntity() {
  }

  public MillisCreatableEntity(Instant createdAt) {
    if (null == createdAt)
      //noinspection ConstantConditions
      throw new ValidationException(ATTR_CREATED_AT, createdAt, "createdAt is null.");

    this.createdAt = LEAVE_MILLISECONDS.adjust(createdAt);
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

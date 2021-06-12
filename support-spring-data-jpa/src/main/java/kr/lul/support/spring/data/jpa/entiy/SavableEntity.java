package kr.lul.support.spring.data.jpa.entiy;

import kr.lul.common.data.Savable;
import kr.lul.common.util.ValidationException;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

import static java.lang.String.format;

/**
 * 생성과 갱신이 가능한 엔티티의 기본 코드.
 *
 * @author justburrow
 * @since 2019/11/09
 */
@MappedSuperclass
public abstract class SavableEntity implements Savable<Instant> {
  public static final String ATTR_CREATED_AT = "createdAt";
  public static final String ATTR_UPDATED_AT = "updatedAt";

  public static final String COL_CREATED_AT = "created_at";
  public static final String COL_UPDATED_AT = "updated_at";

  @Column(name = COL_CREATED_AT, nullable = false, updatable = false)
  protected Instant createdAt;

  @Column(name = COL_UPDATED_AT, nullable = false)
  protected Instant updatedAt;

  public SavableEntity() {
  }

  public SavableEntity(Instant createdAt) {
    if (null == createdAt)
      throw new ValidationException(ATTR_CREATED_AT, null, "createdAt is null.");

    this.createdAt = this.updatedAt = createdAt;
  }

  @Override
  public Instant getCreatedAt() {
    return this.createdAt;
  }

  @Override
  public Instant getUpdatedAt() {
    return this.updatedAt;
  }

  @Override
  public String toString() {
    return format("createdAt=%s, updatedAt=%s", this.createdAt, this.updatedAt);
  }
}

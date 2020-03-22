package kr.lul.support.spring.data.jpa.entiy;

import kr.lul.common.data.Updatable;
import kr.lul.common.util.ValidationException;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

import static java.lang.String.format;

/**
 * @author justburrow
 * @since 2019/11/24
 * @deprecated 이거 쓰나?
 */
@MappedSuperclass
@Deprecated
public abstract class UpdatableEntity implements Updatable<Instant> {
  public static final String ATTR_UPDATED_AT = "updatedAt";
  public static final String COL_UPDATED_AT = "updated_at";

  @Column(name = COL_UPDATED_AT, nullable = false)
  protected Instant updatedAt;

  public UpdatableEntity() {
  }

  public UpdatableEntity(Instant updatedAt) {
    if (null == updatedAt)
      throw new ValidationException(ATTR_UPDATED_AT, null, "updatedAt is null.");

    this.updatedAt = updatedAt;
  }

  @Override
  public Instant getUpdatedAt() {
    return this.updatedAt;
  }

  @Override
  public String toString() {
    return format("updatedAt=%s", this.updatedAt);
  }
}

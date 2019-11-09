package kr.lul.support.spring.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

import static kr.lul.common.util.Arguments.positive;

/**
 * 일련번호를 추가.
 *
 * @author justburrow
 * @since 2019/11/09
 */
public class AccountDetails extends User {
  private final long id;

  public AccountDetails(long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);

    positive(id, "id");
    this.id = id;
  }

  public AccountDetails(long id, String username, String password, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

    positive(id, "id");
    this.id = id;
  }

  public long getId() {
    return this.id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AccountDetails)) return false;

    return this.id == ((AccountDetails) o).id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  @Override
  public String toString() {
    return new StringBuilder(AccountDetails.class.getSimpleName())
        .append("{id=").append(this.id)
        .append(", password=[ PROTECTED ], username=").append(getUsername())
        .append(", authorities=").append(getAuthorities())
        .append(", accountNonExpired=").append(isAccountNonExpired())
        .append(", accountNonLocked=").append(isAccountNonLocked())
        .append(", credentialsNonExpired=").append(isCredentialsNonExpired())
        .append(", enabled=").append(isEnabled())
        .append('}').toString();
  }
}

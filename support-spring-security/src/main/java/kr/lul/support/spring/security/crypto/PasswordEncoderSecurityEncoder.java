package kr.lul.support.spring.security.crypto;

import org.springframework.security.crypto.password.PasswordEncoder;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019/11/09
 */
public class PasswordEncoderSecurityEncoder implements SecurityEncoder {
  private PasswordEncoder encoder;

  public PasswordEncoderSecurityEncoder(PasswordEncoder encoder) {
    notNull(encoder, "encoder");
    this.encoder = encoder;
  }

  @Override
  public String encode(CharSequence raw) {
    return this.encoder.encode(raw);
  }

  @Override
  public boolean matches(CharSequence raw, String encoded) {
    return this.encoder.matches(raw, encoded);
  }

  @Override
  public String toString() {
    return new StringBuilder(PasswordEncoderSecurityEncoder.class.getSimpleName())
        .append("{encoder=").append(this.encoder)
        .append('}')
        .toString();
  }
}

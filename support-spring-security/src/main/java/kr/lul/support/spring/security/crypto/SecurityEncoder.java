package kr.lul.support.spring.security.crypto;

/**
 * @author justburrow
 * @since 2019/11/09
 */
public interface SecurityEncoder {
  String encode(CharSequence raw);

  boolean matches(CharSequence raw, String encoded);
}

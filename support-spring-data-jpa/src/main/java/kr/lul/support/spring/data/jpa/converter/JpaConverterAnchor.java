package kr.lul.support.spring.data.jpa.converter;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2019/11/09
 */
public class JpaConverterAnchor extends Anchor {
  public static final Package PACKAGE = JpaConverterAnchor.class.getPackage();
  public static final String PACKAGE_NAME = PACKAGE.getName();
}

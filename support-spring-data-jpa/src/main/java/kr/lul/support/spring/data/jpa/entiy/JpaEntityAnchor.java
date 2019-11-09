package kr.lul.support.spring.data.jpa.entiy;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2019/11/09
 */
public class JpaEntityAnchor extends Anchor {
  public static final Package PACKAGE = JpaEntityAnchor.class.getPackage();
  public static final String PACKAGE_NAME = PACKAGE.getName();
}

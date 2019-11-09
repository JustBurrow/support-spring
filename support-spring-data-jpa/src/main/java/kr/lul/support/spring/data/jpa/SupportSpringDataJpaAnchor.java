package kr.lul.support.spring.data.jpa;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2019/11/06
 */
public abstract class SupportSpringDataJpaAnchor extends Anchor {
  public static final Package PACKAGE = SupportSpringDataJpaAnchor.class.getPackage();
  public static final String PACKAGE_NAME = PACKAGE.getName();
}

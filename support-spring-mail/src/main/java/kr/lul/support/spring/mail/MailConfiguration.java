package kr.lul.support.spring.mail;

import static kr.lul.common.util.Arguments.notNull;

/**
 * 검증이 끝난 메일 설정.
 * {@link MailParams}를 만들 때 사용한다.
 *
 * @author justburrow
 * @since 2019/12/23
 */
public class MailConfiguration {
  /**
   * 발신자 email 주소. 'dev@lul.kr' 혹은 'Kobalttown Dev<dev@lul.kr>' 형식.
   */
  private String from;
  /**
   * 제목
   */
  private String title;
  /**
   * 메일 본문 템플릿 이름.
   */
  private String template;

  public MailConfiguration(MailProperties properties) {
    notNull(properties, "properties");

    validate(properties);
    this.from = properties.getFrom();
    this.title = properties.getTitle();
    this.template = properties.getTemplate();
  }

  protected void validate(MailProperties properties) {
  }

  public String getFrom() {
    return this.from;
  }

  public String getTitle() {
    return this.title;
  }

  public String getTemplate() {
    return this.template;
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("{from='").append(this.from).append('\'')
        .append(", title='").append(this.title).append('\'')
        .append(", template='").append(this.template).append('\'')
        .append('}').toString();
  }
}

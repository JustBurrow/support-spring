package kr.lul.support.spring.mail;

import javax.validation.constraints.NotEmpty;

/**
 * 메일 설정 항목.
 * 단순히 설정을 읽어들이는 용도로만 사용하기 때문에 별도의 검증 로직은 없다.
 *
 * {@link MailConfiguration} : 검증이 끝난 메일 설정.
 *
 * @author justburrow
 * @since 2019/12/23
 */
public class MailProperties {
  /**
   * 발신자 email 주소. 'dev@lul.kr' 혹은 'Kobalttown Dev<dev@lul.kr>' 형식.
   */
  @NotEmpty
  private String from;
  /**
   * 제목
   */
  @NotEmpty
  private String title;
  /**
   * 메일 본문 템플릿 이름.
   */
  @NotEmpty
  private String template;

  public MailProperties() {
  }

  public MailProperties(String from, String title, String template) {
    this.from = from;
    this.title = title;
    this.template = template;
  }

  public String getFrom() {
    return this.from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTemplate() {
    return this.template;
  }

  public void setTemplate(String template) {
    this.template = template;
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

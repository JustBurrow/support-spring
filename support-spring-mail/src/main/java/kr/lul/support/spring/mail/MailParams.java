package kr.lul.support.spring.mail;

import kr.lul.common.data.Context;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019/12/19
 */
public class MailParams {
  private Context context;
  private String from;
  private String to;
  private String title;
  private String template;
  private boolean html;
  private Map<String, Object> attributes;

  public MailParams() {
  }

  public MailParams(Context context, String from, String to, String title, String template, boolean html,
      Map<String, Object> attributes) {
    notNull(context, "context");
    notEmpty(from, "from");
    notEmpty(to, "to");
    notNull(title, "title");
    notEmpty(template, "template");
    notNull(attributes, "attributes");

    this.context = context;
    this.from = from;
    this.to = to;
    this.title = title;
    this.template = template;
    this.html = html;
    this.attributes = new LinkedHashMap<>(attributes);
  }

  public Context getContext() {
    return this.context;
  }

  public String getFrom() {
    return this.from;
  }

  public String getTo() {
    return this.to;
  }

  public String getTitle() {
    return this.title;
  }

  public String getTemplate() {
    return this.template;
  }

  public boolean isHtml() {
    return this.html;
  }

  public Map<String, Object> getAttributes() {
    return unmodifiableMap(this.attributes);
  }

  public Object putParam(String key, Object value) {
    notNull(key, "key");
    return this.attributes.put(key, value);
  }

  public Object getAttribute(String key) {
    notNull(key, "key");
    return this.attributes.get(key);
  }

  public Object removeAttribute(String key) {
    notNull(key, "key");
    return this.attributes.remove(key);
  }

  @Override
  public String toString() {
    return new StringBuilder(MailParams.class.getSimpleName())
        .append("{context=").append(this.context)
        .append(", from='").append(this.from).append('\'')
        .append(", to='").append(this.to).append('\'')
        .append(", title='").append(this.title).append('\'')
        .append(", template='").append(this.template).append('\'')
        .append(", html=").append(this.html)
        .append(", attributes=").append(this.attributes)
        .append('}').toString();
  }
}

package kr.lul.support.spring.web.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Collections.unmodifiableList;
import static kr.lul.common.util.Arguments.notNegative;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2021/05/05
 */
public class ResourceEntry {
  private List<String> patterns;
  private List<String> locations;
  private int cache;

  public ResourceEntry() {
  }

  public ResourceEntry(List<String> patterns, List<String> locations, Duration cache) {
    this(patterns, locations, (int) cache.getSeconds());
  }

  public ResourceEntry(List<String> patterns, List<String> locations, int cache) {
    setPatterns(patterns);
    setLocations(locations);
    setCache(cache);
  }

  public List<String> getPatterns() {
    return this.patterns;
  }

  public String[] patterns() {
    return this.patterns.toArray(new String[0]);
  }

  public void setPatterns(List<String> patterns) {
    this.patterns = (null == patterns)
                        ? List.of()
                        : unmodifiableList(new ArrayList<>(patterns));
  }

  public List<String> getLocations() {
    return this.locations;
  }

  public String[] locations() {
    return this.locations.toArray(new String[0]);
  }

  public void setLocations(List<String> locations) {
    this.locations = (null == locations)
                         ? List.of()
                         : unmodifiableList(new ArrayList<>(locations));
  }

  public int getCache() {
    return this.cache;
  }

  public void setCache(Duration cache) {
    setCache((int) notNull(cache, "cache").getSeconds());
  }

  public void setCache(int cache) {
    this.cache = notNegative(cache, "cache");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ResourceEntry that = (ResourceEntry) o;
    return this.cache == that.cache &&
               Objects.equals(this.patterns, that.patterns) &&
               Objects.equals(this.locations, that.locations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.patterns, this.locations, this.cache);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ResourceEntry.class.getSimpleName() + "[", "]")
               .add("patterns=" + this.patterns)
               .add("locations=" + this.locations)
               .add("cache=" + this.cache)
               .toString();
  }
}
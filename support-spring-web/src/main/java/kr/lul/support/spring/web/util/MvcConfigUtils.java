package kr.lul.support.spring.web.util;

import org.slf4j.Logger;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.List;

import static kr.lul.common.util.Arguments.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2021/05/05
 */
public abstract class MvcConfigUtils {
  private static final Logger LOGGER = getLogger(MvcConfigUtils.class);

  public static void configure(ResourceHandlerRegistry registry, List<ResourceEntry> resources) {
    LOGGER.info("#configure args : registry={}, resources={}", registry, resources);
    notNull(registry, "registry");

    for (ResourceEntry entry : notNull(resources)) {
      registry.addResourceHandler(notEmpty(entry.patterns(), "entry.patterns"))
          .addResourceLocations(notEmpty(entry.locations(), "entry.locations"))
          .setCachePeriod(notNegative(entry.getCache(), "entry.cache"));
    }
  }

  public static void configure(ResourceHandlerRegistry registry, ResourceEntry... resources) {
    configure(registry, List.of(resources));
  }

  public MvcConfigUtils() {
    throw new UnsupportedOperationException();
  }
}
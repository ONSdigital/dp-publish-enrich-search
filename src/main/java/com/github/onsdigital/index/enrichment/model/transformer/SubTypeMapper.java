package com.github.onsdigital.index.enrichment.model.transformer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Reflective Mapper that determines which classes should be instantiated based on the parent type.
 * <br/>
 * <I>Beware that the classes being subtypes must be in a child directory of <code>com.github.onsdigital</code></I>
 */
public class SubTypeMapper<T> {
  /**
   * The root package for the scanner
   */
  private static final String ROOT_PACKAGE = "com.github.onsdigital";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private final Class<T> parentType;

  public SubTypeMapper(Class<T> parentType) {
    this.parentType = parentType;
    Reflections reflections = new Reflections(ROOT_PACKAGE);

    Set<Class<? extends T>> requestClasses = reflections.getSubTypesOf(parentType)
                                                        .stream()
                                                        .filter(t -> {
                                                          int modifiers = t.getModifiers();
                                                          return !Modifier.isAbstract(modifiers)
                                                              && !Modifier.isInterface(modifiers);
                                                        })
                                                        .collect(Collectors.toSet());

    MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    assignSubtypes(requestClasses);

  }

  void assignSubtypes(final Set<Class<? extends T>> requestClasses) {
    MAPPER.registerSubtypes(requestClasses.toArray(new Class[requestClasses.size()]));
  }

  public T readValue(final String source) throws IOException {
    return MAPPER.readValue(source, parentType);

  }
}

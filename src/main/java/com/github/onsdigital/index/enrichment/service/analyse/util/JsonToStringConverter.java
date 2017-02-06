package com.github.onsdigital.index.enrichment.service.analyse.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.github.onsdigital.index.enrichment.service.analyse.KeyValueAccumulator;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Tika does not handle Json file so extract separately
 */
public class JsonToStringConverter {
  private static final String TAB = "\t";
  private static final ObjectMapper MAP = new ObjectMapper();
  private final String json;
  private final List<String> jsonContents;

  public JsonToStringConverter(final String json) {
    this.json = json;

    jsonContents = new ArrayList<>();
  }

  public List<String> extractText() throws IOException {
    StringBuffer buffer = new StringBuffer();
    addKeys("",
            MAP.readTree(json),
            (k, v) -> {
              if (StringUtils.isNotBlank(v)) {
                buffer.append(v)
                      .append(TAB);
              }
            });

    return Lists.newArrayList(buffer.toString());
  }

  /**
   * Recursively search around the Node Tree and pull out the Key and Value of the leaf nodes
   *
   * @param currentPath
   * @param jsonNode
   * @param acc         Give the caller the ability to define how to accumulate the key and values
   */

  private void addKeys(final String currentPath, final JsonNode jsonNode, final KeyValueAccumulator acc) {

    if (jsonNode.isObject()) {
      String pathPrefix = currentPath.isEmpty() ? "" : currentPath + ".";
      ((ObjectNode) jsonNode).fields()
                             .forEachRemaining(e -> addKeys(pathPrefix + e.getKey(), e.getValue(), acc));
    }
    else if (jsonNode.isArray()) {
      final AtomicLong i = new AtomicLong();
      ((ArrayNode) jsonNode).forEach(node -> addKeys(currentPath + "[" + i.incrementAndGet() + "]", node, acc));

    }
    else if (jsonNode.isValueNode()) {
      acc.put(currentPath, ((ValueNode) jsonNode).asText());
    }
  }
}

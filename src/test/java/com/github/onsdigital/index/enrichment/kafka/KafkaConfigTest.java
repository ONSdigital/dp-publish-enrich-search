package com.github.onsdigital.index.enrichment.kafka;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by fawks on 07/02/2017.
 */
public class KafkaConfigTest {
  @Test
  public void getTopic() throws Exception {
    KafkaConfig cfg = new KafkaConfig();
    assertEquals("testTopic",
                 cfg.setTopic("testTopic")
                    .getTopic());
  }

  @Test
  public void getMessageKey() throws Exception {
    KafkaConfig cfg = new KafkaConfig();
    assertEquals("testMsgKey",
                 cfg.setMessageKey("testMsgKey")
                    .getMessageKey());
  }

  @Test
  public void getAutoStartTrue() throws Exception {
    KafkaConfig cfg = new KafkaConfig();
    assertEquals(true,
                 cfg.setAutoStart(true)
                    .getAutoStart());
  }

  @Test
  public void getAutoStartFalse() throws Exception {
    KafkaConfig cfg = new KafkaConfig();
    assertEquals(false,
                 cfg.setAutoStart(false)
                    .getAutoStart());
  }

  @Test
  public void getAutoStartNull() throws Exception {
    KafkaConfig cfg = new KafkaConfig();
    assertNull(cfg.setAutoStart(null)
                  .getAutoStart());
  }

  @Test
  public void getAutoStartDefaultNull() throws Exception {
    KafkaConfig cfg = new KafkaConfig();
    assertNull(cfg.getAutoStart());
  }

  @Test
  public void getZookeeper() throws Exception {
    KafkaConfig cfg = new KafkaConfig();
    KafkaConfig.ZookeeperConfig zookeeper = new KafkaConfig.ZookeeperConfig();
    assertEquals(zookeeper,
                 cfg.setZookeeper(zookeeper)
                    .getZookeeper());
  }

}
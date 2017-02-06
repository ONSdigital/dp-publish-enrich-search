package com.github.onsdigital.index.enrichment.model.transformer;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import org.apache.cxf.common.util.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by fawks on 06/02/2017.
 */
public class SubTypeMapperTest {
  @Test
  public void testSubTypeDiscovery() throws Exception {
    new SubTypeMapper(TestParent.class) {
      @Override
      void assignSubtypes(final Set requestClasses) {
        assertTrue(requestClasses.contains(TestConcrete.class));
        assertFalse(requestClasses.contains(TestAbstract.class));
        assertFalse(requestClasses.contains(TestSubInterface.class));
      }
    };
  }

  @Test
  public void testMarshallsTestConcrete() throws Exception {
    String testValue1 = "testValue1";
    String testValue2 = "testValue2";
    TestParent expected = new TestConcrete().setArg2(testValue2)
                                            .setArg1(testValue1);
    byte[] expectedBytes = new ObjectMapper().writeValueAsBytes(expected);

    SubTypeMapper subTypeMapper = new SubTypeMapper(TestParent.class);

    Object actual = subTypeMapper.readValue(expectedBytes);

    assertEquals(TestConcrete.class, actual.getClass());
    TestConcrete actualConcrete = (TestConcrete) actual;
    assertEquals(testValue1, actualConcrete.getArg1());
    assertEquals(testValue2, actualConcrete.getArg2());

  }

  @Test(expected = InvalidTypeIdException.class)
  public void testImpossibleParsing() throws IOException {
    byte[] bytes = StringUtils.toBytes("{\"TestAbstract\":{\"arg1\":\"testValue1\"}}", "UTF-8");

    SubTypeMapper subTypeMapper = new SubTypeMapper(TestParent.class);
    Object actual = subTypeMapper.readValue(bytes);
    assertNull(actual);

  }

  @Test(expected = InvalidTypeIdException.class)
  public void testIncorrectJsonObject() throws IOException {
    byte[] bytes = StringUtils.toBytes("{\"blah\":{\"arg1\":\"testValue1\"}}", "UTF-8");

    SubTypeMapper subTypeMapper = new SubTypeMapper(TestParent.class);
    Object actual = subTypeMapper.readValue(bytes);
    assertNull(actual);

  }

  @Test(expected = JsonMappingException.class)
  public void testEmptyJson() throws IOException {
    byte[] bytes = new byte[]{};

    SubTypeMapper subTypeMapper = new SubTypeMapper(TestParent.class);
    Object actual = subTypeMapper.readValue(bytes);
    assertNull(actual);

  }


  @Test(expected = NullPointerException.class)
  public void testNullJson() throws IOException {
    byte[] bytes = null;

    SubTypeMapper subTypeMapper = new SubTypeMapper(TestParent.class);
    Object actual = subTypeMapper.readValue(bytes);
    assertNull(actual);

  }

  @Test
  public void testIgnoreUnknownValuesRequest() throws IOException {
    byte[] bytes = StringUtils.toBytes("{\"TestConcrete\":{\"arg1\":\"testValue1\",\"arg3\":\"discard\"}}", "UTF-8");

    TestConcrete a = (TestConcrete) new SubTypeMapper(TestParent.class).readValue(bytes);
    assertEquals("testValue1", a.getArg1());
  }

}
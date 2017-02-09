package com.github.onsdigital.index.enrichment.model.transformer;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
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

  public static final ObjectMapper MPR = new ObjectMapper();

  @Test
  public void testSubTypeDiscovery() throws Exception {
    new SubTypeMapper<TestParent>(TestParent.class) {
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

    SubTypeMapper<TestParent> subTypeMapper = new SubTypeMapper<>(TestParent.class);

    Object actual = subTypeMapper.readValue(MPR.writeValueAsString(expected));

    assertEquals(TestConcrete.class, actual.getClass());
    TestConcrete actualConcrete = (TestConcrete) actual;
    assertEquals(testValue1, actualConcrete.getArg1());
    assertEquals(testValue2, actualConcrete.getArg2());

  }

  @Test(expected = InvalidTypeIdException.class)
  public void testImpossibleParsing() throws IOException {
    String str = "{\"TestAbstract\":{\"arg1\":\"testValue1\"}}";
    SubTypeMapper<TestParent> subTypeMapper = new SubTypeMapper<>(TestParent.class);
    Object actual = subTypeMapper.readValue(str);
    assertNull(actual);

  }

  @Test(expected = InvalidTypeIdException.class)
  public void testIncorrectJsonObject() throws IOException {
    String str = "{\"blah\":{\"arg1\":\"testValue1\"}}";

    SubTypeMapper<TestParent> subTypeMapper = new SubTypeMapper<>(TestParent.class);
    Object actual = subTypeMapper.readValue(str);
    assertNull(actual);

  }

  @Test(expected = JsonMappingException.class)
  public void testEmptyJson() throws IOException {


    SubTypeMapper<TestParent> subTypeMapper = new SubTypeMapper<>(TestParent.class);
    Object actual = subTypeMapper.readValue("");
    assertNull(actual);

  }


  @Test(expected = NullPointerException.class)
  public void testNullJson() throws IOException {

    SubTypeMapper<TestParent> subTypeMapper = new SubTypeMapper<>(TestParent.class);
    Object actual = subTypeMapper.readValue(null);
    assertNull(actual);

  }

  @Test
  public void testIgnoreUnknownValuesRequest() throws IOException {
    String str = "{\"TestConcrete\":{\"arg1\":\"testValue1\",\"arg3\":\"discard\"}}";
    TestConcrete a = (TestConcrete) new SubTypeMapper<TestParent>(TestParent.class).readValue(str);
    assertEquals("testValue1", a.getArg1());
  }

}
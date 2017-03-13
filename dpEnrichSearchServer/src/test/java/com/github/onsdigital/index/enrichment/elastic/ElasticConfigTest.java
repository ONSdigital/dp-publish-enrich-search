package com.github.onsdigital.index.enrichment.elastic;

import com.beust.jcommander.internal.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by fawks on 07/02/2017.
 */
public class ElasticConfigTest {


    @Test
    public void getHttp() throws Exception {
        ElasticConfig config = new ElasticConfig();
        String testHostName = "testHost";
        Integer testHostPort = -1000;
        List<HttpAddressConfig> testHost = Lists.newArrayList(new HttpAddressConfig().setPort(testHostPort)
                                                                                     .setHost(testHostName));
        ElasticConfig elasticConfig = config.setHttpAddresses(testHost);
        assertEquals(testHostPort,
                     elasticConfig.getHttpAddresses()
                                  .get(0)
                                  .getPort());
        assertEquals(testHostName,
                     elasticConfig.getHttpAddresses()
                                  .get(0)
                                  .getHost());
    }


    @Test
    public void testHashCode() {

        ElasticConfig lhs = buildConfig("testHostName", -11111);
        ElasticConfig rhs = buildConfig("testHostName", -11111);
        assertEquals(lhs.hashCode(), rhs.hashCode());
    }

    @Test
    public void testEquals() {

        ElasticConfig lhs = buildConfig("testHostName", -11111);
        ElasticConfig rhs = buildConfig("testHostName", -11111);
        assertEquals(lhs, rhs);
    }

    @Test
    public void testNotEqualsUneqHostName() {

        ElasticConfig lhs = buildConfig("testHostName1", -11111);
        ElasticConfig rhs = buildConfig("testHostName2", -11111);
        assertNotEquals(lhs, rhs);
    }

    private ElasticConfig buildConfig(final String testHostName, final Integer testHostPort) {


        return ConfigUtils.buildConfig(testHostName, testHostPort);
    }

}
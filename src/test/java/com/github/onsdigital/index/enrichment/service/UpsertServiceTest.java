package com.github.onsdigital.index.enrichment.service;

import com.github.onsdigital.index.enrichment.elastic.ElasticRepository;
import com.github.onsdigital.index.enrichment.model.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpsertServiceTest {

    @Mock
    ElasticRepository repo;

    @Test
    public void upsert() throws Exception {
        Page page = new Page();
        String testId = "testId";
        String testIndex = "testIndex";
        String testType = "testType";
        Map<String, Object> testSource = new HashMap();
        Long testVersion = 10L;

        page.setId(testId)
            .setIndex(testIndex)
            .setType(testType)
            .setSource(testSource)
            .setVersion(testVersion);


        when(repo.upsertData(eq(testId), eq(testIndex), eq(testType), eq(testSource), eq(testVersion)))
                .thenReturn(true);

        new UpsertService().setRepo(repo)
                           .upsert(page);
        Mockito.verify(repo)
               .upsertData(testId, testIndex, testType, testSource, testVersion);
    }
}
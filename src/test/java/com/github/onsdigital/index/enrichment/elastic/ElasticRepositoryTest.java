package com.github.onsdigital.index.enrichment.elastic;

import com.github.onsdigital.index.enrichment.model.Page;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.SearchShardTarget;
import org.elasticsearch.search.internal.InternalSearchHit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElasticRepositoryTest {

  @Mock
  private TransportClient client;


  @Mock
  private GetResponse getResponse;
  @Mock
  private UpdateRequestBuilder updateRequestBuilder;

  private ElasticRepository repo = new ElasticRepository();

  @Before
  public void init() {
    repo.setElasticClient(client);
  }


  @Test
  public void loadData() throws Exception {
    final String type = "testType";
    final String index = "testIndex";
    final String id = "testId";


    final ActionFuture<GetResponse> getActionFuture = mock(ActionFuture.class);

    when(client.get(any(GetRequest.class))).thenReturn(getActionFuture);
    when(getActionFuture.actionGet()).thenReturn(getResponse);
    when(getResponse.getId()).thenReturn(id);
    when(getResponse.getIndex()).thenReturn(index);
    when(getResponse.getType()).thenReturn(type);

    final Page actual = repo.loadData(id, index, type);
    //Test Response is populated
    assertEquals(id, actual.getId());
    assertEquals(type, actual.getType());
    assertEquals(index, actual.getIndex());


    final ArgumentCaptor<GetRequest> arg = ArgumentCaptor.forClass(GetRequest.class);
    verify(client).get(arg.capture());
    //Test the original request is correctly populated
    final GetRequest actualRequest = arg.getValue();
    assertEquals(id, actualRequest.id());
    assertEquals(type, actualRequest.type());
    assertEquals(index, actualRequest.index());

  }

  @Test
  public void upsertData() throws Exception {
    final String type = "testType";
    final String index = "testIndex";
    final String id = "testId";
    final Map<String, Object> source = new HashMap<String, Object>();
    source.put("title", "testTitle");

    when(client.prepareUpdate(eq(index), eq(type), eq(id))).thenReturn(updateRequestBuilder);
    when(updateRequestBuilder.setDoc(eq(source))).thenAnswer(Answers.RETURNS_SELF);
    when(updateRequestBuilder.setUpsert(eq(source))).thenAnswer(Answers.RETURNS_SELF);

    ListenableActionFuture actionRequest = mock(ListenableActionFuture.class);
    when(updateRequestBuilder.execute()).thenReturn(actionRequest);
    when(actionRequest.actionGet()).thenReturn(mock(UpdateResponse.class));

    repo.upsertData(id, index, type, source, 0L);

    verify(client).prepareUpdate(eq(index), eq(type), eq(id));
    ArgumentCaptor<Map> doc = ArgumentCaptor.forClass(Map.class);

    verify(updateRequestBuilder).setDoc(eq(source));
    verify(updateRequestBuilder).setUpsert(eq(source));

  }

  @Test
  public void listAllIndexDocuments() throws Exception {
    final String index = "testIndex";

    prepInitialQuery(index);

    //Now deal with the Scroll section

    prepScrollQuery(index);

    List<Page> pages = repo.listAllIndexDocuments(index);
    assertEquals(2050, pages.size());

  }

  /**
   * Set up for 3 iterations of the scroll loop total 2050 hits
   *
   * @param index
   */
  private void prepScrollQuery(final String index) {

    final SearchScrollRequestBuilder builder = mock(SearchScrollRequestBuilder.class);

    when(builder.setScroll(any(TimeValue.class))).thenAnswer(Answers.RETURNS_SELF);

    when(client.prepareSearchScroll(anyString())).thenReturn(builder, builder, builder);


    ListenableActionFuture futureResponse = mock(ListenableActionFuture.class);
    when(builder.execute()).thenReturn(futureResponse, futureResponse, futureResponse);


    SearchResponse response = mock(SearchResponse.class);
    when(response.getScrollId()).thenReturn("1")
                                .thenReturn("1");
    when(futureResponse.actionGet()).thenReturn(response, response, response);

    SearchHits searchHits = mock(SearchHits.class);

    when(response.getHits()).thenReturn(searchHits);
    when(searchHits.getHits()).thenReturn(getBuildSearchHits(1000, index))
                              .thenReturn(getBuildSearchHits(50, index))
                              .thenReturn(getBuildSearchHits(0, index));


  }

  /**
   * there are two parts to the query, the initial normal query and then the subsequent scrolling
   *
   * @param index
   */
  private void prepInitialQuery(final String index) {
    final SearchRequestBuilder builder = mock(SearchRequestBuilder.class);
    when(client.prepareSearch()).thenReturn(builder);
    when(builder.setIndices(any())).thenAnswer(Answers.RETURNS_SELF);
    when(builder.setScroll(eq(TimeValue.timeValueHours(1)))).thenAnswer(Answers.RETURNS_SELF);
    when(builder.setSize(eq(1000))).thenAnswer(Answers.RETURNS_SELF);


    ListenableActionFuture futureResponse = mock(ListenableActionFuture.class);
    when(builder.execute()).thenReturn(futureResponse);


    SearchResponse response = mock(SearchResponse.class);
    when(futureResponse.actionGet()).thenReturn(response);

    SearchHits searchHits = mock(SearchHits.class);
    when(response.getHits()).thenReturn(searchHits);
    when(searchHits.getHits()).thenReturn(getBuildSearchHits(1000, index));
    when(response.getScrollId()).thenReturn("1");

  }

  private SearchHit[] getBuildSearchHits(int arraySize, String index) {
    SearchHit[] hits = new SearchHit[arraySize];
    SearchShardTarget target = new SearchShardTarget("1", index, 1);

    for (int i = 0; i < arraySize; i++) {
      InternalSearchHit testData = new InternalSearchHit(i,
                                                         Integer.toString(i),
                                                         new Text("testData"),
                                                         new HashMap());
      testData.shardTarget(target);
      hits[i] = testData;
    }
    return hits;
  }

}
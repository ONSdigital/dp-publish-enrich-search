package com.github.onsdigital.index.enrichment.service;

import org.springframework.stereotype.Service;

/**
 *
 * The Integration flows wait for response of a gateway, if no response appears it just hangs.
 *  If you return something from the gateway then the calling flow requires a responseChannel, but we don want to respond to Kafka so we need
 *  to consume the response and end the flow here.<p/>
 *
 *  You can see this in the <code>ConsumerDefinition</code><p/>
 *
 * @see com.github.onsdigital.index.enrichment.ConsumerDefinition ConsumerDefinition
 * <p/>
 *...<p/>
 *  <code>
 *  return IntegrationFlows<br/>
 *  .from(messageConsumer)<br/>
 *  .handle(transformService)<br/>
 *  .handle(extractService)<br/>
 *  .gateway(processChannel(),<br/>
 *  e -> e.advice(retryAdvice()))//Call Gateway flow and then retry if any exception<br/>
 *  .handle(terminatingService)// Fix the integration flow issue to return to the queue and pick up another, otherwise it just waits<br/>
 *  .get();<br/>
 *  </code>
 **/
@Service
public class NullReturningTerminatingService {
    public void doNothingReturnNothing(Object object) {
        //DO NOTHING
    }
}

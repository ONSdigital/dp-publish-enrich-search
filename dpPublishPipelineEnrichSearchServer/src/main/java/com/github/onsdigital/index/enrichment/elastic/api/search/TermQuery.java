package com.github.onsdigital.index.enrichment.elastic.api.search;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guidof on 08/03/17.
 */

public class TermQuery implements Query {

    public Map<String, TermQueryOptions> getTerm() {
        return term;
    }

    Map<String, TermQueryOptions> term = new HashMap();

    public TermQuery(String term, TermQueryOptions termOptions) {
        this.term.put(term, termOptions);
    }
}

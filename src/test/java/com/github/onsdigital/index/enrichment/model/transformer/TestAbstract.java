package com.github.onsdigital.index.enrichment.model.transformer;

/**
 * Created by fawks on 06/02/2017.
 */
public abstract class TestAbstract implements TestParent {

  private String arg1;

  public String getArg1() {
    return arg1;
  }

  public TestAbstract setArg1(final String arg1) {
    this.arg1 = arg1;
    return this;
  }


}

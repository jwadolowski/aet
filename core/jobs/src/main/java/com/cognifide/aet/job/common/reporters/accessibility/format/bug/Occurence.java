package com.cognifide.aet.job.common.reporters.accessibility.format.bug;

import java.util.List;

public class Occurence {

  private final String url;

  private final List<Integer> lineNumbers;

  public Occurence(String url, List<Integer> lineNumbers) {
    this.url = url;
    this.lineNumbers = lineNumbers;
  }

  public String getUrl() {
    return url;
  }

  public List<Integer> getLineNumbers() {
    return lineNumbers;
  }
}

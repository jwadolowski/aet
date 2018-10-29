package com.cognifide.aet.job.common.reporters.accessibility.format.bug;

import java.util.List;

public class Case {

  private final String errorMessage;

  private final List<Occurence> occurences;

  public Case(String errorMessage,
      List<Occurence> occurences) {
    this.errorMessage = errorMessage;
    this.occurences = occurences;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public List<Occurence> getOccurences() {
    return occurences;
  }
}

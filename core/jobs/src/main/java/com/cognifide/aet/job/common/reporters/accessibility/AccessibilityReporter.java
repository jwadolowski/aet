/**
 * AET
 *
 * Copyright (C) 2013 Cognifide Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.cognifide.aet.job.common.reporters.accessibility;

import com.cognifide.aet.communication.api.metadata.Step;
import com.cognifide.aet.communication.api.metadata.Suite;
import com.cognifide.aet.job.common.collectors.accessibility.AccessibilityIssue;
import com.cognifide.aet.job.common.reporters.Bug;
import com.cognifide.aet.job.common.reporters.ReportIssue;
import com.cognifide.aet.job.common.reporters.accessibility.format.AccessibilityFormatter;
import com.cognifide.aet.job.common.reporters.dto.ReporterDto;
import com.cognifide.aet.job.common.reporters.factory.Reporter;
import com.cognifide.aet.vs.ArtifactsDAO;
import com.cognifide.aet.vs.DBKey;
import com.cognifide.aet.vs.MetadataDAO;
import com.cognifide.aet.vs.StorageException;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true)
public class AccessibilityReporter implements Reporter {

  private static final Type ACCESSIBILITY_ISSUE_LIST = new TypeToken<List<AccessibilityIssue>>() {
  }.getType();

  private static final String ACCESSIBILITY_STEP_NAME = "accessibility";

  @Reference
  private ArtifactsDAO artifactsDAO;

  @Reference
  private MetadataDAO metadataDAO;

  @Reference
  private ReporterDto reporterDto;

  @Override
  public List<ReportIssue> report(DBKey dbKey, String suiteCorrelationId) {
    Suite suite = getSuite(dbKey, suiteCorrelationId);
    List<AccessibilityIssue> issues = getAccessibilityIssuesForSuite(dbKey, suite);
    List<Bug> bugs = formatIssues(issues);

    return reporterDto.report(bugs);
  }

  private Suite getSuite(DBKey dbKey, String suiteCorrelationId) {
    try {
      return metadataDAO.getSuite(dbKey, suiteCorrelationId);
    } catch (StorageException e) {
      throw new IllegalStateException("Error getting suite from db!", e);
    }
  }

  private List<AccessibilityIssue> getAccessibilityIssuesForSuite(DBKey dbKey, Suite suite) {
    return suite.getTests().stream()
        .flatMap(test -> test.getUrls().stream())
        .flatMap(url -> url.getSteps().stream())
        .filter(step -> ACCESSIBILITY_STEP_NAME.equals(step.getName()))
        .map(Step::getPattern)
        .map(issueId -> getAccessibilityIssues(dbKey, issueId))
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  private List<AccessibilityIssue> getAccessibilityIssues(DBKey dbKey, String issueId) {
    try {
      return artifactsDAO.getJsonFormatArtifact(dbKey, issueId, ACCESSIBILITY_ISSUE_LIST);
    } catch (IOException e) {
      throw new IllegalStateException("Error while accessing database!", e);
    }
  }

  private List<Bug> formatIssues(List<AccessibilityIssue> issues) {
    return new AccessibilityFormatter(issues).format();
  }
}

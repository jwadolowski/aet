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

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.cognifide.aet.job.common.reporters.Bug;
import com.cognifide.aet.job.common.reporters.dto.ReporterDto;
import com.cognifide.aet.vs.DBKey;
import com.cognifide.aet.vs.MetadataDAO;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccessibilityReporterTest {

  private static final String SUITE_CORRELATION_ID = "foo";

  private static final int ISSUES_COUNT = 10;

  @Mock
  private DBKey dbKey;

  @Mock
  private MetadataDAO metadataDAO;

  @Mock
  private ReporterDto reporterDto;

  @InjectMocks
  private AccessibilityReporter reporter;

  @Captor
  private ArgumentCaptor<ArrayList<Bug>> captor;

  @Test
  public void accessibilityReporter_shouldReportAll_whenAllRequested() {

    reporter.report(dbKey, SUITE_CORRELATION_ID);

    verify(reporterDto).report(captor.capture());
    assertEquals(captor.getValue().size(), ISSUES_COUNT);
  }

  @Test
  public void accessibilityReporter_shouldReportOne_whenOneRequested() {

    reporter.report(dbKey, SUITE_CORRELATION_ID);

    verify(reporterDto).report(captor.capture());
    assertEquals(captor.getValue().size(), 1);
  }

  @Test(expected = IllegalStateException.class)
  public void accessibilityReporter_shouldThrow_whenNoneRequested() {

    reporter.report(dbKey, SUITE_CORRELATION_ID);

    verifyNoMoreInteractions(reporterDto);
  }
}
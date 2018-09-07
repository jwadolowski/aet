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
package com.cognifide.aet.runner.processing.data.RunIndexWrappers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.cognifide.aet.communication.api.metadata.Suite;
import com.cognifide.aet.communication.api.wrappers.Run;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RunIndexWrapperTest {

  private RunIndexWrapper runIndexWrapper;

  @Mock
  private Run objectToRunWrapper;

  @Mock
  private Suite suite;

  @Mock
  private com.cognifide.aet.communication.api.metadata.Test test;

  @Before
  public void setUp(){
    when(suite.getTest(any(String.class))).thenReturn(null);
    when(objectToRunWrapper.getRealSuite()).thenReturn(suite);
    when(test.getName()).thenReturn("testName");
    runIndexWrapper = new SuiteRunIndexWrapper(objectToRunWrapper);
  }

  @Test
  public void cleanUrlFromExecutionData_expectClearedData(){
    //TODO
  }

  @Test
  public void getTest_whenSuiteHasNotTest_expectNull() {
    //TODO
    //assertThat(runIndexWrapper.getTest("testName"), null);
  }
  @Test
  public void getTest_whenSuiteHasTest_expectTest() {
    com.cognifide.aet.communication.api.metadata.Test returnedTest;
    when(suite.getTest("testName")).thenReturn(test);
    returnedTest = runIndexWrapper.getTest("testName");
    assertThat(returnedTest, is(test));
  }

  @Test
  public void getTestUrl_whenSuiteHasNotTest_expectNull() {
    //TODO
  }

  @Test
  public void getTestUrl_whenTestHasNotUrl_expectNull() {
    //TODO
  }

  @Test
  public void getTestUrl_whenTestHasUrl_expectUrl() {
    //TODO
  }

}
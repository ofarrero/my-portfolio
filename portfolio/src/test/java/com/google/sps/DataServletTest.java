// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
 
package com.google.sps;
 
import com.google.sps.servlets.DataServlet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.Date;
import java.time.LocalDateTime;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.http.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/** */
@RunWith(JUnit4.class)
public final class DataServletTest{
   private final LocalServiceTestHelper helper =new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  @Before
  public void setUp() {
    helper.setUp();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  @Test
  public void testDoGet() throws IOException {
    //Build mock servlet request, response & date
    HttpServletRequest request = mock(HttpServletRequest.class);       
    HttpServletResponse response = mock(HttpServletResponse.class);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
 
    // Create a new comment entity for testing 
    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("username", "me");
    commentEntity.setProperty("email", "name@email.com");
    commentEntity.setProperty("comment", "content");
    commentEntity.setProperty("timestamp", 30L);
    
    datastore.put(commentEntity);

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);
    new DataServlet().doGet(request, response);
    String result = stringWriter.toString();
    Assert.assertTrue(result.contains("{\"username\":\"me\",\"email\":\"name@email.com\",\"comment\":\"content\""));
  }
  
  @Test
  public void testDoPost() throws Exception {
    //Build mock servlet request, response & date
    HttpServletRequest request = mock(HttpServletRequest.class);       
    HttpServletResponse response = mock(HttpServletResponse.class);  

    final Date mockDate = mock(Date.class);
     
    //set test values
    when(request.getParameter("username")).thenReturn("me");
    when(request.getParameter("email")).thenReturn("name@email.com");
    when(request.getParameter("comment")).thenReturn("content");
    when(mockDate.getTime()).thenReturn(30L);

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);
 
    new DataServlet().doPost(request, response);
 
    String result = stringWriter.toString();
    Assert.assertTrue( result.contains("{\"username\":\"me\",\"email\":\"name@email.com\",\"comment\":\"content\""));
  }  
}

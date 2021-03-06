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

package com.google.sps.servlets;

import java.io.IOException;
import com.google.sps.servlets.DataServlet;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Date ;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that stores and returns comments */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    

  //A class that holds comment information
  public class Comments{
      final String username;
      final String email;
      final String comment;
      final long timestamp;

      public Comments(String username, String email, String comment, long timestamp){
          this.username = username;
          this.email = email;
          this.comment = comment;
          this.timestamp = timestamp;
      }
  }

  /**
    * Gets all comment data stored and adds it to an array list to be 
    * returned as a servlet response
    */

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Comments> commentList= new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      //long id = entity.getKey().getId();
      String username = (String) entity.getProperty("username");
      String email = (String) entity.getProperty("email");
      String comment = (String) entity.getProperty("comment");
      long timestamp = (long) entity.getProperty("timestamp");

      Comments oldComment = new Comments(username, email, comment, timestamp);
      commentList.add(oldComment);
    }

    Gson gson = new Gson();
    response.setContentType("application/json;");
    String json = gson.toJson(commentList);
    response.getWriter().println(json);
  }

   /**
    * Gets new comment data and creates a new comment object from it to be
    * returned as a servlet response
    */

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String username = getParameter(request, "username", "");
    String email = getParameter(request, "email", "");
    String comment = getParameter(request, "comment", "");
    Date currDate = new Date();
    long timestamp = currDate.getTime();

    // Add entity
    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("username", username);
    commentEntity.setProperty("email", email);
    commentEntity.setProperty("comment", comment);
    commentEntity.setProperty("timestamp", timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    // Create new comment object
    Comments newComment = new Comments(username, email, comment, timestamp);
    String json = convertToJson(newComment);

    // Respond with the result.
    response.setContentType("application/json;");
    response.getWriter().println(json);
    response.sendRedirect("/comments.html");
  }

  /**
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  /**
   * Converts Comment object to json string
   * @param {!Comment} obj
   * @return {String} 
   */

   private String convertToJson(Comments comments) {
    Gson gson = new Gson();
    String json = gson.toJson(comments);
    return json;
  }

}


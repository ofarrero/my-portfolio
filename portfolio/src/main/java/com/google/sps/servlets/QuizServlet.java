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
import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that stores and returns comments */
@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
  private Map<String, Integer> scoresTable = new HashMap<>();

  /**
    * Gets all score data stored and adds it to an hash map to be 
    * returned as a servlet response
    */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Score");
    //ensure hashmap is clear for repopulation
    scoresTable.clear();
    try{
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      PreparedQuery results = datastore.prepare(query);

      //add score and its frequency to hash map
      for (Entity entity : results.asIterable()) {
      String score = (String) entity.getProperty("score");
      if (scoresTable.containsKey(score)) {
          scoresTable.put(score, scoresTable.get(score) + 1);
      } else {
          scoresTable.put(score, 1);
      }
    }
    }
    catch(DatastoreFailureException e){
      System.out.println(e.getCause());
    }
    

    //respond with map
    response.setContentType("application/json");
    Gson gson = new Gson();
    String json = gson.toJson(scoresTable);
    response.getWriter().println(json);
  }

  /**
    * Gets new score data and adds it to datastore to be used later
    * returns given score as a servlet response
    */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the quiz.js.
    String score = request.getParameter("score");

    // Add entity
    Entity scoreEntity = new Entity("Score");
    scoreEntity.setProperty("score", score);

    try{
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(scoreEntity);
    }
    catch(DatastoreFailureException e){
      System.out.println(e.getCause());
    }
    
    //respond with result
    Gson gson = new Gson();
    String json = gson.toJson(score);
    response.setContentType("application/json;");
    response.getWriter().println(json);
    response.sendRedirect("/quiz.html");
  }
}

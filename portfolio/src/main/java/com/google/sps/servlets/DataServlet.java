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
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private List<String> messages;

 @Override 
  public void init() {
    messages = new ArrayList<>();
    messages.add("I was taught the way of progress is neither swift nor easy. - Marie Curie");
    messages.add(
        "I'd like to hear less talk about men and women and more talk about citizens."
        + " - Marjory Stoneman Douglas");
    messages.add(
        "There is no joy more intense than that of coming upon a fact that cannot "
        + "be understood in terms of currently accepted ideas. - Cecilia Payne-Gaposchkin");

  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = convertToJson(messages);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }




   private String convertToJson(List messages) {
    Gson gson = new Gson();
    String json = gson.toJson(messages);
    return json;
  }

}

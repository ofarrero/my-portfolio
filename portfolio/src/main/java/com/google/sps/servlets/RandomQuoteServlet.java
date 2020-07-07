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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/random-quote")
public class RandomQuoteServlet extends HttpServlet {
  private List<String> quotes;

  @Override
  public void init() {
    quotes = new ArrayList<>();
    quotes.add("I was taught the way of progress is neither swift nor easy. - Marie Curie");
    quotes.add(
        "I'd like to hear less talk about men and women and more talk about citizens."
        + " - Marjory Stoneman Douglas");
    quotes.add(
        "There is no joy more intense than that of coming upon a fact that cannot "
        + "be understood in terms of currently accepted ideas. - Cecilia Payne-Gaposchkin");
    quotes.add("When you love science all you really want to do is keep working. - Maria Goeppert-Mayer");
    quotes.add("People are allergic to change you have to get out and sell the idea - Grace Hopper");
    quotes.add("Above all don't fear difficult moments. The best comes from them. - Rita Levi-Montalcini");
    quotes.add(
        "[My Father] made me understand that I must make my own decisions, mold my own character, "
            + "think my own thoughts.  - Hedy Lamar");
    quotes.add(
        "We must believe in ourselves or no one else will believe in us; We must match our aspirations "
            + "with the competence, courage and determination to succeed. - Rosalyn Yalow");
    quotes.add("Science and everyday life cannot and should not be seperated. - Rosalind Franklin");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String quote = quotes.get((int) (Math.random() * quotes.size()));

    response.setContentType("text/html;");
    response.getWriter().println(quote);
  }

}

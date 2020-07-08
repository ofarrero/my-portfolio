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

/**
 * Fetches comments from the servers and adds them to the DOM.
 */
function getComments() {
  fetch('/data').then(response => response.json()).then((comments) => {

    const commentListElement = document.getElementById('comment-container');
    commentListElement.innerHTML = '';
    // Create an array to store comment html
    const commentOutput = [];

    comments.forEach((/**item */commentInfo)=>{
      //Add formatting html for each comment to commentOutput array
      commentOutput.push(
        `<div class="comment">
          <h3 class="username"> ${commentInfo.username} says: </h3>
          <p class="commentContent"> ${commentInfo.comment} </p>
        </div>`
      );
    });
    commentListElement.innerHTML = commentOutput.join('');
  });
}

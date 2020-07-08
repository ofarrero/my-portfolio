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


let slideIndex = 1;
showSlides(slideIndex);

// Next/previous controls called in html onclick
function plusSlides(changeBy) {
  showSlides(slideIndex += changeBy);
}

function showSlides(currentSlide) {
  let slides = document.getElementsByClassName("myPhotos");
  let markers = document.getElementsByClassName("marker");

  //go to first picture at end of slide deck
  if (currentSlide > slides.length) {slideIndex = 1}

  //got to last picture from the start of deck
  if (currentSlide < 1) {slideIndex = slides.length}

  //ensure no pictures are displayed
  Object.keys(slides).forEach((item) => slides[item] = slides[item].style.display = "none");

  //ensure no markers are active
  Object.keys(markers).forEach((item) => markers[item].className = markers[item].className.replace(" active", ""));

  // make correct picture and marker active (take away one as slideIndex starts at 1)
  slides[slideIndex-1].style.display = "block";
  markers[slideIndex-1].className += " active";
}

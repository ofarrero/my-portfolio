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
 * Runs quiz on quiz page
 */

const quizContainer = document.getElementById('quiz');
const resultsContainer = document.getElementById('results');
const submitButton = document.getElementById('submit');

//create quiz questions 
const myQuestions = [
  {
    question: 'Where do I go to College?',
    answers: {
      a: 'UCD',
      b: 'TCD',
      c: 'UCC'
    },
    correctAnswer: 'b'
  },
  {
    question: 'How many dogs do I have?',
    answers: {
      a: 'None',
      b: 'One',
      c: 'Two'
    },
    correctAnswer: 'c'
  },
  {
    question: 'Where did I intern last summer?',
    answers: {
      a: 'Intel',
      b: 'Google',
      c: 'Facebook',
    },
    correctAnswer: 'a'
  },
  {
    question: 'What sport did I take part in in college last year?',
    answers: {
      a: 'Triathalon',
      b: 'Netball',
      c: 'Athletics',
    },
    correctAnswer: 'b'
  },
  {
    question: 'What musical did I see recently?',
    answers: {
      a: 'Hamilton',
      b: 'Hairspray',
      c: 'Wicked',
    },
    correctAnswer: 'a'
  },
  {
    question: 'What is my Mother\'s profession?',
    answers: {
      a: 'Nurse',
      b: 'Engineer',
      c: 'Librarian',
    },
    correctAnswer: 'c'
  },
  {
    question: 'What concert that I was meant to go to was cancelled due to Covid-19?',
    answers: {
      a: 'Vampire Weekend',
      b: 'Lewis Capaldi',
      c: 'The Killers',
    },
    correctAnswer: 'c'
  },
  {
    question: 'Who was my water filtration project conducted with?',
    answers: {
      a: 'Unicef',
      b: 'Engineers without Boarders',
      c: 'The Water Project',
    },
    correctAnswer: 'b'
  },
  {
    question: 'What was the name of my mentor last summer',
    answers: {
      a: 'Chris',
      b: 'Simon',
      c: 'Cillian',
    },
    correctAnswer: 'a'
  },
  {
    question: 'What am I studying in college?',
    answers: {
      a: 'Computer Science',
      b: 'Computer Engineering',
      c: 'Computer Systems',
    },
    correctAnswer: 'b'
  }
];

function buildQuiz(){
    const output = [];

    myQuestions.forEach(
        (currentQuestion, questionNumber)=>{
        //store all possible answers
        const answers = [];
        console.log(output);

        for(letter in currentQuestion.answers){
            // make radio button for each question
            answers.push(
                `<label>
                  <input type="radio" name="question${questionNumber}" value="${letter}">
                  ${letter} :
                  ${currentQuestion.answers[letter]}
                </label>`
            );
        }
        //add question html to output array
        output.push(
            `<div class="question"> ${currentQuestion.question} </div>
            <div class="answers"> ${answers.join('')} </div>`
        );
    
     }
    );
    //add question html to quiz html sheet
    quizContainer.innerHTML = output.join('');
}

function showResults(){

     const answerContainers = quizContainer.querySelectorAll('.answers');
    //users answers count
     let numCorrect = 0;

     myQuestions.forEach( (currentQuestion, questionNumber) => {

        // find selected answer
        const answerContainer = answerContainers[questionNumber];
        const selector = `input[name=question${questionNumber}]:checked`; //gets checked answer
        const userAnswer = (answerContainer.querySelector(selector) || {}).value;

        if(userAnswer === currentQuestion.correctAnswer){
        // add to correct ans
        numCorrect++;

        // colour the answers green
        answerContainers[questionNumber].style.color = 'lightgreen';
        }
        // if answer is wrong or blank
        else{
        // colour the answers red
        answerContainers[questionNumber].style.color = 'red';
    }
  });

  // show number of correct answers out of total
  resultsContainer.innerHTML = `${numCorrect} out of ${myQuestions.length}`;

}

// display quiz when window opens
buildQuiz();

// show results when button clicked
submitButton.addEventListener('click', showResults);
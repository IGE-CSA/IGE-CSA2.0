---
layout: base
title: Leaderboard
---
<!-- 
Get Method:
https://ige-backend.stu.nighthawkcodingsociety.com/api/quizleaders/ 

Post Method:
https://ige-backend.stu.nighthawkcodingsociety.com/api/quizleaders/post/{name}/{score}
-->

<div>
    <input type="text" id="username" placeholder="Enter your username">
    <br>
    <input type="text" id="score" placeholder="Enter your score">
    <button id="create-btn">Add</button>
</div>


  <div>
    <section class="team1">
      <main id="content" class="main-content" role="main">
        <table id="stock">
          <thead>
            <tr>
              <th style="width:30%">Name</th>
              <th style="width:10%">Score</th>
            </tr>
          </thead>
          <tbody id="result">
          </tbody>
        </table>
      </main>
    </section>
  </div>

  <br>
  <br>
  <br>

  <script>
    // prepare HTML result container for new output
    const resultContainer = document.getElementById("result");

    // Create an instance of AbortController
const controller = new AbortController();
const signal = controller.signal;

// Prepare your fetch request
const url = "https://ige-backend.stu.nighthawkcodingsociety.com/api/quizleaders/";
const headers = {
  method: 'GET',
  mode: 'cors',
  cache: 'default',
  signal: signal, // Use the signal from the AbortController
  headers: {
    'Content-Type': 'application/json'
  },
};

// Execute the fetch request
fetch(url, headers)
  .then(response => {
    // Handle the response
  })
  .catch(error => {
    // Handle any errors
    console.error('Fetch error:', error);
  });

// If you need to abort the request, you can call controller.abort()
// controller.abort();

    // fetch the API
    fetch(url, headers)
      // response is a RESTful "promise" on any successful fetch
      .then(response => {
        console.log('Response Headers: ', response.headers);
        // check for response errors
        if (response.status !== 200) {
          const errorMsg = 'Database response error: ' + response.status;
          console.log(errorMsg);
          const tr = document.createElement("tr");
          const td = document.createElement("td");
          td.innerHTML = errorMsg;
          tr.appendChild(td);
          resultContainer.appendChild(tr);
          return;
        }
        // fetch the data from API
        response.json().then(data => {
          console.log(data);
          for (let row in data) {
            console.log(data[row]);
            add_row(data[row]);
          }
        }).catch(err => {
          console.error('Fetch error:', err);
          const tr = document.createElement("tr");
          const td = document.createElement("td");
          td.innerHTML = err;
          tr.appendChild(td);
          resultContainer.appendChild(tr);
        });
      }).catch(err => {
        console.error('Fetch error:', err);
        const tr = document.createElement("tr");
        const td = document.createElement("td");
        td.innerHTML = err;
        tr.appendChild(td);
        resultContainer.appendChild(tr);
      });

    function add_row(rowData) {
      const tr = document.createElement("tr");
      for (let key in rowData) {
        const td = document.createElement("td");
        td.innerHTML = rowData[key];
        tr.appendChild(td);
      }
      resultContainer.appendChild(tr);
    }

    
  </script>

<!-- <script>
        const apiUrl = "https://ige-backend.stu.nighthawkcodingsociety.com/api/quizleaders/";
        const options = {
            method: 'GET',
            mode: 'cors',
            cache: 'default',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json'
            }
        };
        let count = 0;
        let score = 0;
        let questions = [];
        let currentQuestionIndex = 0;
        let correctAnswers = [];
        const questionContainer = document.getElementById("question-text");
        const answerButtons = document.querySelectorAll(".answer-btn");
        const nextButton = document.getElementById("next-btn");
        const usernameInput = document.getElementById("username");
        const createButton = document.getElementById("create-btn");
        const generateButton = document.getElementById("generate-btn");
        function loadQuestion(questionIndex) {
            nextButton.disabled = true;
            answerButtons.forEach(button => button.classList.remove("selected"));
            const question = questions[questionIndex];
            questionContainer.textContent = question.question;
            answerButtons.forEach((button, index) => {
                button.textContent = String.fromCharCode(65 + index) + ". " + question.choices[index];
            });
        }
        function handleAnswerClick(event) {
            nextButton.disabled = false;
            let temp;
            answerButtons.forEach(button => button.classList.remove("selected"));
            event.target.classList.add("selected");
            answerButtons.forEach((button, index) => {
                const choice = String.fromCharCode(65 + index).toLowerCase();
                if (event.target === button) {
                    temp = choice;
                }
            });
            const answerChoice = temp
            const currentCorrectAnswer = correctAnswers[currentQuestionIndex];
            if (answerChoice === currentCorrectAnswer && count === 0) {
                score += 10;
                count = 1;
            }
        }
        function updateScoreDisplay() {
            const scoreDisplay = document.getElementById("score-display");
            scoreDisplay.textContent = "Your score is " + score + "/50!";
        }
        function error(err) {
            console.error(err);
        }
        fetch(apiUrl, options)
            .then(response => {
                if (response.status !== 200) {
                    error('GET API response failure: ' + response.status);
                    return;
                }
                response.json().then(data => {
                    const questionData = data.slice(0, 5);
                    const answerChoices = data.slice(5, 10);
                    questions = questionData.map((question, index) => ({
                        question: question,
                        choices: answerChoices[index].split(', '),
                    }));
                    correctAnswers = answerChoices.map(choice => choice.charAt(choice.length - 1));
                    loadQuestion(currentQuestionIndex);
                });
            })
            .catch(err => {
                error(err + ": " + apiUrl);
            });
        answerButtons.forEach(button => {
            button.addEventListener("click", handleAnswerClick);
        });
        nextButton.addEventListener("click", () => {
            count = 0;
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.length) {
                loadQuestion(currentQuestionIndex);
            } else {
                questionContainer.textContent = "Quiz completed!";
                updateScoreDisplay();
                answerButtons.forEach(button => button.style.display = "none");
                nextButton.style.display = "none";
            }
        });
        createButton.addEventListener("click", () => {
            const username = usernameInput.value;
            const postData = {
                leaders: username,
                score: score,
            };
            fetch(`https://ige-backend.stu.nighthawkcodingsociety.com/api/quizleaders/post/${username}/${score}`, {
                    method: 'POST',
                    mode: 'cors',
                    cache: 'default',
                    credentials: 'same-origin',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer my-token'
                    },
                    body: JSON.stringify(postData)
            })
            .then(response => response.json())
            .then(data => {
                //
            })
            .catch(error => {
                console.error("Error: " + error);
            });
        });
    </script> -->
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

  <!-- HTML remains the same -->

<script>
  // Fetching Leaderboard Data
  const resultContainer = document.getElementById("result");
  const url = "https://ige-backend.stu.nighthawkcodingsociety.com/api/quizleaders/";

  fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok ' + response.statusText);
      }
      return response.json();
    })
    .then(data => {
      data.forEach(row => addRow(row));
    })
    .catch(error => {
      console.error('Fetch error:', error);
    });

  function addRow(rowData) {
    const tr = document.createElement("tr");
    Object.values(rowData).forEach(val => {
      const td = document.createElement("td");
      td.textContent = val;
      tr.appendChild(td);
    });
    resultContainer.appendChild(tr);
  }

  // Posting New Entries
  const addButton = document.getElementById("create-btn");
  addButton.addEventListener('click', () => {
    const username = document.getElementById("username").value;
    const score = document.getElementById("score").value;
    const postUrl = `https://ige-backend.stu.nighthawkcodingsociety.com/api/quizleaders/post/${username}/${score}`;

    fetch(postUrl, { method: 'POST' })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(postResponse => {
        console.log(postResponse);
        // Refresh the leaderboard or add the new row directly
        // For simplicity, just refreshing the page for now
        location.reload();
      })
      .catch(error => {
        console.error('Error:', error);
      });
  });
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
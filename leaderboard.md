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
              <th>ID</th>
              <th>Name</th>
              <th>Score</th>
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

<!-- Getting leaderboard data -->
<script>
  const resultContainer = document.getElementById("result");
  const leaderboardUrl = "http://localhost:8085/api/quizleaders/";
  const controller = new AbortController();
  const signal = controller.signal;

  async function fetchLeaderboard() {
    try {
      const response = await fetch(leaderboardUrl, {
        method: 'GET',
        signal: signal,
        mode: 'cors'
      });

      if (!response.ok) {
        throw new Error('Network response was not ok: ' + response.statusText);
      }

      const data = await response.json();
      data.forEach(row => addRow(row));
    } catch (error) {
      if (error.name === 'AbortError') {
        resultContainer.innerHTML += `<div>Error: Request timed out</div>`;
      } else {
        resultContainer.innerHTML += `<div>Error: Could not retrieve leaderboard data</div>`;
      }
    } 
  }

  function addRow(rowData) {
    const tr = document.createElement("tr");
    Object.values(rowData).forEach(val => {
      const td = document.createElement("td");
      td.textContent = val;
      tr.appendChild(td);
    });
    resultContainer.appendChild(tr);
  }

  document.addEventListener('DOMContentLoaded', fetchLeaderboard);
</script>





<script>
  // Event listener for posting new leaderboard entries
  const addButton = document.getElementById("create-btn");
  addButton.addEventListener('click', () => postLeaderboardEntry());

  async function postLeaderboardEntry() {
    const username = document.getElementById("username").value;
    console.log(username);
    const score = document.getElementById("score").value;
    console.log(score);
    const postUrl = `http://localhost:8085/api/quizleaders/post/${encodeURIComponent(username)}/${encodeURIComponent(score)}`;

    try {
      const response = await fetch(postUrl, {
        method: 'POST',
        signal: signal,
        mode: 'cors',
        headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer my-token'
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const postResponse = await response.json();
      console.log('Posted successfully:', postResponse);
      fetchLeaderboard(); // Refresh the leaderboard
    } catch (error) {
      if (error.name === 'AbortError') {
        console.error('Error: Request timed out');
      } else {
        console.error('Error posting data:', error);
      }
    }
  }
</script>
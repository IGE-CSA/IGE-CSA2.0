---
layout: default
search_exclude: true
---  

<script>
  const resultContainer = document.getElementById("result");
  const leaderboardUrl = "http://localhost:8085/api/sort/speeds";
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


  <div>
    <section class="team1">
      <main id="content" class="main-content" role="main">
        <table id="stock">
          <thead>
            <tr>
              <th>Type</th>
              <th>Speed</th>
            </tr>
          </thead>
          <tbody id="result">
          </tbody>
        </table>
      </main>
    </section>
  </div>
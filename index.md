---
layout: default
search_exclude: true
---  

<!-- <script>
// Make a GET request to the backend endpoint
fetch('http://localhost:8085/api/sort/speeds')
  .then(response => {
    // Check if the request was successful (status code 200)
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    
    // Parse the JSON response
    return response.json();
  })
  .then(data => {
    // Access each item in the dictionary and store them as variables
    const bubbleSortSpeed = data.bubbleSort;
    const insertionSortSpeed = data.insertionSort;
    const mergeSortSpeed = data.mergeSort;
    const selectionSortSpeed = data.selectionSort;

    // Now you can use these variables as needed
    console.log('Bubble Sort Speed:', bubbleSortSpeed);
    console.log('Insertion Sort Speed:', insertionSortSpeed);
    console.log('Merge Sort Speed:', mergeSortSpeed);
    console.log('Selection Sort Speed:', selectionSortSpeed);
  })
  .catch(error => {
    console.error('Error fetching data:', error);
  });
</script> -->


<!-- Getting leaderboard data -->
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
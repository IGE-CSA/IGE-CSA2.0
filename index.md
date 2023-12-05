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
      console.log(data);
      }
}
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
---
layout: default
search_exclude: true
---  

<script>
  const resultContainer = document.getElementById("result");
  const sortUrl = "http://localhost:8085/api/sort/speeds";
  const controller = new AbortController();
  const signal = controller.signal;


  async function fetchSort() {
    try {
      const response = await fetch(sortUrl, {
        method: 'GET',
        signal: signal,
        mode: 'cors'
      });

      if (!response.ok) {
        throw new Error('Network response was not ok: ' + response.statusText);
      }

      const data = await response.json();
      console.log(data);

      // creating variables
      const bubble = data['bubbleSort'];
      console.log(bubble);
      const insert = data['insertionSort'];
      console.log(insert);
      const merge = data['mergeSort'];
      console.log(merge);
      const selection = data['selectionSort'];
      console.log(selection);



      data.forEach(row => addRow(row));
    } catch (error) {
      if (error.name === 'AbortError') {
        resultContainer.innerHTML += `<div>Error: Request timed out</div>`;
      } else {
        resultContainer.innerHTML += `<div>Error: Could not retrieve sort data</div>`;
      }
    } 
  }

  document.addEventListener('DOMContentLoaded', fetchSort);
</script>
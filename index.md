---
layout: default
search_exclude: true
---  

<script>
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
</script>
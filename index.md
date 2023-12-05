---
layout: default
search_exclude: true
---  

<style>
body {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100vh;
    margin: 0;
}

.beaker {
    position: relative;
    width: 100px;
    height: 200px;
    background-color: #ccc;
    border: 5px solid #999;
    border-radius: 5px;
    overflow: hidden;
    margin-bottom: 20px;
}

.water {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 0;
    background-color: #3498db;
    transition: height 1s ease-in-out;
}

#fillButton {
    padding: 10px;
    cursor: pointer;
    background-color: #3498db;
    color: #fff;
    border: none;
    border-radius: 5px;
}

#fillRate {
    margin-bottom: 20px;
}
</style>


<div class="beaker">
<div class="water" id="water"></div>
</div>
<label for="fillRate">Fill Rate:</label>
<input type="range" id="fillRate" min="1" max="10" value="5">
<button id="fillButton" onclick="fillWater()">Fill Beaker</button>


<script>
function fillWater() {
    const water = document.getElementById('water');
    const fillRate = document.getElementById('fillRate').value;
    const duration = 10 / fillRate; // Adjust the divisor for a suitable scale

    water.style.transition = `height ${duration}s ease-in-out`;
    water.style.height = '100%';
}
</script>

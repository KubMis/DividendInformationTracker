let selectedTickers=[];
function getAllTickers(){
    fetch('api/getTickers')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            data.forEach(ticker => {
                const tickerElement = document.createElement('div');
                tickerElement.innerText = ticker;
                tickerElement.className = "ticker";
                tickerElement.addEventListener('click', function(){
                    console.log("Clicked on ticker element");
                    if (selectedTickers.includes(ticker)) {
                        selectedTickers = selectedTickers.filter(item => item !== ticker);
                        tickerElement.classList.remove("selected");
                    } else {
                        selectedTickers.push(ticker);
                        tickerElement.classList.add("selected");
                    }
                });
                document.getElementById('tickers').appendChild(tickerElement);
                console.log("Created ticker element")
            })
            console.log('Success')
        })
        .catch(error => {
            console.error('Error:', error);
        })
}

function addTickersToSelectedTickers(){
    selectedTickers.forEach(ticker => {
        const tickerElement = document.createElement('div');
        tickerElement.innerText = ticker;
        tickerElement.className = "ticker";
        document.getElementById('selected-tickers').appendChild(tickerElement);
    });
    Array.from(document.getElementsByClassName('selected')).forEach(element => {
        element.classList.remove('selected');});
    selectedTickers = [];
}

window.onload = getAllTickers;

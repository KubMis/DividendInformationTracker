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
                const tickerElement = createTickerElement(ticker, " selected");
                document.getElementById('tickers').appendChild(tickerElement);
            })
            console.log('Success')
        })
        .catch(error => {
            console.error('Error:', error);
        })
}

function addTickersToSelectedTickers(){
    selectedTickers.forEach(ticker => {
            const tickerInTickers = document.getElementById(ticker + " selected");
            if(tickerInTickers) tickerInTickers.remove();
            if(!document.getElementById(ticker+" not-selected")){
            const tickerElement = createTickerElement(ticker, " not-selected");
            document.getElementById('selected-tickers-list').appendChild(tickerElement);
        }
    });
    Array.from(document.getElementsByClassName('selected')).forEach(element => {
        element.classList.remove('selected');
    });
    selectedTickers = [];
}

function removeTickersFromSelectedTickers(){
    selectedTickers.forEach(ticker => {
        const tickerInTickers = document.getElementById(ticker + " not-selected");
            if(tickerInTickers) tickerInTickers.remove();

            if (!document.getElementById(ticker+" selected")) {
                const tickerElement = createTickerElement(ticker, " selected");
                document.getElementById('tickers').appendChild(tickerElement);
                sortDivsInAlphabeticalOrder('tickers');
            }

    });
    Array.from(document.getElementsByClassName('selected')).forEach(element => {
        element.classList.remove('selected');
    });
    selectedTickers = [];
}

function createTickerElement(ticker, selected){
    const tickerElement = document.createElement('div');
    tickerElement.id = ticker+selected;
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
    console.log("Created ticker element")
    return tickerElement;
}

function sortDivsInAlphabeticalOrder(selector){
    const tickers = document.getElementById(selector);
    const tickerElements = Array.from(tickers.children);
    tickerElements.sort((a, b) => a.innerText.localeCompare(b.innerText));

    const fragment = document.createDocumentFragment();
    tickerElements.forEach(element => fragment.appendChild(element));

    tickers.innerHTML = '';
    tickers.appendChild(fragment);
}

window.onload = getAllTickers;

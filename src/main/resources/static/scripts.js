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
    doActionOnSelectedDivs(" selected", " not-selected", "selected-tickers-list");
}

function removeTickersFromSelectedTickers(){
    doActionOnSelectedDivs(" not-selected", " selected", "tickers");
}

function doActionOnSelectedDivs(selector, selector2, selector3){
    selectedTickers.forEach(ticker => {
        const tickerInTickers = document.getElementById(ticker + selector);
        if(tickerInTickers) tickerInTickers.remove();

        if (!document.getElementById(ticker+selector2)) {
            const tickerElement = createTickerElement(ticker, selector2);
            document.getElementById(selector3).appendChild(tickerElement);
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

function getTextFromInput(inputId){
    const innerText= document.getElementById(inputId).value;
    console.log(innerText)
    return innerText
}

function saveSelectedTickers(portfolioName){
    const tickersInSelectedDiv = Array.from(document.getElementById('selected-tickers-list').children)
        .map(element => element.innerText);
    console.log("portfolio name ins save function")
    console.log(portfolioName)
    if(portfolioName==null || portfolioName===""){
        console.error("Portfolio name must not be empty")
        return;
    }
       fetch("api/createPortfolio", {
           method: "Post",
           body: JSON.stringify({
               "portfolioName": portfolioName,
               "stocks": tickersInSelectedDiv
           }),
           headers: {"Content-type": "application/json; charset=UTF-8"}
       }).then((response)=>{
           if (!response.ok) {
               throw new Error('Network response was not ok');
           }
           return response.json();
       })
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

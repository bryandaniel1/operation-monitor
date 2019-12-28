/**
 * Event handlers and additional styling not defined in a CSS file are defined 
 * in the callback function parameter of the ready function.
 */
var stockData;
var historyData;
$(document).ready(function () {
    $("#submitButton").button();
    $("#message").addClass("ui-state-error ui-corner-all");
    $("#message").hide();
    google.charts.load('current', {packages: ['corechart', 'line']});

    /**
     * This event handler defines the behavior for the IP address form 
     * submission.
     * @param {type} event
     */
    $("#stockPriceForm").submit(function (event) {
        event.preventDefault();
        $.ajax({
            url: "http://localhost:8080/StocksClient-web/stocks/price",
            type: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8", // send as JSON
            data: $.param({stockSymbol: $("#symbol").val()}),
            complete: function (data) {
                console.log("Request complete");
            },
            success: function (data) {
                if (data !== null) {
                    stockData = data;
                    var stockDisplay = React.createElement(StockDisplay, {data: stockData});
                    ReactDOM.render(
                            stockDisplay,
                            document.getElementById("dataDiv")
                            )
                    getStockHistory();
                } else {
                    $("#message").html("Could not find data for: " + $("#symbol").val());
                    $("#message").show();
                }
            },
            error: function (request, status, error) {
                stockData = null;
                $("#status").html("Error:" + request.responseText);
                ReactDOM.unmountComponentAtNode(document.getElementById("dataDiv"));
            }
        });
    });

    /**
     * Retrieves the history for the stock symbol and creates the history line 
     * chart.
     */
    function getStockHistory() {
        $.ajax({
            url: "http://localhost:8080/StocksClient-web/stocks/history",
            type: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8", // send as JSON
            data: $.param({stockSymbol: $("#symbol").val()}),
            complete: function (data) {
                console.log("Request complete");
            },
            success: function (data) {
                if (data !== null) {
                    historyData = data;
                    ReactDOM.unmountComponentAtNode(document.getElementById("chartDiv"));
                    var historyDisplay = React.createElement(HistoryDisplay, {data: historyData});
                    ReactDOM.render(
                            historyDisplay,
                            document.getElementById("chartDiv")
                            )
                } else {
                    $("#message").html("Could not find data for: " + $("#symbol").val());
                    $("#message").show();
                }
            },
            error: function (request, status, error) {
                historyData = null;
                $("#status").html("Error:" + request.responseText);
                ReactDOM.unmountComponentAtNode(document.getElementById("chartDiv"));
            }
        });
    }
});
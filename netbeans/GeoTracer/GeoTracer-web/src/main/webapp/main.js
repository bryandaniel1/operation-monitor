/**
 * Event handlers and additional styling not defined in a CSS file are defined 
 * in the callback function parameter of the ready function.
 */
$(document).ready(function () {
    $("#submitButton").button();
    $("#dataSection").addClass("ui-widget");
    $("#dataAccordion").accordion({heightStyle: "content"});
    $("#dataSection").hide();
    $("#mapSection").hide();
    $("#message").addClass("ui-state-error ui-corner-all");
    $("#message").hide();
    var requestIp;

    // getting the user's IP address from ipify
    $.get("https://api.ipify.org?format=json", function (data) {
        requestIp = data.ip;
    });

    /**
     * This function uses the location data to draw a map.
     * @param {type} hopLocations
     */
    function showMap(hopLocations) {
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 3,
            center: {lat: hopLocations[0].latitude, lng: hopLocations[0].longitude},
            mapTypeId: 'terrain'
        });
        var coordinates = [];
        var coordinatesCount = 0;
        for (var i = 0; i < hopLocations.length; i++) {
            if (hopLocations[i] !== null) {
                coordinates[coordinatesCount++] = new google.maps.LatLng(parseFloat(hopLocations[i].latitude),
                        parseFloat(hopLocations[i].longitude));
            }
        }
        var path = new google.maps.Polyline({
            path: coordinates,
            geodesic: false,
            strokeColor: '#FF0000',
            strokeOpacity: 1.0,
            strokeWeight: 2
        });
        path.setMap(map);
        $("#mapSection").show();
    }

    /**
     * This function appends specific geographic location data to the 
     * dataAccordion element.
     * @param {type} data
     */
    function showData(data) {
        for (var i = 0; i < data.length; i++) {
            if (data[i] === null) {
                $("#dataAccordion").append("\
                <h3 id=\"header" + (i + 1) + "\">" + (i + 1) + ".&nbsp;no response</h3>\n\
                <div id=\"content" + (i + 1) + "\">\n\
                </div>");
            } else {
                var countryCode = data[i].countryCode === "" ? "*" : data[i].countryCode;
                var countryName = data[i].countryName === "" ? "*" : data[i].countryName;
                var regionCode = data[i].regionCode === "" ? "*" : data[i].regionCode;
                var regionName = data[i].regionName === "" ? "*" : data[i].regionName;
                var city = data[i].city === "" ? "*" : data[i].city;
                var zipCode = data[i].zipCode === "" ? "*" : data[i].zipCode;
                var timeZone = data[i].timeZone === "" ? "*" : data[i].timeZone;
                var latitude = data[i].latitude === "" ? "*" : data[i].latitude;
                var longitude = data[i].longitude === "" ? "*" : data[i].longitude;
                var metroCode = data[i].metroCode === "" ? "*" : data[i].metroCode;
                $("#dataAccordion").append("\
                <h3 id=\"header" + (i + 1) + "\">" + (i + 1) + ".&nbsp;IP Address: "
                        + data[i].ipAddress + "</h3>\n\
                <div id=\"content" + (i + 1) + "\">\n\
                    <div class=\"dataNames\">\n\
                        <div class=\"ipAddressLabel\">IP address:&nbsp;</div>\n\
                        <div class=\"countryCodeLabel\">Country Code:&nbsp;</div>\n\
                        <div class=\"countryNameLabel\">Country Name:&nbsp;</div>\n\
                        <div class=\"regionCodeLabel\">Region Code:&nbsp;</div>\n\
                        <div class=\"regionNameLabel\">Region Name:&nbsp;</div>\n\
                        <div class=\"cityLabel\">City:&nbsp;</div>\n\
                        <div class=\"zipCodeLabel\">Zip Code:&nbsp;</div>\n\
                        <div class=\"timeZoneLabel\">Time Zone:&nbsp;</div>\n\
                        <div class=\"latitudeLabel\">Latitude:&nbsp;</div>\n\
                        <div class=\"longitudeLabel\">Longitude:&nbsp;</div>\n\
                        <div class=\"metroCodeLabel\">Metro Code:&nbsp;</div>\n\
                    </div>\n\
                    <div class=\"dataValues\">\n\
                        <div class=\"ipAddressDiv\">" + data[i].ipAddress + "</div>\n\
                        <div class=\"countryCodeDiv\">" + countryCode + "</div>\n\
                        <div class=\"countryNameDiv\">" + countryName + "</div>\n\
                        <div class=\"regionCodeDiv\">" + regionCode + "</div>\n\
                        <div class=\"regionNameDiv\">" + regionName + "</div>\n\
                        <div class=\"cityDiv\">" + city + "</div>\n\
                        <div class=\"zipCodeDiv\">" + zipCode + "</div>\n\
                        <div class=\"timeZoneDiv\">" + timeZone + "</div>\n\
                        <div class=\"latitudeDiv\">" + latitude + "</div>\n\
                        <div class=\"longitudeDiv\">" + longitude + "</div>\n\
                        <div class=\"metroCodeDiv\">" + metroCode + "</div>\n\
                    </div>\n\
                </div>");
            }
        }
        $("#dataSection").show();
    }

    /**
     * This function removes old data from the element holding geographic 
     * location data.
     */
    function clearData() {
        $("#dataAccordion").empty();
        $("#dataAccordion").accordion('destroy');
    }

    /**
     * This event handler defines the behavior for the IP address form 
     * submission.
     * @param {type} event
     */
    $("#ipAddressForm").submit(function (event) {
        event.preventDefault();
        clearData();

        $.ajax({
            url: "http://localhost:8080/GeoTracer-web/service/find",
            type: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8", // send as JSON
            data: $.param({requestIpAddress: requestIp, destinationIpAddress: $("#ip").val()}),
            complete: function (data) {
                console.log("Request complete");

            },
            success: function (data) {
                if (data !== null) {
                    showMap(data);
                    showData(data);
                    $("#dataAccordion").accordion({heightStyle: "content"});
                } else {
                    $("#mapSection").hide();
                    $("#dataSection").hide();
                    $("#message").html("Could not find location for: " + $("#ip").val());
                    $("#message").show();
                }
            },
            error: function (request, status, error) {
                $("#status").html("Error:" + request.responseText);
                $("#map").empty();
                $("#mapSection").hide();
                $("#dataSection").hide();
            }
        });
    });
});
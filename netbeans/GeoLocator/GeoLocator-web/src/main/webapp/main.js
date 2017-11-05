/**
 * Event handlers and additional styling not defined in a CSS file are defined 
 * in the callback function parameter of the ready function.
 */
$(document).ready(function () {
    $("#submitButton").button();
    $("#dataSection").addClass("ui-widget");
    $("#dataSection").hide();
    $("#mapSection").hide();
    $("#message").addClass("ui-state-error ui-corner-all");
    $("#message").hide();

    // getting the user's IP address from ipify
    $.get("https://api.ipify.org?format=json", function (data) {
        console.log(data);
        $("#ip").val(data.ip);
    });

    /**
     * This function uses the location data to draw a map.
     * @param {type} location
     */
    function showMap(location) {
        var map;
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: Number(location.latitude), lng: Number(location.longitude)},
            zoom: 15
        });
        var marker = new google.maps.Marker({
            position: {lat: Number(location.latitude), lng: Number(location.longitude)},
            map: map,
            title: "Public IP:" + location.ipAddress + " @ " + location.city
        });
        $("#mapSection").show();
    }

    /**
     * This function appends specific geographic location data to the 
     * appropriate element.
     * @param {type} data
     */
    function showData(data) {
        var countryCode = data.countryCode === "" ? "*" : data.countryCode;
        var countryName = data.countryName === "" ? "*" : data.countryName;
        var regionCode = data.regionCode === "" ? "*" : data.regionCode;
        var regionName = data.regionName === "" ? "*" : data.regionName;
        var city = data.city === "" ? "*" : data.city;
        var zipCode = data.zipCode === "" ? "*" : data.zipCode;
        var timeZone = data.timeZone === "" ? "*" : data.timeZone;
        var latitude = data.latitude === "" ? "*" : data.latitude;
        var longitude = data.longitude === "" ? "*" : data.longitude;
        var metroCode = data.metroCode === "" ? "*" : data.metroCode;
        $("#ipAddressDiv").append(data.ipAddress);
        $("#countryCodeDiv").append(countryCode);
        $("#countryNameDiv").append(countryName);
        $("#regionCodeDiv").append(regionCode);
        $("#regionNameDiv").append(regionName);
        $("#cityDiv").append(city);
        $("#zipCodeDiv").append(zipCode);
        $("#timeZoneDiv").append(timeZone);
        $("#latitudeDiv").append(latitude);
        $("#longitudeDiv").append(longitude);
        $("#metroCodeDiv").append(metroCode);
        $("#dataSection").show();
    }

    /**
     * This function removes old data from the elements holding geographic 
     * location data.
     */
    function clearData() {
        $("#ipAddressDiv").empty();
        $("#countryCodeDiv").empty();
        $("#countryNameDiv").empty();
        $("#regionCodeDiv").empty();
        $("#regionNameDiv").empty();
        $("#cityDiv").empty();
        $("#zipCodeDiv").empty();
        $("#timeZoneDiv").empty();
        $("#latitudeDiv").empty();
        $("#longitudeDiv").empty();
        $("#metroCodeDiv").empty();
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
            url: "http://localhost:8080/GeoLocator-web/service/find",
            type: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8", // send as JSON
            data: $.param({ipAddress: $("#ip").val()}),
            complete: function (data) {
                console.log("Request complete");

            },
            success: function (data) {
                if (data !== null) {
                    clearData();
                    showMap(data);
                    showData(data);
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
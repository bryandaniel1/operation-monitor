/* 
 * Copyright 2017 Bryan Daniel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
var searchEventLocation = null;
var hopLocations = null;

/**
 * This function removes old data from the element holding geographic 
 * location data.
 */
function clearData() {
    $("#dataAccordion").empty();
    $("#dataAccordion").accordion('destroy');
    $(".ipAddressDiv").empty();
    $(".countryCodeDiv").empty();
    $(".countryNameDiv").empty();
    $(".regionCodeDiv").empty();
    $(".regionNameDiv").empty();
    $(".cityDiv").empty();
    $(".zipCodeDiv").empty();
    $(".timeZoneDiv").empty();
    $(".latitudeDiv").empty();
    $(".longitudeDiv").empty();
    $(".metroCodeDiv").empty();
}

/**
 * This function uses the location data to draw a map.
 * @param {type} location
 */
function showSearchMap(location) {
    var map;
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: Number(location["latitude"]), lng: Number(location["longitude"])},
        zoom: 15
    });
    var marker = new google.maps.Marker({
        position: {lat: Number(location["latitude"]), lng: Number(location["longitude"])},
        map: map,
        title: "Public IP:" + location["ipAddress"] + " @ " + location["city"]
    });
    $("#mapSection").show();
}

/**
 * This function appends specific geographic location data to the 
 * appropriate element.
 * @param {type} data
 */
function showSearchData(data) {
    var countryCode = data.country === null ? "*" : data.country.countryCode;
    var countryName = data.country === null ? "*" : data.country.countryName;
    var regionCode = data.region === null ? "*" : data.region.regionCode;
    var regionName = data.region === null ? "*" : data.region.regionName;
    var city = data.city === null ? "*" : data.city;
    var zipCode = data.zipCode === null ? "*" : data.zipCode;
    var timeZone = data.timeZone === null ? "*" : data.timeZone;
    var latitude = data.latitude === null ? "*" : data.latitude;
    var longitude = data.longitude === null ? "*" : data.longitude;
    var metroCode = data.metroCode === null ? "*" : data.metroCode;
    $(".ipAddressDiv").append(data.ipAddress);
    $(".countryCodeDiv").append(countryCode);
    $(".countryNameDiv").append(countryName);
    $(".regionCodeDiv").append(regionCode);
    $(".regionNameDiv").append(regionName);
    $(".cityDiv").append(city);
    $(".zipCodeDiv").append(zipCode);
    $(".timeZoneDiv").append(timeZone);
    $(".latitudeDiv").append(latitude);
    $(".longitudeDiv").append(longitude);
    $(".metroCodeDiv").append(metroCode);
    $("#dataSection").show();
}

/**
 * This function uses the location data to draw a map.
 * @param {type} hopLocations
 */
function showTracerMap(hopLocations) {
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
function showTracerData(data) {
    for (var i = 0; i < data.length; i++) {
        if (data[i] === null) {
            $("#dataAccordion").append("\
                <h3 id=\"header" + (i + 1) + "\">" + (i + 1) + ".&nbsp;no response</h3>\n\
                <div id=\"content" + (i + 1) + "\">\n\
                </div>");
        } else {
            var countryCode = data[i].country === null ? "*" : data[i].country.countryCode;
            var countryName = data[i].country === null ? "*" : data[i].country.countryName;
            var regionCode = data[i].region === null ? "*" : data[i].region.regionCode;
            var regionName = data[i].region === null ? "*" : data[i].region.regionName;
            var city = data[i].city === null ? "*" : data[i].city;
            var zipCode = data[i].zipCode === null ? "*" : data[i].zipCode;
            var timeZone = data[i].timeZone === null ? "*" : data[i].timeZone;
            var latitude = data[i].latitude === null ? "*" : data[i].latitude;
            var longitude = data[i].longitude === null ? "*" : data[i].longitude;
            var metroCode = data[i].metroCode === null ? "*" : data[i].metroCode;
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
 * This method opens the user details dialog.
 * @param {type} username
 * @returns {undefined}
 */
function showUserDetails(username) {
    $("div[id$=" + username + "]").dialog("open");
}

$(document).ready(function () {
    $(".submitButton").button();
    $("#dataSection").addClass("ui-widget");
    $("#dataAccordion").accordion({heightStyle: "content"});
    $("#dataSection, #mapSection").hide();
    $("#message").addClass("ui-state-error ui-corner-all").hide();
    $(".datepicker").datepicker({
        dateFormat: "yy-mm-dd",
        changeYear: true,
        yearRange: "-100:+0"
    });
    $('.eventTable, .userTable').DataTable({
        destroy: true,
        responsive: true,
        columnDefs: [
            {responsivePriority: 1, targets: 0},
            {responsivePriority: 2, targets: 2},
            {responsivePriority: 3, targets: 3}
        ]
    });
    $(".dialog").dialog({
        autoOpen: false,
        show: {
            effect: "blind",
            duration: 1000
        },
        hide: {
            effect: "blind",
            duration: 1000
        },
        width: 500
    });

    searchEventLocation = $("#searchEventLocation").val();
    hopLocations = $("#hopLocations").val();

    if (searchEventLocation.length !== 0) {
        clearData();
        showSearchMap(JSON.parse(searchEventLocation));
        showSearchData(JSON.parse(searchEventLocation));
    } else if (hopLocations.length !== 0) {
        clearData();
        showTracerMap(JSON.parse(hopLocations));
        showTracerData(JSON.parse(hopLocations));
        $("#dataAccordion").accordion({heightStyle: "content"});
    }
});

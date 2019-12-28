/* 
 * Copyright 2019 Bryan Daniel.
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
var priceSearchResult = null;
var historyResults = null;

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
});

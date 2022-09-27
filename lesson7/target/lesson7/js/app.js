window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}
window.ajax = function (data, $error, url = "", dataType = "json") {
    $.ajax({
        type: "POST",
        url,
        dataType,
        data,
        success: function (response) {
            if (response["error"]) {
                $error.text(response["error"]);
            } else {
                location.href = response["redirect"];
            }
        }})
}

function validateDates() {
    var checkin = document.getElementById("checkin").value;
    var checkout = document.getElementById("checkout").value;
    if (checkin === "" || checkout === "") {
        return false;
    }
    var checkinDate = new Date(checkin);
    var checkoutDate = new Date(checkout);
    var today = new Date();
    if (checkoutDate <= checkinDate) {
        markup = "<p class=\"text-secondary\"><small><small>⚠️️ Checkout date must be after checkin date</small></small></p>";
        document.getElementById("date-error").innerHTML = markup;
        console.log("checkoutDate <= checkinDate")
        return false;
    } else if (checkinDate < today) {
        markup = "<p class=\"text-secondary\"><small><small>⚠️ Checkin date must be no earlier than tomorrow</small></small></p>";
        document.getElementById("date-error").innerHTML = markup;
        console.log("checkinDate < today")
        return false;
    } else {
        markup = "";
        document.getElementById("date-error").innerHTML = markup;
        return true;
    }
}

function updateSearchBtn() {
    console.log("updateSearchBtn() initiated")
    searchButton = document.getElementById("searchButton");
    if (validateDates()) {
        searchButton.disabled = false;
    } else {
        searchButton.disabled = true;
    }
}

window.onload = updateSearchBtn();

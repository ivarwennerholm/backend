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
        console.log("checkoutDate <= checkinDate")
        return false;
    } else if (checkinDate < today) {
        console.log("checkinDate < today")
        return false;
    } else {
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

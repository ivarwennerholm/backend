function validateName() {
    nameInput = document.getElementById("name");
    var custName = nameInput.value;
    var nameRegex = /^[A-Za-zÅÄÖåäö\s]+$/;
    if (!nameRegex.test(custName) || custName.length < 2) {
        nameInput.className = "form-control w-auto text-center is-invalid";
        return false;
    } else {
        nameInput.className = "form-control w-auto text-center is-valid";
        return true;
    }
}

function validatePhone() {
    phoneInput = document.getElementById("phone");
    custPhone = phoneInput.value;
    var phoneRegex = /^[\d-]+$/;
    if (!phoneRegex.test(custPhone) || custPhone.length < 5 || custPhone.length > 15) {
        phoneInput.className = "form-control w-auto text-center is-invalid";
        return false;
    } else {
        phoneInput.className = "form-control w-auto text-center is-valid";
        return true;
    }
}

function valAllFieldsAndUpdateConfirmBtn() {
    validateName();
    validatePhone();
    updateConfirmButton();
}

function areAllFieldsValid() {
    if (
        validateName() &&
        validatePhone()
    ) {
        return true;
    } else {
        return false;
    }
}

function updateConfirmButton() {
    confirmButton = document.getElementById("confirmButton");
    if (areAllFieldsValid()) {
        confirmButton.disabled = false;
    } else {
        confirmButton.disabled = true;
    }
}

window.onload = valAllFieldsAndUpdateConfirmBtn();

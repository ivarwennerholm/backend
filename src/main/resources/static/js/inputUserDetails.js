function validateName() {
    nameInput = document.getElementById("name");
    if (nameInput.value === "")
        return false;
    var custName = nameInput.value;
    var nameRegex = /^[A-Za-zÅÄÖåäö\s]+$/;
    if (!nameRegex.test(custName) || custName.length < 2) {
        nameInput.className = "form-control w-auto text-center is-invalid";
        markup = "<p class=\"text-secondary\"><small><small>⚠️️ Name can only contain letters and whitespaces and must be at least two characters long</small></small></p>";
        document.getElementById("name-error").innerHTML = markup;
        return false;
    } else {
        nameInput.className = "form-control w-auto text-center is-valid";
        markup = "";
        document.getElementById("name-error").innerHTML = markup;
        return true;
    }
}

function validatePhone() {
    phoneInput = document.getElementById("phone");
    custPhone = phoneInput.value;
    if (custPhone === "")
        return false;
    var phoneRegex = /^[\d-]+$/;
    if (!phoneRegex.test(custPhone) || custPhone.length < 5 || custPhone.length > 15) {
        phoneInput.className = "form-control w-auto text-center is-invalid";
        markup = "<p class=\"text-secondary\"><small><small>⚠️️ Phone number can only contain digits and hyphens and must be between 5 and 16 digits long</small></small></p>";
        document.getElementById("phone-error").innerHTML = markup;
        return false;
    } else {
        phoneInput.className = "form-control w-auto text-center is-valid";
        markup = "";
        document.getElementById("phone-error").innerHTML = markup;
        return true;
    }
}

function validateEmail(){
    var email = document.getElementById("email");
    if (email.value === "")
        return false;
    var email_error = document.getElementById("email-error");
    var regex = /^(.+)@(\S+)$/;
    if (!regex.test(email.value)){
        email.className = "form-control w-auto text-center is-invalid";
        markup = "<p class=\"text-secondary\"><small><small>⚠️️ Email format is valid. Please try again.</small></small></p>";
        email_error.innerHTML = markup;
        return false;
    } else {
        email.className = "form-control w-auto text-center is-valid";
        markup = "";
        email_error.innerHTML = markup;
        return true;
    }
}

function valAllFieldsAndUpdateConfirmBtn() {
    validateName();
    validatePhone();
    validateEmail();
    updateConfirmButton();
}

function areAllFieldsValid() {
    if (
        validateName() &&
        validatePhone() &&
        validateEmail()
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
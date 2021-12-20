var sh=/^[\w-\.]+@[\w-]+\.[a-z]{2,4}$/i;
function validMail(){
    var myMail = document.getElementById('email').value;
    var valid = sh.test(myMail);
    if (valid){

        document.getElementById('email').style.backgroundColor = "white";
        output = ' ';
    }
    else {
        document.getElementById('email').style.backgroundColor = "red";
        output = 'Адрес электронной почты введен неправильно!';
    }

    document.getElementById('message').innerHTML = output;
    return valid;
}
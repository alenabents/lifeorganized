function validPassword(){
    var myPassword = document.getElementById('password').value;
    var myPassword2 = document.getElementById('password2');
    let check=0;
    if(myPassword2&&myPassword2.value){
        myPassword2=myPassword2.value;
        if (myPassword.length>=1 && myPassword2.length>=1){
            output = '';
            check=1;
        }
        else {
            check=0;
            output = 'Пароль не должен быть пустым';
        }
    }
    else{
        if (myPassword.length>=1){
            output = '';
            check=1;
        }
        else {
            check=0;
            output = 'Пароль не должен быть пустым';
        }
    }
    document.getElementById('messagePass').innerHTML = output;
    return check;
}
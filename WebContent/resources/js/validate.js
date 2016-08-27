$(document).ready(function () {
    $('#loginForm').validate({ 
        rules: {
            username: {
                required: true,
                minlength: 5,
                maxlength: 15
            },
            password: {
                required: true,
                minlength: 8,
                maxlength: 15
            }
        },
        messages: {
            username: "Please enter username with maximum 15 characters long.",
            password: "Please enter password with atleast 8 and at max 15 characters long.",
        },
         submitHandler: function(form) {
            var googleResponse = jQuery('#g-recaptcha-response').val();
            if (!googleResponse) {
                $("#errorBox").text("please verify captcha");
                return false;
            } else {

                $(form).ajaxSubmit();
            }
          }
    });


    $('#editPasswordForm').validate({ 
        rules: {
            newpassword:  {
                required: true,
                minlength: 8,
                maxlength: 15
            },
            confirmpassword: {
                equalTo: "#newpassword",
                minlength: 8,
                maxlength: 15
            }
        },
        messages: {
            newpassword: "Please enter password with atleast 8 and at max 15 characters long.",
            confirmpassword: "Password does not match.",
        }
    });

    $('#newPasswordForm').validate({ 
        rules: {
            email:  {
                required: true,
                email: true,
                maxlength: 50  
            }
        },
        messages: {
            email: "Please enter valid email."
        }
    });

    $('#registerForm').validate({ 
        rules: {
            username:  {
                required: true,
                minlength: 5,
                maxlength: 15
            },firstname:  {
                required: true
            },lastname:  {
                required: true
            },password:  {
                required: true,
                minlength: 8,
                maxlength: 15
            },confirmpassword: {
                equalTo: "#password",
                minlength: 8,
                maxlength: 15
            },tmpPassword:  {
            	required: true,
                minlength: 8,
                maxlength: 15
            },email:  {
                required: true,
                email: true,
                maxlength: 50
            },
		    contact: {
                required: true,
                phoneUS: true
            },dob:  {
                required: true,
                date: true
            },pii:  {
                required: true
            },answer:  {
                required: true
            }
        },
        messages: {
            username: "Please enter username at least 5 characters long.",
            firstname: "Please enter your first name.",
            lastname: "Please enter your last name.",
            password: "Please enter password with atleast 8 and at max 15 characters long.",
            confirmpassword: "Password does not match.",
            email: "Please enter valid email.",
            contact: "Please enter valid contact number.",
            dob: "Please enter valid date of birth.",
            pii: "Please provide Personally identifiable information.",
            answer: "Please provide valid answer for security question.",
            tmpPassword: "Please enter password at least 8 at max 15 characters long."
        }
    });

    $('#resendConfirmForm').validate({ 
        rules: {
            email:  {
                required: true,
                email: true
            }
        },
        messages: {
            email: "Please enter valid email."
        }
    });

    $('#securityQuestionForm').validate({ 
        rules: {
            answer:  {
                required: true
            }
        },
        messages: {
            answer: "Please enter valid answer."
        }
    });

    $('#unlockAccountForm').validate({ 
        rules: {
            email:  {
                required: true,
                email: true
            }
        },
        messages: {
            email: "Please enter valid email."
        }
    });

    $('#check_username_availability').click(function(){
        $('#username_availability_result').html("checking ...");
        check_availability();
    });

});


//function to check username availability
function check_availability(){
    //get the username
    var username = $('#username').val();
    //use ajax to run the check
    $.post("/check_availability", { username: username },
        function(result){
            //if the result is 1
            if(result == 1){
                //show that the username is available
                $('#username_availability_result').html(username + ' is Available');
            }else{
                //show that the username is NOT available
                $('#username_availability_result').html(username + ' is not Available');
            }
    });
}

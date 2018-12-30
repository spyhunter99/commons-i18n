/*
 * Copyright 2001-2013 The Apache Software Foundation.
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
 *
 */


function logout()
{

    $.get('logout.jsp', function(data) {
        window.location = "index.jsp";
    });
}

function RefreshLoginPage()
{
    $.get('login.jsp', function(data) {
        $('#loginfield').html(data);
    });
}

function Login(){
    
    
    $("#loginbutton").addClass("disabled");
    $("#loginbutton").text(i18n_loading);

    var form = $("#uddiform");
    var d = form.serializeArray();
    var request = $.ajax({
        url: 'ajax/loginpost.jsp',
        type: "POST",
        //  dataType: "html", 
        cache: false,
        //  processData: false,f
        data: d
    });

    request.done(function(msg) {
        window.console && console.log('postback done ' + msg);
        $("#loginbutton").text(i18n_login);
        RefreshLoginPage();
    });

    request.fail(function(jqXHR, textStatus) {
        window.console && console.log('postback failed ');
        //TODO handle expired nonce values?
        RefreshLoginPage();
        $("#loginbutton").text(i18n_login);
        $("#loginfailuredetails").text("Login failed: " + textStatus + " " + jqXHR.responseText);
        $("#loginfailure").modal();
    });
    
}



function RefreshLoginPage()
{
    $.get('login.jsp', function(data) {
        $('#loginfield').html(data);
    });
}
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var myForm, fileSelector, progress;
var url = "/BlogWS2015/resources/files/upload";
var position;
var filesUploaded = [];

$(document).ready(function () {
    $("#map").hide();
})

window.onload = function () {
    myForm = document.querySelector("#myForm");
    fileSelector = document.querySelector("#fileSelector");
    progress = document.querySelector("#progress");

}
function sendForm() {
    console.log("in sendForm()");

    // SEND THE FORM USING AJAX

    var myForm = document.querySelector("#myForm");
    var fileSelector = document.querySelector("#fileSelector");

    // On remplit un objet FormData pour envoyer le formulaire
    // (y compris les fichiers attachés) en multipart
    var data = new FormData(myForm);

    var files = fileSelector.files;
    for (var i = 0; i < files.length; i++) {
        var name = "file";
        data.append(name, fileSelector.files[i]);
    }

    sendFormDataWithXhr2(url, data);

    return false;
}

function sendFormDataWithXhr2(url, data) {
    // ajax request
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url); // With FormData,
    // POST is mandatory

    xhr.onload = function () {
        $(".uploadedImg").remove();
        
        console.log('Upload complete !');

    };

    xhr.onerror = function () {
        console.log("erreur lors de l'envoi");
    }

    xhr.upload.onprogress = function (e) {
        progress.value = e.loaded; // number of bytes uploaded
        progress.max = e.total;    // total number of bytes in the file
    };

    // send the request
    xhr.send(data);
}

function traiterFichier(evt) {

    var files = event.target.files;

    for (var i = 0, f; f = files[i]; i++) {

        var reader = new FileReader();
        reader.onload = (function (file) {
            return function (e) {
                if ($.inArray(e.target.result, filesUploaded) == -1) {
                    $("<img class=\"uploadedImg\"src=\"" + e.target.result + "\"/>").insertAfter("#media-list");
                    filesUploaded.push(e.target.result);
                    console.log("Le fichier vient d'être ajouté");
                } else {
                    console.log("Le fichier a déjà  été choisi");
                }
            }
        })
                (f);

        reader.readAsDataURL(f);
    }
}
;

function geoloc() {
    console.log("Géolocalisation en cours...");
    navigator.geolocation.getCurrentPosition(showMap);
}

function showMap(position) {

    console.log("Affichage de la carte");
    var myLatLng = {lat: position.coords.latitude, lng: position.coords.longitude};

    var map = new google.maps.Map(document.getElementById('map'), {
        center: myLatLng,
        scrollwheel: false,
        zoom: 15
    });

    var marker = new google.maps.Marker({
        map: map,
        position: myLatLng,
        title: 'Ma position'
    });

    $("#map").show();
}
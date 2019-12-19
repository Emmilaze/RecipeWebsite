$(document).ready(function () {
    $(window).scroll(function () {
        if ($(this).scrollTop() > 10) {
            $("#goTop").fadeIn(300);
        } else {
            $("#goTop").fadeOut(300);
        }
    });

    $("#goTop").on("click", function () {
        $('html, body').animate({scrollTop: 0}, 350)
    });

    if (document.getElementById('qrcode')) {
        var qrcode = new QRCode(document.getElementById("qrcode"), {
            width: 100,
            height: 100
        });
        qrcode.makeCode(window.location.href);
    }

    $(function () {
        $(".recipes").slice(0, 9).show();

        $("#loadMore").on('click', function (e) {
            e.preventDefault();
            $(".recipes:hidden").slice(0, 9).slideDown();
            if (!($(".recipes:hidden").length > 0)) {
                $("#loadMore").fadeOut();
            }
        });
    });

    $("#showAdvance").on("click", function () {
        $("#colapsed").toggleClass("expand");
    });

    $("#loginShow").on("click", function () {
        $("#login").toggleClass("display");
        $("#loginShow").toggleClass("showed");
        $("#navigation").removeClass("display");
        $("#menu").removeClass("showed");
        $("#searchMenu").removeClass("display");
        $("#searchBtn").removeClass("showed");
    });

    $("#show").on("click", function () {
        $("#login").removeClass("display");
        $("#loginShow").removeClass("showed");
        $("#navigation").toggleClass("display");
        $("#menu").toggleClass("showed");
        $("#searchMenu").removeClass("display");
        $("#searchBtn").removeClass("showed");
    });

    $("#searchBtn").on("click", function () {
        $("#searchMenu").toggleClass("display");
        $("#searchBtn").toggleClass("showed");
        $("#navigation").removeClass("display");
        $("#menu").removeClass("showed");
        $("#login").removeClass("display");
        $("#loginShow").removeClass("showed");
    });

    function filterFunction() {
        var input, filter, ul, li, span, i, check;
        input = document.getElementById("myFilter");
        filter = input.value.toUpperCase();
        div = document.getElementById("filter");
        span = div.getElementsByTagName("label");
        for (i = 0; i < span.length; i++) {
            txtValue = span[i].textContent || span[i].innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                span[i].style.display = "";
            } else {
                span[i].style.display = "none";
            }
        }
    }
});

function getCheckedBoxes() {
    if ($(".filter").hasClass("showing")) {
        $(".filter").toggleClass("showing");
    }
    var checkboxes = document.getElementsByName("ingr-item");
    var field = document.getElementById("Search");
    var div = document.getElementById("filter");
    var span = div.getElementsByTagName("span");
    var items = document.getElementsByName("ingr");
    var checkboxesChecked = [];
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            checkboxesChecked.push(span[i].innerText);
        }
    }
    console.log(checkboxesChecked);
    console.log(field.value);
    console.log(span.value);
    console.log(div.value);
}


function filterFunction() {
    var input, filter, ul, li, span, i, check;
    input = document.getElementById("myFilter");
    filter = input.value.toUpperCase();
    div = document.getElementById("filter");
    span = div.getElementsByTagName("label");
    for (i = 0; i < span.length; i++) {
        txtValue = span[i].textContent || span[i].innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            span[i].style.display = "";
        } else {
            span[i].style.display = "none";
        }
    }
}
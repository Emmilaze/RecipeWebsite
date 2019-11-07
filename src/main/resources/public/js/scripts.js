$(".show-nav").on("click",function(){
	$(".top-menu").toggleClass("show");
	$(".top-filter").removeClass("show");
});

$(".filtering").on("click",function(){
	$(".top-filter").toggleClass("show");
	$(".top-menu").removeClass("show");

});

$(function() {
    $(".dd-filters").on("click", function() {
        $(".smenu").removeClass("fullSize");  
        $(".smenu",this).addClass("fullSize");       
    });
});

$(function() {
    $(".target").on("click",function(){
	// $(".top-container").removeClass("top-height");
	$(".top-container").toggleClass("top-height"); 
	});
});


(function($) {
    var $window = $(window),
        $html = $('.increase');

    $window.resize(function resize(){
        if ($window.width() <= 850) {
        	$html.removeClass('col-md-8');
            return $html.addClass('col-md-12');
        }
        $html.removeClass('col-md-12');
        $html.addClass('col-md-8');
    }).trigger('resize');
})(jQuery);

 $(".txtb input").on("focus",function(){
        $(this).addClass("focus");
      });

      $(".txtb input").on("blur",function(){
        if($(this).val() == "")
        $(this).removeClass("focus");
      });


 $(".txtb input").on("focus",function(){
        $(this).addClass("focus");
      });

      $(".txtb input").on("blur",function(){
        if($(this).val() == "")
        $(this).removeClass("focus");
      });



function edit() {
  document.getElementById("values1").contentEditable = true;
  document.getElementById("values2").contentEditable = true;
  document.getElementById("values3").contentEditable = true;

  
}



$("#show-filter").on("click",function(){
  $(".filter").toggleClass("showing");
  $(".sort").removeClass("top-sort-showed");
});

$("#sort").on("click",function(){
  $(".sort").toggleClass("top-sort-showed");
  $(".filter").removeClass("showing");
});

$("#show-menu").on("click",function(){
  $(".top-menu-show").toggleClass("top-menu-showed");
});


$("#show-middle-menu").on("click",function(){
  $(".middle-menu").toggleClass("middle-menu-showed");
});

  



function filterFunction() {
  var input, filter, ul, li, span, i, check;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  div = document.getElementById("Dropdown");
  span = div.getElementsByTagName("span");
  check = div.getElementsByTagName("label");
  for (i = 0; i < span.length; i++) {
    txtValue = span[i].textContent || span[i].innerText;
    if (txtValue.toUpperCase().indexOf(filter) > -1) {
      span[i].style.display = "";
      check[i].style.display = "";

    } else {
      span[i].style.display = "none";
      check[i].style.display = "none";
    }
  }
}


function sortFunction() {
  var input, filter, ul, li, span, i, check;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  div = document.getElementById("Sort-dd");
  span = div.getElementsByTagName("span");
  check = div.getElementsByTagName("label");
  for (i = 0; i < span.length; i++) {
    txtValue = span[i].textContent || span[i].innerText;
    if (txtValue.toUpperCase().indexOf(filter) > -1) {
      span[i].style.display = "";
      check[i].style.display = "";

    } else {
      span[i].style.display = "none";
      check[i].style.display = "none";
    }
  }
}





function getCheckedBoxes() {
  if ($(".filter").hasClass("showing")){
    $(".filter").toggleClass("showing"); 
  }
  var checkboxes = document.getElementsByName("ingr");
  var field = document.getElementById("Search");
  var div = document.getElementById("Dropdown");
  var span = div.getElementsByTagName("span");
//  var items = document.getElementsByName("ingr-item");
  var checkboxesChecked = [];
  for (var i=0; i<checkboxes.length; i++) {
     if (checkboxes[i].checked == true) {
        checkboxesChecked.push(span[i].innerText);
     }
  }
  console.log(checkboxesChecked);
  console.log(field.value);

  if ( field.value == '' && checkboxesChecked.length == 0){
    alert("No data was inserted");
  }
}



function rate_sort(){
  choice.style.left = "0px";
};
function date_sort(){
  choice.style.left = "73px";
};

$("#Date").prop("checked", true);
$("#DESC").prop("checked", true);


$("#Rating").on("click",function(){
  $("#Rating").prop("checked", true);
  $("#Date").prop("checked", false);
});

$("#Date").on("click",function(){
  $("#Date").prop("checked", true);
  $("#Rating").prop("checked", false);
});

$("#ASC").on("click",function(){
  $("#ASC").prop("checked", true);
  $("#DESC").prop("checked", false);
});

$("#DESC").on("click",function(){
  $("#DESC").prop("checked", true);
  $("#ASC").prop("checked", false);
});

function getSort(){
  var sort = [];
  if ($("#Rating").prop("checked"))
  {
    sort.push('order by rating');
  }
  if( $("#Date").prop("checked"))
  {
    sort.push('order by publicationDate');
  }
  if( $("#ASC").prop("checked"))
  {
    sort.push('ASC');
  }
  if ($("#DESC").prop("checked"))
  {
    sort.push('DESC');
  }
  console.log(sort);
}


var x = document.getElementById("register");
var y = document.getElementById("login");
var z = document.getElementById("button");
var log = document.getElementById("log");
var reg = document.getElementById("reg");

if (log) {log.style.color = "white";
reg.style.color = "black";};



function register(){
	x.style.left = "0px";
	y.style.left = "-400px";
	z.style.left = "95px";
  log.style.color = "black";
  reg.style.color = "white";

}
function login(){
	z.style.left = "0px";
	x.style.left = "400px";
	y.style.left = "0px";
  log.style.color = "white";
  reg.style.color = "black";
}




if($("#loop .items").length){
var nums = $("#loop .items").length;
var limitPerPage = 9;

var pages = Math.ceil( nums/limitPerPage);

$("#loop .items:gt(" + (limitPerPage - 1 ) + ")" ).hide();

$('.pagination').append("<li class='page-item' id = 'Prev' ><a class='page-link'  href='javascript:void(0)'>"+ ' <i class="fas fa-arrow-left"></i>' +"</a></li>");
$('.pagination').append("<li class ='active-num current-page page-item'><a class='page-link' href='javascript:void(0)'>"+ 1 +"</a></li>");
for (var i = 2 ; i <= pages;  i++) {
  $('.pagination').append("<li class ='current-page page-item'><a class='page-link' href='javascript:void(0)'>"+ i +"</a></li>");
}
$('.pagination').append("<li class='page-item' id='Next'><a class='page-link' href='javascript:void(0)'>"+ '<i class="fas fa-arrow-right"></i>' +"</a></li>");

$('.pagination li.current-page').on("click",function(){
  if($(this).hasClass("active-num"))
  {
    return false;
  }
  else{
    var currentPage = $(this).index();
    $(".pagination li").removeClass("active-num");
    $(this).addClass("active-num");
    $("#loop .items").hide();
    $("html, body").animate({ scrollTop: 0 }, "slow");

    var grandTotal = limitPerPage*currentPage;
    for (var i = grandTotal - limitPerPage; i < grandTotal; i++) {
      $("#loop .items:eq(" + i+ ")" ).show();
    }
  }
});
};



$("#Next").on("click",function(){
  var currentPage = $(".pagination li.active-num").index();
  if (currentPage === pages) {
    return false;
  }
  else{
    currentPage++;
    $(".pagination li").removeClass("active-num");
    $("#loop .items").hide();
    var grandTotal = limitPerPage*currentPage;
    $("html, body").animate({ scrollTop: 0 }, "slow");
    for (var i = grandTotal - limitPerPage; i < grandTotal; i++) {
      $("#loop .items:eq(" + i + ")" ).show();
    }
    $(".pagination li.current-page:eq("+ (currentPage - 1 )+")").addClass("active-num");
  }
});

$("#Prev").on("click",function(){
  var currentPage = $(".pagination li.active-num").index();
  if (currentPage === 1) {
    return false;
  }
  else{
    currentPage--;
    $(".pagination li").removeClass("active-num");
    $("#loop .items").hide();
    $("html, body").animate({ scrollTop: 0 }, "slow");
    var grandTotal = limitPerPage*currentPage;
    for (var i = grandTotal - limitPerPage; i < grandTotal; i++) {
      $("#loop .items:eq(" + i + ")" ).show();
    }
    $(".pagination li.current-page:eq("+ (currentPage - 1 )+")").addClass("active-num");
  }
});


function addIngr(){
  var len = $("#ingr-cont .ingr").length;
  // console.log(len);
  $('#ingr-cont').append('<li class="ingr"><input type="text" class="ingr-add" name = "add-ingr" required><i class="far fa-plus-square" onclick="addIngr()"></i><i class="far fa-minus-square"></i></li>');
};


$("body").on("click",".fa-minus-square",function(e){
  var val = $("#ingr-cont .ingr").length;
  console.log(val);
  if( val > 1){
       $(this).parents('.ingr').remove();
      }
  });


// console.log($('.time-input').value);

if ($('.time-input').value) {
  Date.now = function now() {
    return new Date().getTime();
  };
}


if($('#recipe').innerHTML === '' ){
 $('#submit').attr("disabled", true);
}


window.onload = function(e){ 
  //perform a for loop to add the event handler
  Array.from(document.getElementsByClassName("time-input")).forEach(
    function(element, index, array) {
      //Add the event handler to the time input
      element.addEventListener("blur", inputTimeBlurEvent);
    }
  );
}

inputTimeBlurEvent = function(e){
  var newTime = "";
  var timeValue = e.target.value;
  var numbers = [];
  var splitTime = [];
  
  if(timeValue.trim() == ""){
    e.target.value = "00:00";
    return;
  }
  
  var regex = /^[0-9.:]+$/;
  if( !regex.test(timeValue) ) {
    e.target.value = "00:00";
    return;
  }
  
  e.target.value = e.target.value.replace(".", ":").replace(/\./g,"");
  timeValue = e.target.value;
  
  if(timeValue.indexOf(".") == -1 && timeValue.indexOf(":") == -1){
    if(timeValue.trim().length > 4){
      timeValue = timeValue.substring(0,4);
    }
    var inputTimeLength = timeValue.trim().length;
    numbers = timeValue.split('');
    switch(inputTimeLength){
      case 2:
        if(parseInt(timeValue) <= 0){
          e.target.value = "00:00";
        }else if(parseInt(timeValue) >= 24){
          e.target.value = "00:00";
        }else{
          e.target.value = timeValue + ":00";
        }
        break;
      case 3:
        newTime = "0" + numbers[0] + ":";
        if(parseInt(numbers[1] + numbers[2]) > 59){
          newTime += "00";
        }else{
          newTime += numbers[1] + numbers[2];
        }
        e.target.value = newTime;
        break;
      case 4:
        if(parseInt(numbers[0] + numbers[1]) >= 24){
          newTime = "23:";
        }else{
          newTime = numbers[0] + numbers[1] + ":";
        }
        if(parseInt(numbers[2] + numbers[3]) > 59){
          newTime += "59";
        }else{
          newTime += numbers[2] + numbers[3];
        }
        e.target.value = newTime;
        break;
    }
    return;
  }
  
  //5th condition: if double dot found
  var doubleDotIndex = timeValue.indexOf(":");
  //if user doesnt enter the first part of hours example => :35
  if(doubleDotIndex == 0){
    newTime = "00:";
    splitTime = timeValue.split(':');
    numbers = splitTime[1].split('');
    if(parseInt(numbers[0] + numbers[1]) > 59){
      newTime += "00";
    }else{
      newTime += numbers[0] + numbers[1];
    }
    e.target.value = newTime;
    return;
  }else{
    //if user enter not full time example=> 9:3
    splitTime = timeValue.split(':');
    var partTime1 = splitTime[0].split('');
    if(partTime1.length == 1){
      newTime = "0" + partTime1[0] + ":";
    }else{
      if(parseInt(partTime1[0] + partTime1[1]) > 23){
        newTime = "00:";
      }else{
        newTime = partTime1[0] + partTime1[1] + ":";
      }
    }
    
    var partTime2 = splitTime[1].split('');
    if(partTime2.length == 1){
      newTime += "0" + partTime2[0];
    }else{
      if(parseInt(partTime2[0] + partTime2[1]) > 59){
        newTime += "00";
      }else{
        newTime += partTime2[0] + partTime2[1];
      }
    }
    e.target.value = newTime;
    return;
  }
}




function unBlock(but){
  console.log(but.parentElement.parentElement.childNodes[0].innerHTML);
};

function unBlock(but){
  console.log(but.parentElement.parentElement.childNodes[0].innerHTML);
};

if (document.getElementById('datepicker')) {
  var date = new Date();
  date.setDate(date.getDate());
    $('#datepicker').datepicker({
        startDate: date,
        weekStart: 0,
        daysOfWeekHighlighted: "6,0",
        autoclose: true,
        todayHighlight: true,
    });
    $('#datepicker').datepicker("setDate", new Date());

    function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#blah')
                        .attr('src', e.target.result)
                        .width(150)
                        .height(200);
                };

                reader.readAsDataURL(input.files[0]);
            }
        }
        function previewFile() {
  var preview = document.querySelector('#rec-img');
  var file = document.querySelector('input[type=file]').files[0];
  var reader = new FileReader();

  reader.addEventListener("load", function() {
    console.log('before preview');
    preview.src = reader.result;
    console.log('after preview');
  }, false);

  if (file) {
    console.log('inside if');
    reader.readAsDataURL(file);
  } else {
    console.log('inside else');
  }

}
}




var p = document.querySelector(".js-overflow");

if (p.scrollWidth > p.offsetWidth) p.classList.add("has-overflow");

while (p.scrollWidth > p.offsetWidth) {
  p.innerHTML = p.innerHTML.slice(0, -1);
}
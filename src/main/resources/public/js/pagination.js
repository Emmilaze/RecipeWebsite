(function($){


    $.fn.customPaginate = function(options){

      var paginationContaier = this;
      var itemsToPaginate;
      
      var defaults = {

        itemsPerPage : 5
      };



      var settings = {};

      $.extend(settings,defaults, options);

      itemsToPaginate = $(settings.itemsToPaginate);

      $("<ul></ul>").prependTo(paginationContaier);

    };

}(jQuery));
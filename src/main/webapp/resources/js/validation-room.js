
$(document).ready(function() {
    $(function() {
       $.validator.addMethod("regexName", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "Invalid name.");
       $.validator.addMethod("regexAddress", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "Invalid address.");
       $.validator.addMethod("regexCity", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "Invalid city.");
       $.validator.addMethod("regexPhone", function(value, element, regexpr) {
          return regexpr.test(value);
       }, "Invalid phone number.");
        $('#roomForm').validate({
            rules:{
                name: {
                    required: true,
                    regexName: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя0-9\s]+$/
                },
                address:{
                    required: true,
                    regexAddress: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя0-9\s]+$/
                },
                city: {
                    required: true,
                    regexCity: /^[a-zA-ZАаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя]+$/
                },
                phoneNumber:{
                    required: true,
                    regexPhone: /[0-9]{7,14}/
                },
                capacity: {
                    min: 1,
                    max: 200,
                    required: true
                },
                workingHoursStart:{
                    required: true
                },
                workingHoursEnd:{
                    required: true
                }
            }
        });
    });
});
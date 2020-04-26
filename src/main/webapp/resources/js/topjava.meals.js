//$(document).ready(function () {
$(function () {
    // alert("ready");
    makeEditable({
            ajaxUrl: "ajax/profile/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            })
        }
    );

    var t1 = document.getElementById("endDate").value;
    var t2 = document.getElementById("startDate").value;
    var t3 = document.getElementById("startTime").value;
    var t4 = document.getElementById("endTime").value;
    if ((t1 != "") || (t2 != "") || (t3 != "") || (t4 != "")) {
        filter();
    }


});

function filter() {
    $.ajax({
        type: "GET",
        url: context.ajaxUrl + "filter",
        data: $('#filterForm').serialize()
    }).done(function (data) {
       // alert("done" );
        update(data);
        successNoty("done");
    }).fail(function (jqXHR, exception) {
        // Our error logic here
        var msg = '';
        if (jqXHR.status === 0) {
            msg = 'Not connect.\n Verify Network.';
        } else if (jqXHR.status === 404) {
            msg = 'Requested page not found. [404]';
        } else if (jqXHR.status === 500) {
            msg = 'Internal Server Error [500].';
        } else if (exception === 'parsererror') {
            msg = 'Requested JSON parse failed.';
        } else if (exception === 'timeout') {
            msg = 'Time out error.';
        } else if (exception === 'abort') {
            msg = 'Ajax request aborted.';
        } else {
            msg = 'Uncaught Error.\n' + jqXHR.responseText;
        }
        alert("msg=" + msg);
    })
     ;
}

function clearFilter() {
    $('#endTime').prop('disabled', true).val('00:00');
    $('#startTime').prop('disabled', true).val('00:00');
    $('#endDate').prop('disabled', true).val('00:00');
    $('#startDate').prop('disabled', true).val('00:00');
}
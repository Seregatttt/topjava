let ajaxUrl = "ajax/profile/meals/";  //"ajax/admin/users/";  by analogy users for easy merge !!!

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

function paintRowByExcess(row, data, dataIndex) {
    $(row).attr("data-mealExcess", data.excess);
}

$.ajaxSetup({
    converters: {
        "text json": function (result) {
            // https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/JSON/parse
            let json = JSON.parse(result, function (k, v) {
                if (k === 'dateTime') {
                    var data3 = v.toString().replace('T', ' ').substr(0, 16);
                    return data3;
                }
                return v;
            });
            return json;
        }
    }
});

$(function () {
    $('#datetimepicker').datetimepicker({
        format: 'Y-m-d H:i'
    });

    $('[type="date"]').datetimepicker({
        timepicker: false,
        format: 'Y-m-d'
    });

    $('[type="time"]').datetimepicker({
        datepicker: false,
        format: 'H:i'
    });

    makeEditable({
        ajaxUrl: ajaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": ajaxUrl,
                "dataSrc": ""
            },
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
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": paintRowByExcess
        }),
        updateTable: updateFilteredTable
    });
});
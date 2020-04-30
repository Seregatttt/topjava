let AjaxUrl = "ajax/profile/meals/";  //"ajax/admin/users/";  by analogy users for easy merge !!!

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: AjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(AjaxUrl, updateTableByData);
}

function paintRowByExcess(row, data, dataIndex) {
    $(row).attr("data-mealExcess", data.excess);
}

$(function () {
    $('#datetimepicker').datetimepicker({
        format: 'Y-m-d H:i'
    });

    makeEditable({
        ajaxUrl: AjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": AjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": renderDataTime
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
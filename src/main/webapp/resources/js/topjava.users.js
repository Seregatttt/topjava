// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
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
                        "asc"
                    ]
                ]
            })
        }
    );


});

function clickCheckBox(userId, isEnabled) {
    var checked = $("#chk"+userId).is(':checked');
    $.ajax({
        url: context.ajaxUrl + "changeEnabled/" + userId + "/" + isEnabled,
        type: "GET"
    }).done(function () {
        //updateTable();
        if (checked) {
            $("#chk"+userId).attr("checked", false);
        } else {
            $("#chk"+userId).attr("checked", true);
        };
        successNoty("done");
    });
}
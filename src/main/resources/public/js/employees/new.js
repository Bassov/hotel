$(document).ready(function() {
    $("#staffOption").click(function () {
        $("#usersForm").addClass('hidden');
    });

    $("#admOption").click(function () {
        $("#usersForm").removeClass('hidden');
    });

    $("#managerOption").click(function () {
        $("#usersForm").removeClass('hidden');
    });
});
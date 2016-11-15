$(document).ready(function() {
    $("#staffOption").click(function () {
        $("#usersForm").addClass('hidden');
    });

    $("#admOption").click(function () {
        $("#usersForm").removeClass('hidden');
    });

    $("#ownerOption").click(function () {
        $("#usersForm").removeClass('hidden');
    });
});
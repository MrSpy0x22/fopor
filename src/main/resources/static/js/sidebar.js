const hide_sb_key_name = "hide_sidebar";

let hide_sb_value = readStorage(false);
let sidebar = $("#sidebar");

function readStorage(update = false) {
    let storage = window.localStorage.getItem(hide_sb_key_name) === "true";

    if (update) {
        storage = !storage;
        window.localStorage.setItem(hide_sb_key_name , storage ? "true" : "false");
    }

    return storage;
}

$(document).ready(() => hide_sb_value ? $(sidebar).hide() : $(sidebar).show());

$("#menu-button").click(() => {
    hide_sb_value = readStorage(true);

    if (hide_sb_value) {
        $(sidebar).hide();
    } else {
        $(sidebar).show();
    }
});
/**
 * Pokazuje powiadomienie.
 * @param text Treść powiadomienia.
 * @param title Tytuł powiadomienia.
 * @param cls_accent Klasa akcentu (zalecane wartości: "primary", "secondary", "success", "alert", "warning", "yellow", "info", "dark").
 */
function showMetro4Notification(text , title , cls_accent) {
    if (typeof cls_accent == "string") {
        let notify = Metro.notify;
        if (notify !== undefined) {
            notify.create(text , title , { cls: cls_accent });
        }
    } else {
        throw new TypeError(`'cls_accent' musi być typu 'string', a nie '${typeof cls_accent}'`);
    }
}

function notifyUpdate(text , title="Powiadomienie") { showMetro4Notification(text , title , ""); }
function notifyInfo(text , title="Informacja") { showMetro4Notification(text , title , "primary"); }
function notifySuccess(text , title="Sukces") { showMetro4Notification(text , title , "success"); }
function notifyWarning(text , title="Ostrzeżenie") { showMetro4Notification(text , title , "warning"); }
function notifyError(text , title="Błąd") { showMetro4Notification(text , title , "alert"); }

/**
 * Pokazyje informację na środku dokumentu.
 * @param text Treść powiadomienia.
 * @param title Tytuł powiadomienia (większy tekst).
 * @param cls_accent Klasa akcentu (zalecane wartości: "primary", "secondary", "success", "alert", "warning", "yellow", "info", "dark").
 * @throws TypeError Jeżeli któryś z parametrów nie jest typu 'string'.
 */
function showInformationModal(text , title , cls_accent) {
    if (typeof text == "string" || typeof text == "string" || typeof cls_accent == "string") {
        Metro.infobox.create(`<h3>${title}</h3><p>${text}</p>` , cls_accent);
    } else {
        throw new TypeError(`Niezgodność typów (oczekiwano: "string", "string" , "string"; otrzymano ${typeof text} , ${typeof title} , ${typeof cls_accent}).`);
    }
}

function modalUpdate(text , title="Powiadomienie") { showInformationModal(text , title , ""); }
function modalInfo(text , title="Informacja") { showInformationModal(text , title , "primary"); }
function modalSuccess(text , title="Sukces") { showInformationModal(text , title , "success"); }
function modalWarning(text , title="Ostrzeżenie") { showInformationModal(text , title , "warning"); }
function modalError(text , title="Błąd") { showInformationModal(text , title , "alert"); }
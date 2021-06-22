// Przycisk w dokumencie musi mieć poniższe Id
const sortingKey = "filter_sorting";
const pageSizeKey = "filter_page_size";

// Odczytywanie zapisanych wartości
var filterOrder = window.localStorage.getItem(sortingKey);
var filterPageSize = window.localStorage.getItem(pageSizeKey);

// Sprawdzanie poprawności odczytanych wartości
if (filterOrder == null) {
    filterOrder = true;
}
if (filterPageSize == null) {
    filterPageSize = 10;
}

const filterButton = $("#btn-filter");

if (filterButton !== undefined) {
    $(filterButton).click(() => {
        Metro.dialog.create({
            title: "Dostosuj widok",
            content: `
                <div class="row mb-2">
                    <label class="cell-md-6">Rozmiar strony</label>
                    <div class="cell-md-6">
                        <input id="fltr_pagesize" data-role="spinner" data-step="10" value="${filterPageSize}">
                    </div>
                </div>
                <div class="row mb-2">
                    <label class="cell-md-6">Porządek</label>
                    <div class="cell-md-6">
                        <input id="fltr_order" type="checkbox" data-role="checkbox" data-caption="Roznący" ${filterOrder ? "checked" : ""}>
                    </div>
                </div>`,
            cls: "dark",
            closeButton: true ,
            actions: [
                {
                    caption: "Filtruj",
                    cls: "js-dialog-close dark",
                    onclick: function() {
                        filterOrder = $("#fltr_order").is(":checked");
                        filterPageSize = parseInt($("#fltr_pagesize").val());

                        window.localStorage.setItem(sortingKey , filterOrder);
                        window.localStorage.setItem(pageSizeKey , filterPageSize);
                    }
                },
                {
                    caption: "Zamknij",
                    cls: "js-dialog-close"
                }
            ]
        });
    });
}
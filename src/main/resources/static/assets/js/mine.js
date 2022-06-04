function showSearchForm() {

    const searchForm = document.getElementById("mine-searchFrm");

    if (searchForm.style.display === "block") {
        searchForm.style.display = "none";
    } else {
        searchForm.style.display = "block";
    }
}
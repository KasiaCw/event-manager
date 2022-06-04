function showSearchForm() {

    const searchForm = document.getElementById("mine-searchFrm");

    if (searchForm.style.display === "none") {
        searchForm.style.display = "block";
    } else {
        searchForm.style.display = "none";
    }
}
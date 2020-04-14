let movieDb;
let content = document.getElementsByClassName('content')[0];
let inputToSearch = decodeURI(window.location.search.slice(1));

function fetchDataFromLocalStorage() {
    movieDb = readOnServiceDb();
}

function searchProject(data) {
    let inputValue = document.getElementById("search-movie").value || data;
    inputValue = (inputValue === undefined) ? ' ' : inputValue;
    content.innerHTML = `<h2>搜索：${inputValue}</h2>`;
    let searchMovieId = itemSearch(movieDb, inputValue);
    let searchMovie = movieSearch(searchMovieId);
    if (inputValue && searchMovie.length !== 0) {
        searchMovie.forEach(item => {
            content.innerHTML += `<div class="searchResult">
            <div class="picAndDetail">
            <a href="../pages/detailPage.html?${item.id}" target="_blank"><img src="${item.images.small}" /></a>
            <div class="movie-details">
            <p>${item.title}</p>
            <p>导演：${item.directors.map(item => item.name).join(',')}</p>
            <p>主演：${item.casts.map(item => item.name).join(',')}</p>
            <p>类型：${item.genres.join(',')}</p>
            <p>评分：${item.rating.average}</p>
      </div></div></div>`;
        });
    } else {
        content.innerHTML += `<div class="searchResult">没有找到关于"${inputValue}"的电影，换个搜索词试试吧~</div>`;
    }
}

function keyEnterSearchProject(event) {
    if (13 == event.keyCode) {
        searchProject();
    }
}

function movieSearch(searchMovieId) {
    return movieDb.filter(movie => (searchMovieId.indexOf(movie.id) !== -1));
}

initDb(fetchDataFromLocalStorage);
searchProject(inputToSearch);
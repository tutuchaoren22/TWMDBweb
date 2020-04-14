let content = document.getElementsByClassName('content')[0];
let inputToSearch = decodeURI(window.location.search.slice(1));

searchProject(inputToSearch);

function searchProject(data) {
  let inputValue = document.getElementById("search-movie").value || data;
  inputValue = (inputValue === undefined) ? '' : inputValue;
  if (0 !== inputValue.length) {
    content.innerHTML = `<h2>搜索：${inputValue}</h2>`;
    // let searchMovieId = itemSearch(movieDb, inputValue);
    getMoviesByName(inputValue).then(searchResult => {
      if (searchResult.length !== 0) {
        searchResult.forEach(item => {
          content.innerHTML += `<div class="searchResult">
            <div class="picAndDetail">
            <a href="../pages/detailPage.html?${item.movieId}" target="_blank"><img src="${item.image}" /></a>
            <div class="movie-details">
            <p>${item.title}</p>
            <p>导演：${item.directors}</p>
            <p>主演：${item.casts}</p>
            <p>类型：${item.genres}</p>
            <p>评分：${item.rating}</p>
      </div></div></div>`;
        });
      } else {
        content.innerHTML += `<div class="searchResult">没有找到关于"${inputValue}"的电影，换个搜索词试试吧~</div>`;
      }
    });
  }
}

function keyEnterSearchProject(event) {
  if (13 == event.keyCode) {
    searchProject();
  }
}

function getMoviesByName(name) {
  return new Promise(function (resolve, reject) {
    let AJAXSetup = {
      url: 'http://localhost:8080/api/search?text=' + name,
      method: 'GET',
      success: function (result) {
        resolve(result);
      }
    };
    AJAXHandle(AJAXSetup);
  });
}
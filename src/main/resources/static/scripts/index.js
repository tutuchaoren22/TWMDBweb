let dataLoadedFlag = false;
let movieListToRender;
let currentCategory = 'all';
let movieCollectProgressIndex = 1;
let movieRenderProgressIndex = 1;
const movieRenderInterval = 20;
const movieCollectInitLength = 40;
let pastHighlightCatagoryBox;

run();

function run() {
  getCatagories().then(result => renderAllCategroys(result));
  getAllMoviesBetween(movieCollectProgressIndex, (movieCollectProgressIndex += movieCollectInitLength) - 1).then(movieList => {
    movieListToRender = movieList;
    renderMovieListInInterval(movieListToRender, movieRenderProgressIndex, (movieRenderProgressIndex += movieRenderInterval) - 1);
    dataLoadedFlag = true;
  });
}

function onInterfaceClick(event) {
  const catagoryBox = 'catagory-box';
  const bannerImg = 'banner-img';
  let clickClass = event.target.getAttribute('class') || event.target.parentElement.getAttribute('class');

  switch (clickClass) {
    case catagoryBox:
      selectCatagoryHandle(findCatagoryBox(event.target));
      break;
    case bannerImg:
      onClickBannerImg(event.target);
      break;
  }
}

function highlightCatagoryBox(target) {
  toggleHighlightCatagoryBox(target);
  if (pastHighlightCatagoryBox) {
    toggleHighlightCatagoryBox(pastHighlightCatagoryBox);
  }
  pastHighlightCatagoryBox = target;
}

function toggleHighlightCatagoryBox(target) {
  if ('grey' === target.style.backgroundColor) {
    target.style.backgroundColor = null;
    target.style.color = null;
  } else {
    target.style.backgroundColor = 'grey';
    target.style.color = 'black';
  }
}

function findCatagoryBox(target) {
  let targetEl;
  if (target.getAttribute('class')) {
    targetEl = target;
  } else {
    targetEl = target.parentElement;
  }
  return targetEl;
}

function selectCatagoryHandle(catagoryBoxEl) {
  currentCategory = catagoryBoxEl.firstElementChild.textContent;
  highlightCatagoryBox(catagoryBoxEl);
  removeMovies();
  movieRenderProgressIndex = 1;
  movieCollectProgressIndex = 1;
  getMoviesOfCategory(currentCategory, movieCollectProgressIndex, (movieCollectProgressIndex + movieCollectInitLength) - 1).then(result => {
    movieListToRender = result;
    renderMovieListInInterval(movieListToRender, movieRenderProgressIndex, (movieRenderProgressIndex += movieRenderInterval) - 1);
  });
}

function sortCatagoryByMovieCount(catagoryObjList) {
  return catagoryObjList.sort(function (a, b) {
    return b.count - a.count;
  });
}

function onClickBannerImg(target) {
  let movieId = target.getAttribute("douban-id");
  const imgHrefPrefix = "../pages/detailPage.html?";
  window.open(imgHrefPrefix + movieId, '_blank');
}

function removeMovies() {
  let movieGallaryEl = document.getElementsByClassName("movie-gallary")[0];
  while (movieGallaryEl.firstChild) {
    movieGallaryEl.removeChild(movieGallaryEl.firstChild);
  }
}

function renderMovieListInInterval(movieList, start, end) {
  let renderStart = start || 1;
  let renderEnd = end || movieList.length;
  let movieGallaryEl = document.getElementsByClassName("movie-gallary")[0];
  movieList.slice(renderStart - 1, renderEnd).forEach(movie => {
    movieGallaryEl.appendChild(renderSingleMovie(movie));
  })
}

function renderSingleMovie(movieObj) {
  let movieEl = document.createElement('div');
  movieEl.innerHTML = `
  <div class="movie-box">
    <a href="../pages/detailPage.html?${movieObj.movieId}" target="_blank">
      <div class="img-wrap">
        <img src=${movieObj.image} alt="电影海报图">
      </div>
      <div class="movie-title">${movieObj.title}</div>
    </a>
    <div class="movie-year">(${movieObj.year})</div>
    <div>
      <span class="rating-star">★</span><span class="movie-rating">${movieObj.rating.toFixed(1)}</span>
    </div>
  </div>`;
  return movieEl;
}

function renderSingleCatagory(catagoryObj) {
  let catagoryEl = document.createElement('div');
  catagoryEl.setAttribute('class', 'catagory-box');
  catagoryEl.innerHTML = `
    <span>${catagoryObj.name}</span>
    <span>(${catagoryObj.count}部)</span>`;
  return catagoryEl;
}

function renderCatagorysFromList(catagoryObjList) {
  let catagoryListEl = document.getElementsByClassName("catagory-list")[0];
  catagoryObjList.forEach((catagoryObj) => {
    catagoryListEl.appendChild(renderSingleCatagory(catagoryObj));
  })
}

function renderAllCategroys(data) {
  renderCatagorysFromList(sortCatagoryByMovieCount(data));
}

function getDocumentTop() {
  let scrollTop = 0,
    bodyScrollTop = 0,
    documentScrollTop = 0;
  if (document.body) {
    bodyScrollTop = document.body.scrollTop;
  }
  if (document.documentElement) {
    documentScrollTop = document.documentElement.scrollTop;
  }
  scrollTop = (bodyScrollTop - documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;
  return scrollTop;
}

function getWindowHeight() {
  let windowHeight = 0;
  if (document.compatMode == "CSS1Compat") {
    windowHeight = document.documentElement.clientHeight;
  } else {
    windowHeight = document.body.clientHeight;
  }
  return windowHeight;
}

function getScrollHeight() {
  let scrollHeight = 0,
    bodyScrollHeight = 0,
    documentScrollHeight = 0;
  if (document.body) {
    bodyScrollHeight = document.body.scrollHeight;
  }

  if (document.documentElement) {
    documentScrollHeight = document.documentElement.scrollHeight;
  }
  scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
  return scrollHeight;
}


window.onscroll = function () {
  if (dataLoadedFlag) {
    if (getScrollHeight() < getWindowHeight() + getDocumentTop() + 15) {
      let loadmore = document.getElementsByClassName('loadmore')[0];
      loadmore.innerHTML = '<span class="loading"></span>加载中..';
      if (getScrollHeight() - 1 <= getWindowHeight() + getDocumentTop()) {
        loadmore.innerHTML = ' ';
        if ('all' == currentCategory) {
          console.log("here!");
          getAllMoviesBetween(movieCollectProgressIndex, (movieCollectProgressIndex += movieRenderInterval) - 1).then(movieList => {
            movieListToRender = movieListToRender.concat(movieList);
          });
        } else {
          getMoviesOfCategory(currentCategory, movieCollectProgressIndex, (movieCollectProgressIndex += movieRenderInterval) - 1).then(movieList => {
            movieListToRender = movieListToRender.concat(movieList);
          });
        }
        renderMovieListInInterval(movieListToRender, movieRenderProgressIndex, (movieRenderProgressIndex += movieRenderInterval) - 1);
      }
    }
  }
};

function keyEnterSearchProject() {
  if (13 == event.keyCode) {
    searchProject();
  }
}

function searchProject() {
  let inputValue = document.getElementById("search-movie").value;
  if(null !== inputValue && inputValue.length)
  window.open(`../pages/searchPage.html?${inputValue}`, '_blank');
}

function getCatagories() {
  return new Promise(function (resolve, reject) {
    let AJAXSetup = {
      url: 'http://localhost:8080/api/allcategories',
      method: 'GET',
      success: function (result) {
        resolve(result);
      }
    };
    AJAXHandle(AJAXSetup);
  });
}

function getAllMoviesBetween(start, end) {
  return new Promise(function (resolve, reject) {
    let AJAXSetup = {
      url: 'http://localhost:8080/api/all?start=' + start + '&end=' + end,
      method: 'GET',
      success: function (result) {
        resolve(result);
      }
    };
    AJAXHandle(AJAXSetup);
  });
}

function getMoviesOfCategory(category, start, end) {
  return new Promise(function (resolve, reject) {
    let AJAXSetup = {
      url: 'http://localhost:8080/api/category?category=' + category + '&start=' + start + '&end=' + end,
      method: 'GET',
      success: function (result) {
        resolve(result);
      }
    };
    AJAXHandle(AJAXSetup);
  });
}
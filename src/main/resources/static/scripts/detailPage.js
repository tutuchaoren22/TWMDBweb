let movieId = window.location.search.slice(1);
let movieGenres;
let movieInformations = document.getElementsByClassName('movie-informations')[0];
let movieSummarys = document.getElementsByClassName('movie-summary')[0];
let movieComments = document.getElementsByClassName('movie-comments')[0];
let movieRecommend = document.getElementsByClassName('recommend-information')[0];

run();

function run() {
  getMovieData().then(result => {
    loadMovieDetail(result);
    getMovieRecommends();
  });
  getMovieReviews().then(result => {
    loadMovieReviews(result);
  });
}

function getMovieData() {
  return new Promise(function (resolve, reject) {
    let options = {
      url: 'http://localhost:8080/api/movie?movieId=' + movieId,
      method: 'GET',
      success: function (res) {
        resolve(res);
      },
      fail: function (error) {
        console.log('ERROR');
      }
    };
    AJAXHandle(options);
  });
}

function loadMovieDetail(data) {
  let movieCountries = data.countries.replace(/\"|\[|\]/g,'');
  let movieDuration = data.durations.replace(/\"|\[|\]/g,'');
  movieInformations.innerHTML = `<h2>${data.title}——${data.originalTitle}</h2>
    <div class="picAndDetail">
    <img src="${data.image}" alt="电影海报图" />
    <div class="movie-details">
    <p>导演：${data.directors}</p>
    <p>主演：${data.casts}</p>
    <p>类型：${data.genres}</p>
    <p>制片国家/地区：${movieCountries}</p>
    <p>上映日期：${data.year}</p>
    <p>片长：${movieDuration}</p>
    <p>评分：${data.rating}</p></div></div>`;
  movieSummarys.innerHTML = `<h3>剧情简介</h3><p>${data.summary}</p>`;
  movieGenres = data.genres.split(',')[0];
}

function getMovieReviews() {
  return new Promise(function (resolve, reject) {
    let options = {
      url: 'http://localhost:8080/api/comments?movieId=' + movieId,
      method: 'GET',
      success: function (res) {
        resolve(res);
      },
      fail: function (error) {
        console.log('ERROR');
      }
    };
    AJAXHandle(options);
  });
}

function loadMovieReviews(data) {
  let comments = sortCommentsWithTime(data).slice(0, 5);
  movieComments.innerHTML += `<h3>电影热评</h3>
    <div class="user-comments"></div>`;
  let userComments = document.getElementsByClassName('user-comments')[0];
  comments.forEach(element => {
    userComments.innerHTML += `
      <div class='user-comment'>
      <span>${element.authorName}</span>
      <span>${ratingToStar(element.rating)}</span>
      <span> ${element.commentDate}</span>
      <p>${element.comments}</p>
      </div>`;
  });
}

function sortCommentsWithTime(comments) {
  return comments.sort(function (movieA, movieB) {
    let yearA = Number(movieA.commentDate.split(/[-: ]/).join(''));
    let yearB = Number(movieB.commentDate.split(/[-: ]/).join(''));
    return yearB - yearA;
  });
}

function ratingToStar(num) {
  return "★".repeat(num) + "☆".repeat(5 - num);
}

function loadMovieRecommend(data) {
  data.forEach(movieObj => {
    if (movieObj.movieId !== movieId) {
      let movieURL = `../pages/detailPage.html?${movieObj.movieId}`;
      movieRecommend.innerHTML += `
    <div class="one-movie-recommend">
      <a href=${movieURL}>
        <img src="${movieObj.image}" />
        <p>${movieObj.title}</p>
      </a>
        <p>评分：${movieObj.rating}</p>
    </div>`;
    }
  });

}


function getMovieRecommends() {
  getMoviesOfCategory(movieGenres, 1, 6).then(result => {
    loadMovieRecommend(result);
  });
}

function keyEnterSearchProject() {
  if (13 == event.keyCode) {
    searchProject();
  }
}

function searchProject() {
  let inputValue = document.getElementById("search-movie").value;
  window.open(`../pages/searchPage.html?${inputValue}`, '_blank');
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

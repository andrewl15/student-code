import axios from "axios";
/**
 * Get all books from the API
 * @returns {Promise} A promise that resolves with an array of book objects
 */
function getBooks() {
    return axios.get(`https://teapi.netlify.app/api/books/`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            const errorLabel = document.getElementById('error-label');
            if(error.response){
              errorLabel.textContent = `Response error of ${error.response.statusCode}`;
            } else if (error.request){
              errorLabel.textContent = `Request error. Server can't be reached.`
            } else {
              errorLabel.textContent = 'Something else when haywire.'
            }
          })
}

/**
 * Get a book by its ID
 * @param {number} id The ID of the book to retrieve
 * @returns {Promise} A promise that resolves with the book object
 */
function getBookById(id) {
    return axios.get(`https://teapi.netlify.app/api/books/${id}`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            const errorLabel = document.getElementById('error-label');
            if(error.response){
              errorLabel.textContent = `Response error of ${error.response.statusCode}`;
            } else if (error.request){
              errorLabel.textContent = `Request error. Server can't be reached.`
            } else {
              errorLabel.textContent = 'Something else when haywire.'
            }
          })
}

/**
 * Get all movies from the API
 * @returns {Promise} A promise that resolves with an array of movie objects
 */
function getMovies() {
    return axios.get(`https://teapi.netlify.app/api/movies/`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            const errorLabel = document.getElementById('error-label');
            if(error.response){
              errorLabel.textContent = `Response error of ${error.response.statusCode}`;
            } else if (error.request){
              errorLabel.textContent = `Request error. Server can't be reached.`
            } else {
              errorLabel.textContent = 'Something else when haywire.'
            }
          })
}

/**
 * Get a movie by its ID
 * @param {number} id The ID of the movie to retrieve
 * @returns {Promise} A promise that resolves with the movie object
 */
function getMovieById(id) {
    return axios.get(`https://teapi.netlify.app/api/movies/${id}`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            const errorLabel = document.getElementById('error-label');
            if(error.response){
              errorLabel.textContent = `Response error of ${error.response.statusCode}`;
            } else if (error.request){
              errorLabel.textContent = `Request error. Server can't be reached.`
            } else {
              errorLabel.textContent = 'Something else when haywire.'
            }
          })
}


// do not modify the following line
export { getBooks, getBookById, getMovies, getMovieById };
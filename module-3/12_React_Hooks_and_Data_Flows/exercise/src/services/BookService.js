import axios from 'axios';

/**
 * This service class is used to interact with the Books server API.
 * All methods return a Promise so that the calling code can handle both success and
 * error responses appropriately.
 *
 * The baseURL for the server API is set in the main.jsx file.
 */

export default {

  getBooks() {
    return axios.get('/books');
  },

  getAuthors() {
    return axios.get('/books/authors');
  }

};

import axios from 'axios';

/**
 * This service class is used to interact with the Favorite Movies server API.
 * All methods return a Promise so that the calling code can handle both success and
 * error responses appropriately.
 */
export default {
  getMovies() {
    return axios.get('/movies');
  },

  getMyLikes() {
    return axios.get('/movies/likes');
  },

  likeMovie(movieId) {
    return axios.put(`/movies/${movieId}/like`);
  },

  unlikeMovie(movieId) {
    return axios.delete(`/movies/${movieId}/like`);
  }
};

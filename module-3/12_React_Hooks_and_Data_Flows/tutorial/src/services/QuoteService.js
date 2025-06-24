/**
 * This service class contains methods to send requests to the "quote-server" backend API.
 * Example of quote object structure:
 *
 * {
 *   id: 6,
 *   text: "Understanding is a two-way street.",
 *   author: "Eleanor Roosevelt",
 *   userId: 3,
 *   userFavorite: false
 * }
 *
 */
import axios from 'axios';

export default {

  getRandomQuote() {
    return axios.get('/quote');
  },
};

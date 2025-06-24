import axios from 'axios';

/**
 * This service class is used to interact with the Quote Server API.
 * All methods return a Promise so that the calling code can handle both success and
 * error responses appropriately.
 */
export default {
  getUserProfile(userId) {
    return axios.get(`/users/${userId}`);
  },

};

import axios from 'axios';

const API_URL = 'http://localhost:8080/hotels';

export default {
  getHotels() {
    return axios.get(`${API_URL}`);
  }
};

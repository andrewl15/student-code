import axios from 'axios';

const API_URL = 'http://localhost:8080';

export default {
  login(user) {
    return axios.post(`${API_URL}/login`, user);
  }
};

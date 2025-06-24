import axios from 'axios';

const API_URL = 'http://localhost:8080/reservations';

export default {
  getReservations() {
    return axios.get(`${API_URL}`);
  },

  getReservationById(id) {
    return axios.get(`${API_URL}/${id}`);
  },

  createReservation(reservation) {
    return axios.post(`${API_URL}`, reservation);
  },

  updateReservation(id, reservation) {
    return axios.put(`${API_URL}/${id}`, reservation);
  },

  deleteReservation(id) {
    return axios.delete(`${API_URL}/${id}`);
  }
};

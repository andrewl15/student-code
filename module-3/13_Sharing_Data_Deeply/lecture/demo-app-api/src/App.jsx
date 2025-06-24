import { useState, useEffect } from 'react';
import ReservationList from './components/ReservationList/ReservationList';
import ReservationService from './services/ReservationService';
import AddReservation from './components/AddReservation/AddReservation';
import EditReservation from './components/EditReservation/EditReservation';

export default function App() {
  const [reservations, setReservations] = useState([]);
  const [editReservationId, setEditReservationId] = useState(0);

  function getReservations() {
    ReservationService.getReservations()
      .then((response) => {
        setReservations(response.data);
      })
      .catch((error) => {
        const message = error.response?.message || error.message;
        console.log('Error retrieving reservations:', message);
      });
  }

  useEffect(() => {
    getReservations();
  }, []);

  function handleAddSubmit() {
    getReservations();
  }

  function handleEdit(reservationId) {
    setEditReservationId(reservationId);
  }

  function handleEditSubmit() {
    // TODO: set reservationId to 0
    // TODO: Refresh list of reservations
    setEditReservationId(0);
    getReservations();
  }

  function handleDelete() {
    // TODO: Refresh list of reservations
    getReservations();
  }

  return (
    <div>
      <h1>Hotel Reservation System</h1>

      <ReservationList reservations={reservations} onEdit={handleEdit} onDelete={handleDelete} />
      <hr />
      {editReservationId ? (
        <EditReservation onSubmit={handleEditSubmit} reservationId={editReservationId} />
      ) : (
        <AddReservation onSubmit={handleAddSubmit} />
      )}
    </div>
  );
}

import { useState } from 'react';
import ReservationForm from '../ReservationForm/ReservationForm';
import ReservationService from '../../services/ReservationService';

export default function AddReservation({ onSubmit }) {
  const [reservation, setReservation] = useState(null);

  function handleReservationSubmit(event) {
    //TODO: Use axios to send POST that will insert a new reservation record
    event.preventDefault();
    ReservationService.createReservation(reservation)
      .then(
        (response) => {
          if (response.status === 201) {
            alert('Reservation added');
            onSubmit();
          }
        }
      ).catch((error) => {
        const message = error.response?.message || error.message;
        console.log('Error creating reservation:', message);
      });
  }

  return (
    <div>
      <h2>Add Reservation</h2>
      <ReservationForm
        reservation={reservation}
        setReservation={setReservation}
        onReservationSubmit={handleReservationSubmit}
      />
    </div>
  );
}

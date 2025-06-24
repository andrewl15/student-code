import { useEffect, useState } from 'react';
import ReservationForm from '../ReservationForm/ReservationForm';
import ReservationService from '../../services/ReservationService';

export default function EditReservation({ reservationId, onSubmit }) {
  const [isLoading, setIsLoading] = useState(false);
  const [reservation, setReservation] = useState(null);

  useEffect(() => {
    setIsLoading(true);
    // TODO: call axios to invoke the get for current reservation
    ReservationService.getReservationById(reservationId).then(
      (response) => {
        setReservation(response.data);
        setIsLoading(false);
      }
    )
  }, [reservationId]);

  function handleReservationSubmit(event) {
    event.preventDefault();

    //TODO: call axios to invoke put to edit reservation.

    //TODO: report back to parent, tell it to reload reservations.

    ReservationService.updateReservation(reservationId, reservation).then(
      () => {
        alert('Reservation updated successfully!');
        onSubmit();
      }
    )
  }

  return (
    <div>
      <h2>Edit Reservation</h2>
      {isLoading ?
        <p>Loading...</p>
      :
        <ReservationForm
          reservation={reservation}
          setReservation={setReservation}
          onReservationSubmit={handleReservationSubmit}
        />
      }
    </div>
  );
}

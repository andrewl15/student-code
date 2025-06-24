import ReservationService from '../../services/ReservationService';
import styles from './ReservationList.module.css';

export default function ReservationList({ reservations, onEdit, onDelete }) {


  // TODO: Complete handleDeleteReservation(id), call it when button is clicked

  // TODO: Report back to parent, refresh list of reservations

  function handleDeleteReservation(id) {

      ReservationService.deleteReservation(id).then(
          () => {
              alert('Reservation deleted successfully!');
              onDelete();
          }
      ).catch((error) => {
        const message = error.response?.message || error.message;
        console.log('Error deleting reservation:', message);
      });

  }

  return (
    <div className={styles.reservationList}>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Hotel</th>
            <th>Full Name</th>
            <th>Check-in Date</th>
            <th>Check-out Date</th>
            <th>Guests</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {reservations.map((reservation) => (
            <tr key={reservation.id}>
              <td>{reservation.id}</td>
              <td>{reservation.hotelName}</td>
              <td>{reservation.fullName}</td>
              <td>{reservation.checkInDate}</td>
              <td>{reservation.checkOutDate}</td>
              <td>{reservation.guests}</td>
              <td>
                <button className={styles.editButton} onClick={() => onEdit(reservation.id)}>Edit</button>
                <button className={styles.deleteButton} onClick={ () => handleDeleteReservation(reservation.id) } >Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

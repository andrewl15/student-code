import { useEffect, useState } from 'react';
import HotelService from '../../services/HotelService';
import styles from './ReservationForm.module.css';

export default function ReservationForm({ reservation, setReservation, onReservationSubmit }) {
  const [hotels, setHotels] = useState([]);

  function getHotelData() {
    HotelService.getHotels()
      .then((response) => {
        setHotels(response.data);
      })
      .catch((error) => {
        console.log('Error retrieving hotels:', error);
      });
  }

  useEffect(() => {
    getHotelData();
  }, []);

  //TODO: Implement the onChange events, call the parent component's setter

  //TODO: Prepopulate values
  return (
    <form className={styles.reservationForm} onSubmit={onReservationSubmit}>
      <header>Reservation Details</header>
      <section>
        <label>Hotel:</label>
        <select value={reservation?.hotelId ?? ''} onChange={(e) => setReservation({ ...reservation, hotelId: e.target.value })}>
          <option value="" disabled>Select a hotel</option>
          {hotels.map((hotel) => (
            <option key={hotel.id} value={hotel.id}>
              {hotel.name}
            </option>
          ))}
        </select>
      </section>
      <section>
        <label>Full Name:</label>
        <input type="text" value={reservation?.fullName ?? ''} onChange={ (e) => setReservation( { ...reservation, fullName: e.target.value} )} />
      </section>
      <section>
        <label>Check-in Date:</label>
        <input type="date" value={reservation?.checkInDate ?? ''}  onChange={ (e) => setReservation( { ...reservation, checkInDate: e.target.value} )}  />
      </section>
      <section>
        <label>Check-out Date:</label>
        <input type="date" value={reservation?.checkOutDate ?? ''}  onChange={ (e) => setReservation( { ...reservation, checkOutDate: e.target.value} )}  />
      </section>
      <section>
        <label>Number of Guests:</label>
        <input type="number" value={reservation?.guests ?? ''}  onChange={ (e) => setReservation( { ...reservation, guests: e.target.value} )}   />
      </section>
      <button type="submit">Submit</button>
    </form>
  );
}

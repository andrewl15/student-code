import { useState, useEffect } from 'react';
import HotelService from '../../services/HotelService';
import styles from './HotelList.module.css';

export default function HotelList() {
  const [hotels, setHotels] = useState([]);

  function getHotels() {
    HotelService.getHotels()
      .then((response) => {
        setHotels(response.data);
      })
      .catch((error) => {
        console.log('Error retrieving hotels:', error);
      });
  }

  function deleteHotel(id) {
    HotelService.deleteHotel(id)
      .then(() => {
        setHotels(hotels.filter((hotel) => hotel.id !== id));
      })
      .catch((error) => {
        console.log('Error deleting hotel:', error);
      });
  }

  useEffect(() => {
    getHotels();
  }, []);

  return (
    <div>
      <h2>Hotel List</h2>
      <ul className={styles.hotelList}>
        {hotels.map((hotel) => (
          <li key={hotel.id}>
            <button onClick={() => deleteHotel(hotel.id)}>Delete</button>
            {hotel.name}
          </li>
        ))}
      </ul>
    </div>
  );
}

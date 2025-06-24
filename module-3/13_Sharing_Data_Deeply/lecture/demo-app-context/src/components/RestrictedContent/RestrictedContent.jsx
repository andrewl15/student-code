import HotelList from '../HotelList/HotelList';
import styles from './RestrictedContent.module.css';
import { useContext } from 'react';
import { UserContext } from '../../context/UserContext';

// TODO: Import in User Context

// Retrieve user data from the Context.

export default function RestrictedContent() {
  const user = useContext(UserContext);

  return (
    <div className={styles.restrictedContent}>
      {user ? (
        <HotelList />
      ) : (
        <p>Please log in to see Hotel List.</p>
      )}
    </div>
  );
}

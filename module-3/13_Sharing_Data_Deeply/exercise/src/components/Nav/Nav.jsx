import styles from './Nav.module.css';
import { useContext } from 'react';
import { UserContext } from '../../context/UserContext';

export default function Nav({ onNav, onLogout }) {
  const user = useContext(UserContext);
  const isLoggedIn = user;

  function handleNav(view) {
    onNav(view);
  }

  function handleLogout() {
    onLogout();
  }

  return (
    <nav className={styles.nav}>
      <a onClick={() => handleNav('MOVIES')}>Movies</a>

      { isLoggedIn ? (
        <>
          <a onClick={() => handleNav('LIKES')}>My Likes</a>
          <a onClick={handleLogout}>Logout</a>
        </>
      ) : (
        <a onClick={() => handleNav('LOGIN')}>Login</a>
      )}
    </nav>
  );
}
import { useContext } from 'react';
import { Link, NavLink } from 'react-router-dom';
import { UserContext } from '../../context/UserContext';
import { ThemeContext } from '../../context/ThemeContext';

import styles from './AppNav.module.css';

export default function AppNav() {
  const user = useContext(UserContext);
  const isLoggedIn = user !== null;

  const theme = useContext(ThemeContext);
  const navThemed = theme === 'light' ? styles.navLight : styles.navDark;

  return (
    <nav className={`${navThemed} ${styles.navigation}`}>
      <NavLink className={styles.link} to="/">
        Random Quote
      </NavLink>
      |
      {isLoggedIn ? (
        <>
          <NavLink className={styles.link} to="/addQuote">
            Add a Quote
          </NavLink>
          |
          <Link className={styles.link} to="/logout">
            Logout
          </Link>
        </>
      ) : (
        <NavLink className={styles.link} to="/login">
          Login
        </NavLink>
      )}
    </nav>
  );
}

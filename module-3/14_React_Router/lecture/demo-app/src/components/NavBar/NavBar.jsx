import { useContext } from 'react';
import { NavLink } from 'react-router-dom';
import { UserContext } from '../../context/UserContext';
import styles from './NavBar.module.css';

export default function NavBar() {
  const user = useContext(UserContext);
  return (
    <nav className={styles.navbar}>
      <NavLink to="/">Home</NavLink>
      <NavLink to="/products">Products</NavLink>
      <NavLink to="/about">About Us</NavLink>
      {user ?
      (
        <>
          <NavLink to="/admin">Admin</NavLink>
          <NavLink to="/logout">Logout</NavLink>
        </>
      ) :
        <NavLink to="/login">Login</NavLink>
      }
    </nav>
  );
}

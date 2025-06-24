import styles from './SideNav.module.css';

export default function SideNav() {
  return (
    <nav className={styles.sideNav}>
      <ul>
        <li>Home</li>
        <li>About</li>
        <li>Contact</li>
      </ul>
    </nav>
  );
}

import styles from './AppHeader.module.css';

export default function AppHeader() {

  return (
    <header className={styles.appHeader}>
      <h1 className={`${styles.appTitle}`}>Random Quote Generator</h1>
    </header>
  );
}
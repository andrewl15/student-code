import styles from './AppHeader.module.css';

export default function AppHeader() {

  return (
    <header>
      <h1 className={styles.appTitle}>Random Quote Generator</h1>
    </header>
  );
}
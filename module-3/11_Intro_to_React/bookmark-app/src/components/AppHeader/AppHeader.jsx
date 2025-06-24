import styles from './AppHeader.module.css';

/*
 * The AppHeader inherits some base styles from the global.css, but manages additional styles in
 * its own CSS Module AppHeader.module.css, isolating these styles from the rest of the application.
 */
export default function AppHeader() {
  return (
    <header id="app-header" className={styles.headerGrid}>
      <div id="app-info" className={styles.infoGrid}>
        <img className="app-logo" src="/images/logo.png" alt="app logo" />
        <h1>Bookmark Manager</h1>
      </div>
    </header>
  );
}

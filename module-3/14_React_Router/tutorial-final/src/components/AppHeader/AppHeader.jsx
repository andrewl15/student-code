import styles from './AppHeader.module.css';
import { useContext } from 'react';
import { ThemeContext } from '../../context/ThemeContext';
import ThemeControl from '../ThemeControl/ThemeControl';

export default function AppHeader({ onThemeChange }) {
  const theme = useContext(ThemeContext);
  const appTitleThemed = theme === 'light' ? styles.appTitleLight : styles.appTitleDark;

  return (
    <header className={styles.appHeader}>
      <h1 className={`${styles.appTitle} ${appTitleThemed}`}>Random Quote Generator</h1>
      <div className={styles.controlContainer}>
        <ThemeControl onThemeChange={onThemeChange} />
      </div>
    </header>
  );
}
import { useContext } from 'react';
import { ThemeContext } from '../../context/ThemeContext';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import styles from './ThemeControl.module.css';

export default function ThemeControl({ onThemeChange }) {
  const theme = useContext(ThemeContext);
  const controlButtonThemed = theme === 'light' ? styles.controlButtonLight : styles.controlButtonDark;

  return (
    <div>
      <button
        className={`${styles.controlButton} ${controlButtonThemed}`}
        onClick={onThemeChange}
      >
        <FontAwesomeIcon className={controlButtonThemed} icon={`fa-solid ${theme === 'light' ? 'fa-sun' : 'fa-moon'}`} />
      </button>
    </div>
  )
};

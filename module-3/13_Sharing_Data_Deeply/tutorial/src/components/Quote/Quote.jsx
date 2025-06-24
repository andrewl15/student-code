import styles from './Quote.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export default function Quote({ quote, onRefresh }) {

  return (
    <div className={`${styles.quote}`}>
      <p>{quote.text}</p>
      <p className={styles.author}>
        <em>{quote.author}</em>
      </p>
      <FontAwesomeIcon
        className={styles.iconRefresh}
        onClick={onRefresh}
        icon="fa-solid fa-refresh"
        title="Get new quote"
      />
    </div>
  );
}
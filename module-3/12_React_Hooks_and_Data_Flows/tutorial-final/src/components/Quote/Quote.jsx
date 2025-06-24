import styles from './Quote.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export default function Quote({ quote, onRefresh, onFavorite }) {
  return (
    <div className={styles.quote}>
      <FontAwesomeIcon
        className={quote.userLiked ? styles.iconFavoriteActive : styles.iconFavorite}
        icon="fa-solid fa-heart"
        title={quote.userLiked ? "Unlike this quote" : "Like this quote"}
        onClick={() => onFavorite(quote)}
      />
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
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import QuoteService from '../../services/QuoteService';
import styles from './QuoteView.module.css';

export default function QuoteView() {
  let likes = 0;

  function updateLikesDisplay() {
    const likesDisplay = document.getElementById('likes-counter');
    if (likes > 0) {
      likesDisplay.textContent = `You've liked ${likes} quotes.`;
    } else {
      likesDisplay.textContent = "You haven't liked any quotes.";
    }
  }

  function handleFavoriteClick(event) {
    const iconElement = event.currentTarget;
    const titleElement = iconElement.querySelector('title');

    if (titleElement.textContent === 'Like this quote') {
      titleElement.textContent = 'Unlike this quote';
      iconElement.classList.add(styles.iconFavoriteActive);
      likes++;
    } else {
      titleElement.textContent = 'Like this quote';
      iconElement.classList.remove(styles.iconFavoriteActive);
      likes--;
    }
    updateLikesDisplay();
  }

  return (
    <>
      <div className={styles.quoteContainer}>
        {QuoteService.getQuotes().map((quote) => (
          <div
            className={quote.id % 2 === 0 ? styles.quoteEven : styles.quoteOdd}
            key={quote.id}
          >
            <FontAwesomeIcon
              className={styles.iconFavorite}
              icon="fa-solid fa-heart"
              title="Like this quote"
              onClick={handleFavoriteClick}
            />
            <p>{quote.text}</p>
            <p className={styles.author}>{quote.author}</p>
          </div>
        ))}
        <p id="likes-counter" className={styles.likesCounter}>
          {"You haven't liked any quotes."}
        </p>
      </div>
    </>
  );
}

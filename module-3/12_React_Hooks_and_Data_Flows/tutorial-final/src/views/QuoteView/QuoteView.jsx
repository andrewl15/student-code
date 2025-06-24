import styles from './QuoteView.module.css';
import { useState, useEffect } from 'react'
import QuoteService from '../../services/QuoteService';
import Quote from '../../components/Quote/Quote';

export default function QuoteView() {
  const [likes, setLikes] = useState(0);
  const [quote, setQuote] = useState({});
  const [isLoading, setIsLoading] = useState(true);

  function handleFetchRandomQuote() {
    setIsLoading(true);
    QuoteService.getRandomQuote()
      .then((response) => {
        setQuote(response.data);
        setIsLoading(false);
      })
      .catch((error) => {
        console.error('Error fetching quote:', error);
        setIsLoading(false);
      });
  }

  function handleFavoriteClick(quote) {
    if (quote.userLiked) {
      setQuote({ ...quote, userLiked: false });
      setLikes(likes - 1);
    } else {
      setQuote({ ...quote, userLiked: true });
      setLikes(likes + 1);
    }
  }

  useEffect(() => {
    handleFetchRandomQuote();
  }, []);

  return (
    <>
      <div className={styles.quoteContainer}>
        {isLoading ? (
          <div className={styles.loadingQuote}>
            <div className="dot-pulse"></div>
          </div>
        ) : (
          <Quote
            quote={quote}
            onRefresh={handleFetchRandomQuote}
            onFavorite={handleFavoriteClick}
          />
        )}
        <p id="likes-counter" className={styles.likesCounter}>
          {likes > 0 ? `You've liked ${likes} quotes.` : "You haven't liked any quotes."}
        </p>
      </div>
    </>
  );
}

import styles from './QuoteView.module.css';
import { useState, useEffect } from 'react'
import QuoteService from '../../services/QuoteService';
import Quote from '../../components/Quote/Quote';

export default function QuoteView() {
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
          />
        )}
      </div>
    </>
  );
}

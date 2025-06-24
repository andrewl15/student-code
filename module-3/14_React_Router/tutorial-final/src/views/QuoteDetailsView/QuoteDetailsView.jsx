import styles from "./QuoteDetailsView.module.css";
import { useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import QuoteService from "../../services/QuoteService";
import { useContext } from "react";
import { ThemeContext } from "../../context/ThemeContext";
import { useParams } from "react-router-dom";

export default function QuoteView() {
  const { quoteId } = useParams();
  const [quote, setQuote] = useState({});
  const [isLoading, setIsLoading] = useState(true);
  const theme = useContext(ThemeContext);
  const quoteThemed = theme === "light" ? styles.quoteLight : styles.quoteDark;

  function fetchQuoteWithParam() {
    setIsLoading(true);
    QuoteService.getQuoteById(quoteId)
      .then((response) => {
        setQuote(response.data);
        setIsLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching quote:", error);
        setIsLoading(false);
      });
  }

  useEffect(() => {
    fetchQuoteWithParam();
  }, []);

  return (
    <>
      <div className={styles.quoteDetailsView}>
        {isLoading ? (
          <div className={styles.loadingQuote}>
            <div className="dot-pulse"></div>
          </div>
        ) : (
          <div>
            <div className={`${quoteThemed} ${styles.quoteDetails}`}>
              <p className={`${styles.text} ${styles.quote}`}>{quote.text}</p>
              <p
                className={`${styles.text} ${styles.author}`}
              >{`${quote.author}`}</p>
              <a
                className={styles.link}
                href={`https://en.wikipedia.org/wiki/${quote.author}`}
                target="_blank"
                rel="noopener noreferrer"
              >
                {`More about the author `}
                <FontAwesomeIcon
                  icon="fa-solid fa-external-link"
                  title="Wikipedia"
                />
              </a>
            </div>
          </div>
        )}
      </div>
    </>
  );
}

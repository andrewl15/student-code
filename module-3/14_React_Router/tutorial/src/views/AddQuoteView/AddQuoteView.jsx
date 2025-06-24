import { useState } from "react";
import { useNavigate } from "react-router-dom";
import QuoteService from "../../services/QuoteService";

import styles from "./AddQuoteView.module.css";

export default function AddQuoteView() {
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const [quote, setQuote] = useState({ text: "", author: "" });

  function handleSubmit(event) {
    setIsLoading(true);
    event.preventDefault();

    QuoteService.addQuote(quote)
      .then((response) => {
        // Navigate to the QuoteDetailsView with the newly created quote's id
        navigate(`/quotes/${response.data.id}`);
      })
      .catch((error) => {
        // Check for a response message, but display a default if that doesn't exist
        let message = "Failed to add quote: ";
        if (error.response) {
          message += error.response.data.message;
        } else if (error.request) {
          message += "No response from server. Please try again later.";
        } else {
          message += error.message;
        }
        console.log(message);
        setIsLoading(false);
      });
  }

  return (
    <div className={styles.addQuoteView}>
      <form onSubmit={handleSubmit}>
        <div className="form-control">
          <label htmlFor="text">Quote:</label>
          <input
            type="text"
            id="text"
            value={quote.text}
            size="50"
            required
            autoFocus
            autoComplete="quote"
            onChange={(event) =>
              setQuote({ ...quote, text: event.target.value })
            }
          />
        </div>

        <div className="form-control">
          <label htmlFor="author">Author:</label>
          <input
            type="text"
            id="author"
            value={quote.author}
            size="50"
            required
            onChange={(event) =>
              setQuote({ ...quote, author: event.target.value })
            }
          />
        </div>

        <div className={styles.formControl}>
          <button type="submit" className={`btn-primary ${styles.formButton}`}>
            Submit Quote
          </button>

        </div>

        {isLoading && <div className="dot-pulse"></div>}
      </form>
    </div>
  );
}

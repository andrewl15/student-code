import styles from "./Quote.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useContext } from "react";
import { ThemeContext } from "../../context/ThemeContext";
import { Link } from "react-router-dom";

export default function Quote({ quote, onRefresh }) {
  const theme = useContext(ThemeContext);
  const quoteThemed = theme === "light" ? styles.quoteLight : styles.quoteDark;

  return (
    <div className={`${styles.quote} ${quoteThemed}`}>
      <p>
        <Link className={styles.link} to={`/quotes/${quote.id}`}>
          {quote.text}
        </Link>
      </p>
      <p className={styles.author}>{quote.author}</p>
      <FontAwesomeIcon
        className={styles.iconRefresh}
        onClick={onRefresh}
        icon="fa-solid fa-refresh"
        title="Get new quote"
      />
    </div>
  );
}

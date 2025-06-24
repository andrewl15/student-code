import styles from "./Quote.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useContext } from "react";
import { ThemeContext } from "../../context/ThemeContext";

export default function Quote({ quote, onRefresh }) {
  const theme = useContext(ThemeContext);
  const quoteThemed = theme === "light" ? styles.quoteLight : styles.quoteDark;

  return (
    <div className={`${styles.quote} ${quoteThemed}`}>
      <p>
          {quote.text}
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

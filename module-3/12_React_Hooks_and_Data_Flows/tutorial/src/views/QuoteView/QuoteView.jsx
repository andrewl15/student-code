import styles from './QuoteView.module.css';

export default function QuoteView() {

  return (
    <>
      <div className={styles.quoteContainer}>
        <div className={styles.quote}>
          <div className="dot-pulse"></div>
        </div>
      </div>
    </>
  );
}

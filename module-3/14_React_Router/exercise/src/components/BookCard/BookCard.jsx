import styles from './BookCard.module.css';
import { Link } from 'react-router-dom';

export default function BookCard({ book }) {
  return (
    <article className={styles.bookCard}>
      <img
        className={styles.cover}
        src={`https://teapi.netlify.app${book.coverImage}`}
      />
      <section className={styles.details}>
        <Link to={`/book/${book.id}`} >
          <h2 className={styles.title}>{book.title}</h2>
        </Link>
        <p className={styles.overview}>{book.overview}</p>
      </section>
    </article>
  );
}

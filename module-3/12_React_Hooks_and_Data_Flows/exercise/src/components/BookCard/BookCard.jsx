import { useState } from 'react';
import styles from './BookCard.module.css';

export default function BookCard( {book, onLike} ) {
  const likeText = '🤍 Like';
  const likedText = '❤️ Liked';
  const [likeStatus, setLikeStatus] = useState(false);

  function handleLike(){
    onLike(likeStatus ? -1 : 1);
    setLikeStatus(!likeStatus);
  };
  return (
    <article className={styles.bookCard}>
      <img
        className={styles.cover}
        src={`https://teapi.netlify.app${book.coverImage}`}
      />
      <section className={styles.details}>
        <h2 className={styles.title}>{book.title}</h2>
        <p className={styles.overview}>{book.overview}</p>
        <button onClick={handleLike}>
          {likeStatus ? likedText : likeText}
        </button>
      </section>
    </article>
  );
}
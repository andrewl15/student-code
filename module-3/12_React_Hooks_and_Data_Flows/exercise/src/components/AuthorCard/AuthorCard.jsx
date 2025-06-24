import styles from './AuthorCard.module.css';

export default function AuthorCard( {author} ) {
  return (
    <article className={styles.authorCard}>
      <img
        className={styles.profile}
        src={`https://teapi.netlify.app${author.image}`}
      />
      <section className={styles.details}>
        <h2 className={styles.name}>{author.fullName}</h2>
        <p className={styles.bio}>{author.about}</p>
      </section>
    </article>
  );
}

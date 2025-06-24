import styles from './AuthorCard.module.css';
import { Link } from 'react-router-dom';


export default function AuthorCard({ author }) {
  return (
    <article className={styles.authorCard}>
      <img
        className={styles.profile}
        src={`https://teapi.netlify.app${author.image}`}
      />
      <section className={styles.details}>
        <Link to={`/author/${author.id}`} >
          <h2 className={styles.name}>{author.fullName}</h2>
        </Link>        
        <p className={styles.bio}>{author.about}</p>
      </section>
    </article>
  );
}

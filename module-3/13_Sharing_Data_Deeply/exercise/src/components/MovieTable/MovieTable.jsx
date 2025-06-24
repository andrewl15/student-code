import styles from './MovieTable.module.css';
import { useContext } from 'react';
import { UserContext } from '../../context/UserContext';

export default function MovieTable({ movies, onLike }) {
  const user = useContext(UserContext);
  const isLoggedIn = user;

  return (
    <table className={styles.tableFull}>
      <thead>
        <tr>
          <th>Title</th>
          <th>Release Date</th>
          <th>Rating</th>
          <th>Director</th>
          { onLike && isLoggedIn && <th className={styles.likeCell}>Like?</th> }
        </tr>
      </thead>
      <tbody>
        {movies.map((movie) => (
          <tr key={movie.movieId}>
            <td className={styles.movieTitle}>{movie.title}</td>
            <td>{movie.releaseDate}</td>
            <td>{movie.rating}</td>
            <td>{movie.director}</td>
            { onLike && isLoggedIn &&
              <td className={styles.likeCell}>
                <button
                  className={styles.likeButton}
                  onClick={() => onLike(movie.userLiked, movie.movieId)}
                >
                  {movie.userLiked ? ('Unlike') : ('Like')}
                </button>
              </td>
            }
          </tr>
        ))}
      </tbody>
    </table>
  );
}

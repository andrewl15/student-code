import MovieService from "../../services/MovieService";
import styles from "./MoviesView.module.css";

export default function MoviesView() {
  const movies = MovieService.getMovies();

  function setLike(event) {
    if (event.target.textContent.includes('Like')) {
      event.target.textContent = 'Unlike';
    } else {
      event.target.textContent = 'Like';
    }
  }
  return (
    <>
      <header id="movie-list-header">
        <h2>Movies List</h2>
      </header>
      <table className={styles.tableFull}>
        <thead>
          <tr>
            <th>Title</th>
            <th>Release Date</th>
            <th>Rating</th>
            <th>Director</th>
            <th>Liked</th>
          </tr>
        </thead>
        <tbody>
          {
            movies.map(
              (movie) => (

                <tr key={movie.movieId}>
                  <td className={styles.movieTitle}>{movie.title}</td>
                  <td>{movie.releaseDate}</td>
                  <td>{movie.rating}</td>
                  <td>{movie.director}</td>
                  <td><button onClick={setLike} className={styles.likeButton}>{movie.userLiked ? "Unlike" : "Like"}</button></td>
                </tr>
              )
            )
          }
        </tbody>
      </table>
    </>
  );
}



import { useEffect, useState } from 'react';
import MovieService from '../services/MovieService';
import Notification from '../components/Notification/Notification';
import MovieTable from '../components/MovieTable/MovieTable';

export default function MyLikesView() {
  const [movies, setMovies] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [notification, setNotification] = useState(null);

  function getPageData() {
    setIsLoading(true);
    MovieService.getMyLikes()
      .then((response) => {
        setMovies(response.data);
        setIsLoading(false);
      })
      .catch((error) => {
        const message = error.response?.data?.message || error.message;
        console.error(`Error fetching movies: ${message}`);
        setNotification({ type: 'error', message: 'Failed to fetch movies.' });
        setIsLoading(false);
      });
  }

  useEffect(() => {
    getPageData();
  }, []);

  return (
    <>
      <header id="movie-likes-header">
        <h2>Your Likes</h2>
      </header>

      <Notification notification={notification} clearNotification={() => setNotification(null)} />

      {isLoading ? (
        <p>Loading...</p>
      ) : (
        <MovieTable movies={movies} />
        // purposely not passing onLike prop to MovieTable so that the Like button isn't displayed
      )}
    </>
  );
}

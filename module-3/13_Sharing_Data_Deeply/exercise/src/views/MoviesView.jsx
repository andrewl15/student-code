import { useEffect, useState } from 'react';
import MovieService from '../services/MovieService';
import Notification from '../components/Notification/Notification';
import MovieTable from '../components/MovieTable/MovieTable';

export default function MoviesView() {
  const [movies, setMovies] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [notification, setNotification] = useState(null);

  function getPageData() {
    setIsLoading(true);
    MovieService.getMovies()
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

  function handleLike(userLiked, movieId) {
    if (userLiked) {
      // unlike movie
      MovieService.unlikeMovie(movieId)
        .then((response) => {
          getPageData();
        }
        ).catch((error) => {
          const message = error.response?.data?.message || error.message;
          console.error(`Error fetching movies: ${message}`);
          setNotification({ type: 'error', message: 'Failed to fetch movies.' });
        })

    } else {
      // like movie
      MovieService.likeMovie(movieId)
        .then((response) => {
          getPageData();
        }
        ).catch((error) => {
          const message = error.response?.data?.message || error.message;
          console.error(`Error fetching movies: ${message}`);
          setNotification({ type: 'error', message: 'Failed to fetch movies.' });
        })
    }
  }

  useEffect(() => {
    getPageData();
  }, []);

  return (
    <>
      <header id="movie-list-header">
        <h2>Movies List</h2>
      </header>

      <Notification notification={notification} clearNotification={() => setNotification(null)} />

      {isLoading ? (
        <p>Loading...</p>
      ) : (
        <MovieTable movies={movies} onLike={handleLike} />
      )}
    </>
  );
}

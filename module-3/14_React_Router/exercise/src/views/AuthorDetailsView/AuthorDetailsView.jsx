import { useEffect, useState } from 'react';
import BookService from '../../services/BookService';
import BookCard from '../../components/BookCard/BookCard';
import AuthorCard from '../../components/AuthorCard/AuthorCard';
import { useParams } from 'react-router-dom';

import styles from './AuthorDetailsView.module.css';

export default function AuthorsView() {
  const [author, setAuthor] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const authorId = 302203;
  const { id } = useParams();

  function getPageData() {
    setIsLoading(true);
    BookService.getAuthorById(id)
      .then((response) => {
        setAuthor(response.data);
        setIsLoading(false);
      })
      .catch((error) => {
        const errorMessage = error.response ? error.response.data.message : error.message;
        console.error(errorMessage);
        setIsLoading(false);
      });
  }

  useEffect(() => {
    getPageData();
    // Disable the linter rule about missing dependencies because passing an empty array is valid to have this effect run only once when the component first mounts
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      {isLoading ? (
        <p>Loading...</p>
      ) : (
        <>
          <AuthorCard author={author}/>
          <p className={styles.bookCounter}>Total Books: {author.books.length}</p>
          <div>
            {author.books.map((book) => (
              <BookCard key={book.id} book={book} />
            ))}
          </div>
        </>
      )}
    </>
  );
}

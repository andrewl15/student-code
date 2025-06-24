import { useState, useEffect } from 'react';
import BookService from '../../services/BookService';
import BookCard from '../../components/BookCard/BookCard';
import { useParams } from 'react-router-dom';

import styles from './BookDetailsView.module.css';

export default function BookDetailsView() {
  const [book, setBook] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const bookId = 102230;
  const { id } = useParams();

  function getPageData() {
    setIsLoading(true);
    BookService.getBookById(id)
      .then((response) => {
        setBook(response.data);
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
          <BookCard book={book} />
          <h3>Authors:</h3>
          <section className={styles.authorList}>
            {book.authors.map((author) => (
              <span key={author.id}>
                {author.fullName}
              </span>
            ))}
          </section>
        </>
      )}
    </>
  );
}

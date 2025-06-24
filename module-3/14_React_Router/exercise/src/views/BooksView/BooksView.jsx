import { useEffect, useState } from 'react';
import BookService from '../../services/BookService';
import BookCard from '../../components/BookCard/BookCard';

import styles from './BooksView.module.css';

export default function BooksView() {
  const [books, setBooks] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  function getPageData() {
    setIsLoading(true);
    BookService.getBooks()
      .then((response) => {
        setBooks(response.data);
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
  }, []);

  return (
    <>
      {isLoading ? (
        <p>Loading...</p>
      ) : (
        <>
          <p className={styles.totalCounter}>Total Books: {books.length}</p>
          <div className={styles.bookList}>
            {books.map((book) => (
              <BookCard key={book.id} book={book} />
            ))}
          </div>
        </>
      )}
    </>
  );
}

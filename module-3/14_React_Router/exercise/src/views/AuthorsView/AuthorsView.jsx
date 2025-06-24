import { useEffect, useState } from 'react';
import BookService from '../../services/BookService';
import AuthorCard from '../../components/AuthorCard/AuthorCard';

import styles from './AuthorsView.module.css';

export default function AuthorsView() {
  const [authors, setAuthors] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  function getPageData() {
    setIsLoading(true);
    BookService.getAuthors()
      .then((response) => {
        setAuthors(response.data);
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
          <p className={styles.authorCounter}>Total Authors: {authors.length}</p>
          <div className={styles.authorList}>
            {authors.map((author) => (
              <AuthorCard key={author.id} author={author} />
            ))}
          </div>
        </>
      )}
    </>
  );
}

import styles from './AuthorsView.module.css';
import BookService from '../../services/BookService';
import AuthorCard from '../../components/AuthorCard/AuthorCard';
import { useState, useEffect } from 'react';

export default function AuthorsView() {
  const [authorObjects, setAuthorObjects] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  function getPageData() {
    setIsLoading(true);
    BookService.getAuthors().then(
      (response) => {
        console.log(response.data)
        setAuthorObjects(response.data);
        setIsLoading(false);
      }
    ).catch(
      (error) => {
        console.log(error.message)
      }
    );
  }

  useEffect(
    () => { getPageData() },
    []
  );
  return (
    <>
      {isLoading ? (
        <p>Loading...</p>
      ) : (
        <>
          <p className={styles.authorCounter}>Total Authors: {authorObjects.length}</p>
          <div className={styles.authorList}>
            {
              authorObjects.map(
                (author, index) => (
                  <AuthorCard
                    key={index}
                    author={author}
                  />
                )
              )
            }
          </div>
        </>
      )}
    </>
  );
}

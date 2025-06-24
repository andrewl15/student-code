import styles from './BooksView.module.css';
import { useState, useEffect } from 'react';
import BookService from '../../services/BookService';
import BookCard from '../../components/BookCard/BookCard';

export default function BooksView() {
  const [bookObjects, setBookObjects] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [likedBooks, setLikedBooks] = useState(0);

  function incrementLikes(number){
    setLikedBooks(likedBooks + number);
  }

  function getPageData() {
    setIsLoading(true);
    BookService.getBooks().then(
      (response) => {
        console.log(response.data)
        setBookObjects(response.data);
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
          <p className={styles.totalCounter}>Total Books: {bookObjects.length}</p>
          <p className={styles.likeCounter}>Liked Books: {likedBooks}</p>
          <div className={styles.bookList}>
            {
              bookObjects.map(
                (book, index) => (
                  <BookCard
                    key={index}
                    book={book}
                    onLike={incrementLikes}
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

import { useState } from 'react';
import AuthorsView from './views/AuthorsView/AuthorsView';
import BooksView from './views/BooksView/BooksView';

import styles from './App.module.css';

export default function App() {
  const [view, setView] = useState('authors');

  return (
    <div className={styles.app}>
      <h1 className={styles.header}>🧭 Book Explorer</h1>
      <nav>
        <button className={`${styles.link} ${view === 'authors' ? styles.active : '' }`} onClick={() => setView('authors')}>👩‍🏫 Authors</button>
        <button className={`${styles.link} ${view === 'books' ? styles.active : '' }`} onClick={() => setView('books')}>📚 Books</button>
      </nav>
      {view === 'authors' && <AuthorsView />}
      {view === 'books' && <BooksView />}
    </div>
  );
}

import { useState } from 'react';
import AuthorsView from './views/AuthorsView/AuthorsView';
import AuthorDetailsView from './views/AuthorDetailsView/AuthorDetailsView';
import BookDetailsView from './views/BookDetailsView/BookDetailsView';
import BooksView from './views/BooksView/BooksView';
import { Routes, Route, NavLink } from 'react-router-dom';
import styles from './App.module.css';
import { BrowserRouter } from 'react-router-dom';

export default function App() {

  return (
    <div className={styles.app}>
      <BrowserRouter>
        <h1 className={styles.header}>🧭 Book Explorer</h1>
        <nav>
          {/* replace these buttons */}
          <NavLink to='/authors'>Authors</NavLink>
          <NavLink to='/books'>Books</NavLink>
        </nav>
        
        <Routes>
          <Route path='/authors' element={<AuthorsView />} />
          <Route path='/books' element={<BooksView />} />
          <Route path='/author/:id' element={<AuthorDetailsView/>}/>
          <Route path='/book/:id' element={<BookDetailsView/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

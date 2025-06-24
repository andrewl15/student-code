import { useState, useEffect } from 'react';
import AppHeader from './components/AppHeader';
import Nav from './components/Nav/Nav';
import ViewManager from './components/ViewManager';
import axios from 'axios';
import { UserContext } from './context/UserContext';

export default function App() {
  const [viewName, setViewName] = useState('MOVIES');
  const [user, setUser] = useState(null);

  // When a user comes back to the app or refreshes the page, check for user/token in local storage and validate it
  useEffect(() => {
    const user = JSON.parse(localStorage.getItem('user'));
    const token = localStorage.getItem('token');

    if (user && token) {
      // Set the token in the axios default headers
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      handleLogin(user);
    }
  }, []);

  function handleLogin(userData) {
    setUser(userData);
    setViewName('MOVIES');
  }

  function handleLogout() {
    // Remove auth data from local storage
    localStorage.removeItem('user');
    localStorage.removeItem('token');

    // Clear auth token from axios
    delete axios.defaults.headers.common['Authorization'];

    // Clear the auth context
    setUser(null);
    setViewName('LOGIN');
  }

  function handleNav(view) {
    setViewName(view);
  }

  return (
    <div id="movie-app">

      <AppHeader />
      <UserContext.Provider user={user}>
        <Nav
          onNav={handleNav}
          onLogout={handleLogout}
        />
        <ViewManager
          viewName={viewName}
          onLogin={handleLogin}
        />
      </UserContext.Provider>
    </div>
  );
}

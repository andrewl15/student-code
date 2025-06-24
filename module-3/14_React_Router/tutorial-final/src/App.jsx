import { useState } from 'react';
import { BrowserRouter } from 'react-router-dom';
import { UserContext } from './context/UserContext';
import { ThemeContext } from './context/ThemeContext';
import AppHeader from './components/AppHeader/AppHeader';
import AppNav from './components/AppNav/AppNav';
import MainContent from './components/MainContent/MainContent';
import axios from 'axios';

export default function App() {
  const [user, setUser] = useState(() => getUserAndTokenFromStorage());
  const [theme, setTheme] = useState('light');

  function handleLogin(userData) {
    setUser(userData);
  }

  function handleLogout() {
    // Remove auth data from local storage
    localStorage.removeItem('user');
    localStorage.removeItem('token');

    // Clear auth token from axios
    delete axios.defaults.headers.common['Authorization'];

    // Clear the auth context
    setUser(null);
  }

   // When a user comes back to the app or refreshes the page, check for user/token in local storage and validate it
   function getUserAndTokenFromStorage() {
    const user = JSON.parse(localStorage.getItem('user'));
    const token = localStorage.getItem('token');

    if (user && token) {
      // Set the token in the axios default headers
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      // Set the user to the user state
      return user;
    }

    // If no user/token in local storage, return null to ensure user state variable is assigned null
    return null;
  }

  function handleThemeChange() {
    setTheme(theme === 'light' ? 'dark' : 'light');
  }

  document.body.className = theme === 'light' ? 'light-mode' : 'dark-mode';

  return (
    <div id="quote-app">
      <BrowserRouter>
        <UserContext.Provider value={user}>
          <ThemeContext.Provider value={theme}>
            <AppHeader onThemeChange={handleThemeChange} />
            <AppNav />
            <MainContent onLogin={handleLogin} onLogout={handleLogout} />
          </ThemeContext.Provider>
        </UserContext.Provider>
      </BrowserRouter>
    </div>
  );
}

import { useState } from 'react';
import LoginPanel from './components/LoginPanel/LoginPanel';
import MainContent from './components/MainContent/MainContent';
import axios from 'axios';

//TODO: Import in user context

//TODO: set the user object you retrieved from the server

//TODO: Add in Context tag

import { UserContext } from './context/UserContext';

export default function App() {
  const [user, setUser] = useState(null);

  function handleLogin(userData) {
    setUser(userData);
  }

  function handleLogout() {
    setUser(null);
    delete axios.defaults.headers.common['Authorization'];
  }

  return (
    <div>
      <UserContext.Provider value={user}>
        <header>
          <LoginPanel /*user={user}*/ onLogin={handleLogin} onLogout={handleLogout} />
        </header>
        <main>
          <MainContent />
        </main>
      </UserContext.Provider>
    </div>
  );
}

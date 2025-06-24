import { useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import AppHeader from './components/AppHeader/AppHeader';
import NavBar from './components/NavBar/NavBar';
import HomeView from './views/HomeView';
import LoginView from './views/LoginView';
import LogoutView from './views/LogoutView';
import AboutUsView from './views/AboutUsView';
import ProductListView from './views/ProductListView/ProductListView';
import ProductDetailView from './views/ProductDetailView/ProductDetailView';
import AdminView from './views/AdminView';
import PageNotFoundView from './views/PageNotFoundView';
import ProtectedRoute from './components/ProtectedRoute';
import { UserContext } from './context/UserContext';

export default function App() {
  const [user, setUser] = useState(() => getUserFromStorage());

  function handleLogin(userData) {
    setUser(userData);
    localStorage.setItem('user', JSON.stringify(userData));
  }

  function handleLogout() {
    setUser(null);
    localStorage.removeItem('user');
  }

  // When a user comes back to the app or refreshes the page, check for user in local storage
  function getUserFromStorage() {
    const user = JSON.parse(localStorage.getItem('user'));

    if (user) {
      return user;
    }
    // If no user in local storage, return null
    return null;
  }

  // TODO: Build a route for the about
  // TODO: Build a route for products
  // TODO: Build a protected route for AdminView

  // TODO: Build a route for products/:id

  return (
    <div id="store-app">
      <BrowserRouter>
        <AppHeader />
        <UserContext.Provider value={user}>
          <main>
              <NavBar />
              <Routes>
                <Route path="/" element={<HomeView />} />

                {/* Place new Routes Here */}
                {/* and btw lol this is how you comment inside jsx, escape first */}
                <Route path="/about" element={ <AboutUsView/> } />
                <Route path="/products" element={ <ProductListView/>} />
                <Route path="/products/:id" element={ <ProductDetailView/>} />

              
                <Route path="/login" element={<LoginView onLogin={handleLogin} />} />
                <Route path="/logout" element={<LogoutView onLogout={handleLogout} />} />
                <Route
                  path="/admin"
                  element={
                    <ProtectedRoute>
                      <AdminView />
                    </ProtectedRoute>
                  }
                />
                <Route path="*" element={<PageNotFoundView />} />
              </Routes>
          </main>
        </UserContext.Provider>
      </BrowserRouter>
    </div>
  );
}

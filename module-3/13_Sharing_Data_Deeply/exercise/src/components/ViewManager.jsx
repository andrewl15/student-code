import LoginView from '../views/LoginView/LoginView';
import MoviesView from '../views/MoviesView';
import MyLikesView from '../views/MyLikesView';

export default function ViewManager({ viewName, onLogin }) {
  function handleLogin(userData) {
    onLogin(userData);
  }

  return (
    <main id="main-content">
      {viewName === 'LOGIN' &&
        <LoginView onLogin={handleLogin} />
      }
      {viewName === 'MOVIES' &&
        <MoviesView />
      }
      {viewName === 'LIKES' &&
        <MyLikesView />
      }
    </main>
  );
}

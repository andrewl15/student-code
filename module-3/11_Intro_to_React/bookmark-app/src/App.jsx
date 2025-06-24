import AppHeader from './components/AppHeader/AppHeader';
import AppFooter from './components/AppFooter';
import PublicBookmarksView from './views/PublicBookmarksView/PublicBookmarksView';

export default function App() {

  return (
    <div id="book-app">
      <AppHeader />
      <main id="main-content">
        <PublicBookmarksView />
      </main>
      <AppFooter />
    </div>
  );
}

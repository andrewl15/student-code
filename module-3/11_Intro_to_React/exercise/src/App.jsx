import AppHeader from "./components/AppHeader";
import MoviesView from "./views/MoviesView/MoviesView";

export default function App() {
  return (
    <div id="movie-app">
      <AppHeader/>
      <main id="main-content">
        <MoviesView/>
      </main>
    </div>
  );
}

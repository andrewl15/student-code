import QuoteView from './views/QuoteView/QuoteView';
import AppHeader from './components/AppHeader/AppHeader';

export default function App() {

  return (
    <div id="quote-app">
      <AppHeader />
      <QuoteView />
    </div>
  );
}

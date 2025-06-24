import AppHeader from './components/AppHeader/AppHeader';
import QuoteView from './views/QuoteView/QuoteView';
import { useState } from 'react';
import { ThemeContext } from './context/ThemeContext';

export default function App() {
  const [theme, setTheme] = useState(ThemeContext);

  function handleThemeChange() {
    setTheme(theme === 'light' ? 'dark' : 'light');
  }

  document.body.className = theme === 'light' ? 'light-mode' : 'dark-mode';

  return (
    <div id="quote-app">
      <ThemeContext.Provider value={theme}>
        <AppHeader onThemeChange={handleThemeChange} />
        <QuoteView />
      </ThemeContext.Provider>
    </div>
  );
}

import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import App from './App.jsx';

/* import fontawesome core */
import { library } from '@fortawesome/fontawesome-svg-core'
import { faXmark } from '@fortawesome/free-solid-svg-icons'

/* add icons to the library */
library.add(faXmark)


createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>,
)

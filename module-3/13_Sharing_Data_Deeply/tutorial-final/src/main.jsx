import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import axios from 'axios';

import App from './App.jsx';

/* import fontawesome core */
import { library } from '@fortawesome/fontawesome-svg-core';
import { faHeart, faRefresh, faMoon, faSun } from '@fortawesome/free-solid-svg-icons';

/* add icons to the library */
library.add(faHeart);
library.add(faRefresh);
library.add(faMoon);
library.add(faSun);

/* sets the base url for server API communication with axios */
axios.defaults.baseURL = import.meta.env.VITE_REMOTE_API;

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>,
);

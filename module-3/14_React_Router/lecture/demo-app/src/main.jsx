import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import axios from 'axios';

import App from './App.jsx'

/* sets the base url for server API communication with axios */
axios.defaults.baseURL = import.meta.env.VITE_REMOTE_API;

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>,
)

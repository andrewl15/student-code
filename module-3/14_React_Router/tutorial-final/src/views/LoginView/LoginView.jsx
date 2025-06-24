import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "../../services/AuthService";
import axios from "axios";

import styles from "./LoginView.module.css";

export default function LoginView({ onLogin }) {
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  // Setup state for the registration data
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  function handleSubmit(event) {
    setIsLoading(true);
    event.preventDefault();

    AuthService.login({ username, password })
      .then((response) => {
        // Grab the user and token
        const user = response.data.user;
        const token = response.data.token;
        axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;

        // Add the login data to local storage
        localStorage.setItem("user", JSON.stringify(user));
        localStorage.setItem("token", token);

        // Use the callback to add user to state
        onLogin(user);

        // Navigate to the home page
        navigate("/");
      })
      .catch((error) => {
        // Check for a response message, but display a default if that doesn't exist
        let message = "Login failed: ";
        if (error.response) {
          message += error.response.data.message;
        } else if (error.request) {
          message += "No response from server. Please try again later.";
        } else {
          message += error.message;
        }
        console.log(message);
        setIsLoading(false);
      });
  }

  return (
    <div className={styles.loginView}>
      <h2>Log in</h2>

      <form onSubmit={handleSubmit}>
        <div className="form-control">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            size="50"
            required
            autoFocus
            autoComplete="username"
            onChange={(event) => setUsername(event.target.value)}
          />
        </div>

        <div className="form-control">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            size="50"
            required
            onChange={(event) => setPassword(event.target.value)}
          />
        </div>

        <button type="submit" className={`btn-primary ${styles.formButton}`}>
          Sign in
        </button>
        {isLoading && <div className="dot-pulse"></div>}
      </form>
    </div>
  );
}

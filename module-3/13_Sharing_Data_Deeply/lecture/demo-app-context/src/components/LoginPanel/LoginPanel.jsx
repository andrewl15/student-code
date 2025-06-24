import { useContext, useState } from 'react';
import AuthService from '../../services/AuthService';
import { UserContext } from '../../context/UserContext';
import axios from 'axios';

export default function LoginPanel({ /*user,*/ onLogin, onLogout }) {
  const user = useContext(UserContext);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  function handleSubmit(event) {
    event.preventDefault();

    const userData = {
      username: username,
      password: password
    };

    AuthService.login(userData)
      .then((response) => {
        const user = response.data.user;
        const token = response.data.token;
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

        onLogin(user);

        setUsername('');
        setPassword('');
      })
      .catch((error) => {
        const message = error.response?.data?.message || 'Login failed.';
        alert(message);
        console.log('Login error:', error);
      });
  }

  return (
    <>
      {user ? (
        <div>
          Welcome, {user.username}! <button onClick={onLogout}>Logout</button>
        </div>
      ) : (
        <form onSubmit={handleSubmit}>
          <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
          <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
          <button type="submit">Login</button>
        </form>
      )}
    </>
  );
}

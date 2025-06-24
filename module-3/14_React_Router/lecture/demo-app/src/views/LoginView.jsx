import { useNavigate } from 'react-router-dom';

// This is a simulated login view that results in a successful login with a hardcoded user object.
// In a real application, you'd use a form to get the user's credentials and send them to the server for authentication.

export default function LoginView({ onLogin }) {
  const navigate = useNavigate();

  const simulatedUser = {
    id: 1,
    name: 'Admin'
  };

  function handleLogin() {
    onLogin(simulatedUser);
    navigate('/admin');
  }

  return (
    <section>
      <h2>Login</h2>
      <p>Log in to access the admin page</p>
      <input type="text" value="admin" readOnly />
      <br />
      <input type="password" value="password" readOnly />
      <br />
      <button onClick={handleLogin}>Log in</button>
    </section>
  );
}

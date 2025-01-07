import React from 'react';

const Login = () => {
  const handleLogin = () => {
    window.location.href = '/oauth2/authorization/google';
  };

  return (
    <div>
      <h1>Login</h1>
      <button onClick={handleLogin}>Login with Google</button>
    </div>
  );
};

export default Login;
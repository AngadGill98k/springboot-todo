import React, { useState } from 'react';
import './login.css';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const [isSignup, setIsSignup] = useState(false);
  const [form, setForm] = useState({ username: '', mail: '', password: '' });
  const [errorMsg, setErrorMsg] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setErrorMsg('');
  };

  const validateForm = () => {
    const { username, mail, password } = form;

    if (!username.trim() || !mail.trim() || !password.trim()) {
      setErrorMsg('All fields are required.');
      return false;
    }

    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegex.test(mail)) {
      setErrorMsg('Please enter a valid email address.');
      return false;
    }

    return true;
  };

  const handleSignup = () => {
    fetch('http://localhost:8080/signup', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify({
        name: form.username,
        mail: form.mail,
        pass: form.password
      })
    })
      .then(res => res.json())
      .then(data => {
        if (data.msg === 'Signed up successfully') {
          
        } else {
          setErrorMsg(data.msg || 'Signup failed.');
        }
      })
      .catch(err => {
        console.error(err);
        setErrorMsg('Signup error occurred.');
      });
  };

  const handleSignin = () => {
    fetch('http://localhost:8080/signin', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify({
        name: form.username,
        mail: form.mail,
        pass: form.password
      })
    })
      .then(res => res.json())
      .then(data => {
        if (data.msg === 'Logged in successfully') {
          navigate('/main');
        } else {
          setErrorMsg(data.msg || 'Login failed. Please check your credentials.');
        }
      })
      .catch(err => {
        console.error(err);
        setErrorMsg('Login error occurred.');
      });
  };

  const handleSubmit = () => {
    if (!validateForm()) return;

    if (isSignup) {
      handleSignup();
    } else {
      handleSignin();
    }
  };

  return (
    <div className="login-background">
      <div className="login-card">
        <h2>{isSignup ? 'Sign Up' : 'Sign In'}</h2>

        <input
          type="text"
          name="username"
          placeholder="Username"
          value={form.username}
          required
          onChange={handleChange}
        />

        <input
          type="email"
          name="mail"
          placeholder="Email"
          value={form.mail}
          required
          onChange={handleChange}
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          required
          onChange={handleChange}
        />

        <button onClick={handleSubmit}>
          {isSignup ? 'Create Account' : 'Log In'}
        </button>

        {errorMsg && <p className="error-message">{errorMsg}</p>}

        <p>
          {isSignup ? 'Already have an account?' : "Don't have an account?"}
          <span onClick={() => {
            setIsSignup(!isSignup);
            setErrorMsg('');
          }}>
            {isSignup ? ' Sign In' : ' Sign Up'}
          </span>
        </p>
      </div>
    </div>
  );
};

export default Login;

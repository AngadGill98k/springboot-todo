
import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [todos, setTodos] = useState([]);
  const [input, setInput] = useState('');

  const fetchTodos = () => {
    fetch('http://localhost:8080/main/gettodos', {
      method: 'GET',
      credentials: 'include', // make sure cookies (token) go with the request
    })
      .then(res => res.json())
      .then(data => {
        if (Array.isArray(data.todos)) {
          setTodos(data.todos);
        }
      })
      .catch(err => console.error('Error fetching todos:', err));
  };

  const handleclick = () => {
    if (!input.trim()) return;

    const name = {
      name: input,
      status: false,
    };

    fetch('http://localhost:8080/main/add', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(name)
    })
      .then(res => res.json())
      .then(data => {
        console.log(data);
        setInput('');
        fetchTodos(); // ✅ refresh todos after adding
      })
      .catch(err => console.error(err));
  };

  // ✅ Fetch todos when component mounts
  useEffect(() => {
    fetchTodos();
  }, []);

  return (
    <>
      <input
        type='text'
        id='todo'
        value={input}
        onChange={e => setInput(e.target.value)}
        placeholder="Add todo"
      />
      <button onClick={handleclick}>Add</button>

      <ul>
        {todos.map((todo, index) => (
          <li key={index}>
            <span>{todo.name}</span> - <span>{todo.status ? '✅ Done' : '❌ Pending'}</span>
          </li>
        ))}
      </ul>
    </>
  );
}

export default App;
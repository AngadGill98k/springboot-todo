import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [todos, setTodos] = useState([]);
  const [input, setInput] = useState('');

  const fetchTodos = () => {
    fetch('http://localhost:8080/main/gettodos', {
      method: 'GET',
      credentials: 'include',
    })
      .then(res => res.json())
      .then(data => {
        if (Array.isArray(data.todos)) {
          console.log(data.todos)
          setTodos(data.todos);
        }
      })
      .catch(err => console.error('Error fetching todos:', err));
  };

  const handleAdd = () => {
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
      .then(() => {
        setInput('');
        fetchTodos();
      })
      .catch(err => console.error(err));
  };

  const handleDelete = (id) => {
    fetch(`http://localhost:8080/main/delete/${id}`, {
      method: 'DELETE',
      credentials: 'include',
    })
      .then(res => res.json())
      .then(() => fetchTodos())
      .catch(err => console.error('Delete failed:', err));
  };

  const handleToggleDone = (id) => {
    fetch(`http://localhost:8080/main/toggle/${id}`, {
      method: 'PUT',
      credentials: 'include',
    })
      .then(res => res.json())
      .then(() => fetchTodos())
      .catch(err => console.error('Toggle failed:', err));
  };

  useEffect(() => {
    fetchTodos();
  }, []);

  return (
  <div className="app-container">
    <h2>📝 Todo App</h2>
    <input
      type='text'
      id='todo'
      value={input}
      onChange={e => setInput(e.target.value)}
      placeholder="Add todo"
    />
    <button onClick={handleAdd}>Add</button>

    <ul>
      {todos.map((todo) => (
        <li key={todo.id}>
          <span>{todo.name}</span> - <span>{todo.status ? '✅ Done' : '❌ Pending'}</span>
          <div>
            <button onClick={() => handleToggleDone(todo.id)}>Toggle Done</button>
            <button onClick={() => handleDelete(todo.id)}>Delete</button>
          </div>
        </li>
      ))}
    </ul>
  </div>
);

}

export default App;

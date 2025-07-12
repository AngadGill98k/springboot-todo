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
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(name),
    })
      .then(res => res.json())
      .then(() => {
        setInput('');
        fetchTodos();
      })
      .catch(err => console.error(err));
  };

  const handleDone = (id) => {
    fetch(`http://localhost:8080/main/markdone/${id}`, {
      method: 'PUT',
      credentials: 'include',
    })
      .then(res => res.json())
      .then(() => fetchTodos())
      .catch(err => console.error('Error marking done:', err));
  };

  const handleDelete = (id) => {
    fetch(`http://localhost:8080/main/delete/${id}`, {
      method: 'DELETE',
      credentials: 'include',
    })
      .then(res => res.json())
      .then(() => fetchTodos())
      .catch(err => console.error('Error deleting:', err));
  };

  useEffect(() => {
    fetchTodos();
  }, []);

  return (
    <>
      <input
        type="text"
        value={input}
        onChange={(e) => setInput(e.target.value)}
        placeholder="Add todo"
      />
      <button onClick={handleAdd}>Add</button>

      <ul>
        {todos.map((todo) => (
          <li key={todo._id}>
            <span>{todo.name}</span> - <span>{todo.status ? '✅ Done' : '❌ Pending'}</span>
            {!todo.status && <button onClick={() => handleDone(todo._id)}>Mark Done</button>}
            <button onClick={() => handleDelete(todo._id)}>Delete</button>
          </li>
        ))}
      </ul>
    </>
  );
}

export default App;

import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from "./components/Login.jsx";
import Dashboard from "./components/Dashboard.jsx";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/dashboard" element={<Dashboard/>}/>
        <Route path="/" element={<Login/>}/>
      </Routes>
    </Router>
  );
}

export default App;
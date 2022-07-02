import { useState } from 'react';
import GoogleLogin from 'react-google-login'
import './App.css';
import Navbar from './components/Navbar';
import {BrowserRouter as Router, Routes, Route} 
from 'react-router-dom';

function App() {
  const [loginData, setLoginData] = useState(
    localStorage.getItem('loginData')
    ? JSON.parse(localStorage.getItem('loginData'))
    : null
  );
  const handleFailure= (result)=>{
    alert(result);
  };
  const handleLogin= async (googleData)=>{
    const y = googleData.getBasicProfile();
    // document.write(y.getId());
    // document.write(y.getImageUrl());
    // document.write(y.getEmail());
    // document.write(y.getName());
    // document.write(y.getGivenName());
    // document.write(y.getFamilyName());
     const res = await fetch('http://localhost:8080/api/v1/auth/signup', {
       method: 'POST',
       body: JSON.stringify({
         username: y.getGivenName(),
         email: y.getEmail(),
         password: "password",  //TODO aggiustare la signup
       }),
       headers: {
         'Content-Type': 'application/json',
       },
     });
     const data = await res.json();
     setLoginData(data);
     localStorage.setItem('loginData', JSON.stringify(data));
  };
  const handleLogout= ()=>{
    localStorage.removeItem('loginData');
    setLoginData(null);
  };
  return (
    <div className="App">
      <Router>
        <Navbar />
        <Routes>
          <Route path='/' exact/>
        </Routes>
      </Router>

      <header className="App-header">
        <h1> React Google Login App</h1>
        <div>
          {
            loginData ? (
              <div>
                <h3>You logged in as {loginData.email}</h3>
                <button onClick={handleLogout}>Logout</button>
              </div>
            ):(
              <GoogleLogin
            clientId={process.env.REACT_APP_GOOGLE_CLIENT_ID}
            buttonText="Login with Google"
            onSuccess={handleLogin}
            onFailure={handleFailure}
            cookiePolicy={'single_host_origin'}
          ></GoogleLogin>
            )
          }        
        </div>
      </header>
    </div>
  );
}

export default App;

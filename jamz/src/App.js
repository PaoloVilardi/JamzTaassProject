import './App.css';
import Navbar from './components/Navbar';
import {BrowserRouter as Router, Routes, Route} 
from 'react-router-dom';
import Home from './components/pages/Home';
import SignUp from './components/SignUp';
import Posts from './components/Posts';
import Profiles from './components/Profiles';
import MyProfile from './components/MyProfile';
import MyProfileInvitations from './components/MyProfileInvitations';


function App() {
    return (
    <div className="App">
      <Router>
        <Navbar/>
        <Routes>
          <Route path='/' exact element=
          {<Home/>} />
          <Route path='/home' exact element=
          {<Home/>} />
          <Route path='/profiles' exact element= //TODO CAPIRE IL PATH DI ROUTE, SE CAMBIA, NON CARICA I PROFILI
          {<Profiles/>} />
          <Route path='/posts' exact element=
          {<Posts/>} />
          <Route path='/sign-up' exact element=
          {<SignUp/>} />
          <Route path='/myprofile' exact element=
          {<MyProfile/>} />
          <Route path='/profile_details' exact element=
          {<MyProfile/>}/>
          <Route path='/profile_details#my_invitations' exact element=
          {<MyProfileInvitations/>} />
        </Routes>
      </Router>

    </div>
  );
}

export default App;

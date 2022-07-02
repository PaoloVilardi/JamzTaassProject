import React,{useState, useEffect} from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Button } from './Button';
import { v4 as uuidv4 } from 'uuid';
import './Navbar.css';
import authService from './services/auth.service';


//TODO aggiustare il menu quando si diminuisce la pagina

function Navbar() {
    const [click, setClick] = useState(false);
    const [button, setButton] = useState(true);
    const [loggedIn, setLoggedIn] = useState(false);

    const handleClick = () => setClick(!click); 
    const closeMobileMenu = () => setClick(false);

    const navigate = useNavigate();

    const showButton = () => {
        if(window.innerWidth <= 960){
            setButton(false);
        } else {
            setButton(true);
        }
    };
    const isLogged = () => {
        console.log(authService.getUserToken());
        if(authService.getUserToken()){
            setLoggedIn(true);
        } else {
            setLoggedIn(false);
        }
    };

    const handleSignout = async (e) => {
        e.preventDefault();
        try {
            authService.logout();
            navigate("/home");
            window.location.reload();
        } catch (err) {
          console.log(err);
        }
      };

    useEffect(() => {
        isLogged();

        showButton();
      }, []);

    window.addEventListener('resize', showButton);

    return (
        <>
            <nav className='navbar'>
                <div className='navbar-container'>
                    <Link to="/home" className='navbar-logo'>
                        JAMZ <i className='fab fa-typo3'/>
                    </Link>
                    <div className='menu-icon' onClick={handleClick}>
                        <i className={click ? 'fas fa-times': 'fas fa-bars'} />
                    </div>
                        <ul className={click ? 'nav-menu active' : 'nav-menu'}>
                            <li className='nav-item'>
                                <Link to='/home' className='nav-links' onClick={closeMobileMenu}>
                                    Home
                                </Link>
                            </li>
                            <li className='nav-item'>
                                <Link to='/profiles' className='nav-links' onClick={closeMobileMenu}>
                                    Profiles
                                </Link>
                            </li>
                            <li className='nav-item'>
                                <Link to='/posts' className='nav-links' onClick={closeMobileMenu}>
                                    Posts
                                </Link>
                            </li>
                            <li className='nav-item'>
                                <Link to='/sign-up' className='nav-links-mobile' onClick={closeMobileMenu}>
                                    Sign Up
                                </Link>
                            </li>
                            <li className='nav-item'>
                                {loggedIn ? <Link to={{pathname: "/myprofile", key: uuidv4()}} className='nav-links' onClick={() => {window.location.href="/myprofile"}}> My profile </Link> 
                                // TODO MODIFICARE LINK PER PORTARE A MY_PROFILE
                                : <div/>}                        
                            </li>
                        </ul>
                        {button && loggedIn ? <Button buttonStyle='btn--outline' url_link="/home" onClick={((e) => handleSignout(e))}>Sign Out</Button>
                                             : <Button buttonStyle='btn--outline' url_link="/sign-up">Sign In/Sign Up</Button>}
                </div>
            </nav>
        </>
    )
}

export default Navbar;
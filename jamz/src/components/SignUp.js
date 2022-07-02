import React, {useState, useEffect, useReducer } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import AuthService from "./services/auth.service";
import GoogleLogin from 'react-google-login'
import FacebookLogin from 'react-facebook-login';
import './SignUp.css';
import { toBeEmpty } from "@testing-library/jest-dom/dist/matchers";


//TODO NAVBAR MESSA MALE A CAUSA DEL CSS -> RISOLVERLO
//TODO CONTROLLARE CODICE SU GIT E RISOLVERE FORM VALIDATION

 

function SignUp() {
    
    useEffect(() => {
        console.log("name = " + name)
      })
    
    const { register, formState: {errors} } = useForm();


    const [name, setName] = useState("");
    const [surname, setSurname] = useState(""); 
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [instrument, setInstrument] = useState("");

    const navigate = useNavigate();

    const handleClickSignUp = (e) => {
        var container = document.getElementById('sign-container');
        container.classList.add("right-panel-active");
    }

    const handleClickSignIn = (e) => {
        var container = document.getElementById('sign-container');
        container.classList.remove("right-panel-active");
    }

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            console.log(username, password);
        await AuthService.login(username, password).then(
            () => {
            navigate("/home"); //modificare indirizzo
            window.location.reload();
            },
            (error) => {
            alert("Errore nel login. Prova a inserire nuovamente i dati assicurandoti che siano corretti!");
            console.log(error);
            }
        );
        } catch (err) {
        console.log(err);
        }
    };

    const handleSignup = async (e) => {
        e.preventDefault();
        try {
            if(name == ""){
                setName(document.getElementById("name_input").value);
            }
            if(surname == "") {
                setSurname(document.getElementById("surname_input").value);
            }
            if(email == ""){
                setEmail(document.getElementById("email_input").value);
            }
          await AuthService.signup(name, surname, username, email, password, instrument).then(
            (response) => {
                if(response){
                    navigate("/sign-up");
                    if(!alert('Utente registrato correttamente!')){window.location.reload();}
                } else {
                    alert("Errore nella Sign Up. Prova a inserire nuovamente i dati assicurandoti che siano corretti!");
                }
            },
            (error) => {
                alert("Errore nella Sign Up. Prova a inserire nuovamente i dati assicurandoti che siano corretti!");
                console.log(error);
            }
          );
        } catch (err) {
          console.log(err);
        }
      };

    const handleGoogleRegistration= async (googleData)=>{
        const y = googleData.getBasicProfile();

        setName(y.getGivenName());
        setSurname(y.getFamilyName());
        setEmail(y.getEmail());
    };

    const handleGoogleFailure= (result)=>{
        console.log(result)
    };

    // const handleFacebookRegistration = async(facebookData) => { 
    //     console.log(facebookData)
    //     if (facebookData.accessToken) {
    //         var fullName = facebookData.name.split(" ");
    //         //setMyData(fullName[0], fullName[1], facebookData.email);
    //         setName(fullName[0]);          
    //         setSurname(fullName[1]);
    //         setEmail(facebookData.email);
    //         forceUpdate();          
            
    //     } else {
    //         console.log("FACEBOOK ACCESS TOKEN NOT FOUND!");
    //     }
    // };

    const handleFacebookRegistration = response => {
        console.log(response);
        if (response.accessToken) {
            var fullName = response.name.split(" ");
            var email = response.email;
            //setMyData(fullName[0], fullName[1], facebookData.email);
            // setName(fullName[0]);          
            // setSurname(fullName[1]);
            // setEmail(response.email);
            document.getElementById("name_input").value = fullName[0];
            document.getElementById("surname_input").value = fullName[1];
            document.getElementById("email_input").value = email;
            setName(fullName[0]);          
            setSurname(fullName[1]);
            setEmail(response.email);
            //forceUpdate();          
            
        } else {
            console.log("FACEBOOK ACCESS TOKEN NOT FOUND!");
        }
    }
    

    // function setMyData(dataName, dataSurname, dataEmail) {
    //     setName(dataName);          
    //     setSurname(dataSurname);
    //     setEmail(dataEmail)
    // }


    return (
        <>
        <div className="sign-page-container">
            <div class="sign-container" id="sign-container">
                <div class="form-container sign-up-container">
                    <form action="#"  onSubmit={handleSignup}>
                        <h1>Create Account</h1>
                        <div class="social-container">
                            <GoogleLogin
                                clientId={process.env.REACT_APP_GOOGLE_CLIENT_ID}
                                buttonText="SignUp with Google"
                                onSuccess={handleGoogleRegistration}
                                onFailure={handleGoogleFailure}
                                cookiePolicy={'single_host_origin'}
                            ></GoogleLogin>
                            <FacebookLogin
                                appId="1687423581616899"
                                autoLoad={false}
                                textButton="SignUp with Facebook"
                                fields="name,email,picture"
                                callback={handleFacebookRegistration}
                                // cssClass="my-facebook-button-class"
                                icon="fa-facebook"
                            />
                            <a href="#" class="social"><i class="fab fa-facebook-f"></i></a>
                            <a href="#" class="social"><i class="fab fa-google-plus-g"></i></a>
                            <a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
                        </div>
                        <span>or use your email for registration</span>
                        <input type="text" placeholder="Name" id="name_input" name="name" value={name} onChange={(e) => setName(e.target.value)}/>
                        <input type="text" placeholder="Surname" id="surname_input" name="surname" value={surname} onChange={(e) => setSurname(e.target.value)} />
                        <input type="text" placeholder="Username" name="username" value={username} onChange={(e) => setUsername(e.target.value)} />
                        <input type="email" placeholder="Email" id="email_input" name="email" value={email} onChange={(e) => setEmail(e.target.value)}/>
                        <input type="password" placeholder="Password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                        <input type="text" placeholder="Instrument" id="instrument_input" name="instrument" value={instrument} onChange={(e) => setInstrument(e.target.value)}/>
                        <button className="sign-up-button" type="submit">Sign Up</button>
                    </form>
                </div>
                <div class="form-container sign-in-container">
                    <form action="#" onSubmit={handleLogin}> 
                        <h1>Sign in</h1>
                        <div class="social-container">
                            <a href="#" class="social"><i class="fab fa-facebook-f"></i></a>
                            <a href="#" class="social"><i class="fab fa-google-plus-g"></i></a>
                            <a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
                        </div>
                        <span>or use your account</span>
                        <input type="text" placeholder="Username" name="username" value={username} onChange={(e) => setUsername(e.target.value)} />
                        <input type="password" placeholder="Password" name="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                        <button className="sign-in-button" type="submit">Sign In</button>
                    </form>
                </div>
                <div class="overlay-container">
                    <div class="overlay">
                        <div class="overlay-panel overlay-left" id="overlay-panel-overlay-left">
                            <h1>Welcome Back!</h1>
                            <p>To keep connected with us please login with your personal info</p>
                            <button class="ghost" id="signIn" onClick={((e) => handleClickSignIn(e))}>Sign In</button>
                        </div>
                        <div class="overlay-panel overlay-right">
                            <h1>Hello, Friend!</h1>
                            <p>Enter your personal details and start journey with us</p>
                            <button class="ghost" id="signUp" onClick={((e) => handleClickSignUp(e))}>Sign Up</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </>
    )
    
}

export default SignUp;
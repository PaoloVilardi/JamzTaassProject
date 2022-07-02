import axios from "axios";

const API_URL = "api/v1/auth";

const signup = (name, surname, username, email, password, instrument) => {
  console.log(name, surname, username, email, password, instrument)
  return axios
    .post(API_URL + "/signup", {
      name,
      surname,
      username,
      email,
      password,
      instrument,
    })
    .then((response) => {
      if (response) {
        console.log(response);
        //localStorage.setItem("user", JSON.stringify(response.data));
        return response;
      }
    })
    .catch((error) => {
      console.error(error)
      return null;
    })

};

const login = (username, password) => {
  return axios
    .post(API_URL + "/login", {
      username,
      password,
    })
    .then((response) => {
      if (response.data.access_token) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    });
};

const logout = () => {
  if(localStorage.getItem("user")){
    localStorage.removeItem("user");
    return true;
  }
};

const getCurrentUser = () => {
  if(localStorage.getItem("user")){
    console.log("current user is " + localStorage.getItem("user"));
    return JSON.parse(localStorage.getItem("user")).username;
  } else {
    return null;
  }
};

const getUserToken  = () => {
  if(localStorage.getItem("user")){
    console.log("current user token is " + JSON.parse(localStorage.getItem("user")).access_token);
    return JSON.parse(localStorage.getItem("user")).access_token;
  } else {
    return null;
  }
};

const authService = {
  signup,
  login,
  logout,
  getCurrentUser,
  getUserToken,
};

export default authService;

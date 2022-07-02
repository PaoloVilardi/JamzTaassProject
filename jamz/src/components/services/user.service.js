import axios from "axios";
import authService from "./auth.service";


//inserire dettagli delle MIE richieste(quindi cambiare /public e /private e implementare le funzioni che servono alla mia app)
const API_URL = "api/v1/user";

const getAllPublicUsers = () => {
  return axios.get(API_URL + "/list", {
    headers: {
      'Authorization': "Bearer " + authService.getUserToken()
    }
  })
  .then((res) => {
    console.log(res.data);
    return res.data;
  })
  .catch((error) => {
    console.error(error)
  })
};

const getMyProfile = () => {
  return axios.get(API_URL + "/my_profile", {
    headers: {
      'Authorization': "Bearer " + authService.getUserToken()
    }
  })
  .then((res) => {
    console.log("my_profile data: " + res.data);
    return res.data;
  })
  .catch((error) => {
    console.error("error: " + error)
  })
};

const getUserByUsername = (username) => {
  return axios.post(API_URL + "/get_profile", {
    username,
  })
  .then((res) => {
    console.log("get_profile data: " + res.data);
    return res.data;
  })
  .catch((error) => {
    console.error("error: " + error)
  })
};

const updateMyProfile = (bio, location, available, instrumentList, tagList) => {
  return axios.post(API_URL + "/update", {
    bio,
    location,
    available,
    instrumentList,
    tagList
  },  
  {
    headers: {
      'Authorization': "Bearer " + authService.getUserToken()
    }
  })
  .then((res) => {
    console.log("update response: " + res.data);
    return res.data;
  })
  .catch((error) => {
    console.error("error: " + error)
  })
};



const userService = {
  getAllPublicUsers,
  getUserByUsername,
  getMyProfile,
  updateMyProfile,
};

export default userService;

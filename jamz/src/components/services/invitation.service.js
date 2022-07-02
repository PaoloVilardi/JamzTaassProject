import axios from "axios";
import authService from "./auth.service";


//inserire dettagli delle MIE richieste(quindi cambiare /public e /private e implementare le funzioni che servono alla mia app)
const API_URL = "api/v1/invitation";

const getAllPublicInvitations = () => {
  return axios.get(API_URL + "/list", {
    // headers: {
    //   'Authorization': "Bearer " + authService.getUserToken()
    // }
  })
  .then((res) => {
    console.log(res.data);
    return res.data;
  })
  .catch((error) => {
    console.error(error)
  })
};

const apply = (id) => {
  console.log(id);
  return axios
    .post(API_URL + "/apply", 
    {
      id
    },
    { 
      headers: {
        'Authorization': "Bearer " + authService.getUserToken()
      },
      
    })
    .then((response) => {
      console.log("response of invitation_service for APPLY is: " + response);
      return true;
    })
    .catch((error) => {
      console.error(error)
      return null;
    });
};

const isApplied = (id) => {
  return axios.post(API_URL + "/is_applied",{
    id
  },  
  {
    headers: {
      'Authorization': "Bearer " + authService.getUserToken()
    }
  })
  .then((res) => {
    console.log("applied res_data: " + res.data);
    return res.data;
  })
  .catch((error) => {
    console.error(error)
  })
};

const addInvitation = (title, description, genre, instrument, tagList, invitationType, creator) => {
  return axios
    .post(API_URL + "/create", 
    {
      title, description, genre, instrument, tagList, invitationType, creator
    },
    { 
      headers: {
        'Authorization': "Bearer " + authService.getUserToken()
      },
      
    })
    .then((response) => {
      console.log("response of invitation_service for ADD is: " + response);
      return response;
    })
    .catch((error) => {
      console.error(error)
      return null;
    });
};

const closeInvitation = (id) => {
  return axios.post(API_URL + "/close",
  {
    id,
  },
  { 
    headers: {
      'Authorization': "Bearer " + authService.getUserToken()
    },
    
  })
  .then((response) => {
    console.log("response of invitation_service for CLOSE is: " + response);
    return response;
  })
  .catch((error) => {
    console.error(error)
    return null;
  });
}

const deleteInvitation = (id) => {
  return axios.post(API_URL + "/delete",
  {
    id,
  },
  { 
    headers: {
      'Authorization': "Bearer " + authService.getUserToken()
    },
    
  })
  .then((response) => {
    console.log("response of invitation_service for DELETE is: " + response);
    return response;
  })
  .catch((error) => {
    console.error(error)
    return null;
  });
}

const modifyInvitationAcceptance = (id, candidate) => {
  return axios.post(API_URL + "/modify_acceptance",
  {
    id, candidate
  },
  { 
    headers: {
      'Authorization': "Bearer " + authService.getUserToken()
    },
    
  })
  .then((response) => {
    console.log("response of invitation_service for MODIFY ACCEPTANCE is: " + response);
    return response;
  })
  .catch((error) => {
    console.error(error)
    return null;
  });
}

const removeApply = (id) => {
  return axios.post(API_URL + "/remove_apply",
  {
    id,
  },
  { 
    headers: {
      'Authorization': "Bearer " + authService.getUserToken()
    },
    
  })
  .then((response) => {
    console.log("response of invitation_service for REMOVE_APPLY is: " + response);
    return response;
  })
  .catch((error) => {
    console.error(error)
    return null;
  });
}

const filteredSearch = (invitationType, genre, instrument, tag_list, open) => {
  if(authService.getUserToken()){
    return axios.post(API_URL + "/filtered_search",
    {
      invitationType, genre, instrument, tag_list, open,
    },
    { 
      headers: {
        'Authorization': "Bearer " + authService.getUserToken()
      },
      
    })
    .then((response) => {
      console.log("response of invitation_service for FILTERED_SEARCH is: " + response.data);
      return response.data;
    })
    .catch((error) => {
      console.error(error)
      return null;
    });
  } else {
    return axios.post(API_URL + "/filtered_search",
    {
      invitationType, genre, instrument, tag_list, open,
    },)
    .then((response) => {
      console.log("response of invitation_service for FILTERED_SEARCH is: " + response.data);
      return response.data;
    })
    .catch((error) => {
      console.error(error)
      return null;
    });
  }
}

const getInvitationsListFromIds = async(invIdList) => {
  try{
    if(invIdList){
      const resp = await axios.post(API_URL + "/list_by_ids",
      {
        invIdList
      },
      { 
        headers: {
          'Authorization': "Bearer " + authService.getUserToken()
        },
      })
      console.log("response of invitation_service for getInvitationsFromIds is: " + resp);
      console.log("response.data of invitation_service for getInvitationsFromIds is: " + resp.data);
      return resp;
    } else {
      return [];
    }
  } catch(err) {
    console.error(err);
    return null;
  }
}


const invitationService = {
  getAllPublicInvitations,
  apply,
  isApplied,
  addInvitation,
  closeInvitation,
  modifyInvitationAcceptance,
  deleteInvitation,
  removeApply,
  filteredSearch,
  getInvitationsListFromIds,
};

export default invitationService;

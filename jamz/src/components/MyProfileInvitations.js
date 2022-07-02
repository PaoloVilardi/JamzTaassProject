import React from 'react';
import { Link } from 'react-router-dom';
import '../App.css';
import './MyProfile.css';
import './MyProfileInvitations.css';
import { v4 as uuidv4 } from 'uuid';
import invitationService from './services/invitation.service';

//TODO cambiare i button
function MyProfileInvitations(props) {

  const handleCloseInvitation = (id) => {
    invitationService.closeInvitation(id).then(
      (response) => {
      console.log("response is " + response);             
      },
      (error) => {
      console.log(error);
      }
    );
    props.updateFromChild();
  }

  const handleDeleteInvitation = (id) => {
    invitationService.deleteInvitation(id).then(
      (response) => {
      console.log("response is " + response);             
      },
      (error) => {
      console.log(error);
      }
    );
    props.updateFromChild();

  }

  const handleRemoveApply = (id) => {
    invitationService.removeApply(id).then(
      (response) => {
      console.log("response is " + response);             
      },
      (error) => {
      console.log(error);
      }
    ); 
    props.updateFromChild();  
  }

  const handleModifyAcceptance = (id, candidate) => {
    invitationService.modifyInvitationAcceptance(id, candidate).then(
      (response) => {
        console.log("response is " + response);
      },
      (error) => {
        console.log(error);
      }
    );
    props.updateFromChild();
  }

  return (
    <>
    <div className='my-profile-invitation-inline'>
     <h6 className={props.open ? 'my-profile-invitation-title-open' : 'my-profile-invitation-title-closed'}>{props.title} </h6>
     <p>&#40;<strong>Genre: </strong>{props.genre},</p>
     <p><strong>Instrument: </strong>{props.instrument},</p>
     <p><strong>created: </strong>{props.localDateTime},</p> 
     <p><strong>Type: </strong>{props.invitationType}</p>
     <p><strong>by: </strong>&#64;
          <Link to={{
            pathname: "/profile_details?user=" + `${props.creator}`,
            key: uuidv4(),
            }} style={{ textDecoration: 'none', color: 'black'}} onClick={() => {window.location.href="/profile_details?user=" + `${props.creator}`}}>{props.creator ? props.creator : 'null'}
          </Link>&#41;</p> <br/>    
    <div className='my-profile-invitation-tagList'> 
        <h6>TAGS: </h6>
        <ul>
            {props.tagList && props.tagList.map((tag, index) => (
                <li key={tag + index}>{tag}</li>
            ))}
        </ul>
      </div>
     
     {props.type && props.type==="invitation" && !props.open ? <button className='my-profile-invitation-delete-button' onClick={() => handleDeleteInvitation(props.id)}>Delete</button> : <div/>}
     {props.type && props.type==="apply" ? <button className='my-profile-invitation-remove-apply-button' onClick={() => handleRemoveApply(props.id)}>Remove</button> : <div/>}
     <strong> Candidates: </strong>
     {props.candidateList ?
     <div className='my-profile-invitation-candidateList'> 
      <ul>{props.candidateList.map((candidate) => (
        <li>
          {!props.open && props.acceptanceStatus===candidate 
            ? 
            <Link to={{
              pathname: "/profile_details?user=" + `${candidate}`,
              key: uuidv4(),
              }} style={{ textDecoration: 'none', color: 'green', fontWeight: 'bold'}} onClick={() => {window.location.href="/profile_details?user=" + `${candidate}`}}>{candidate + " [ACCEPTED]"}
            </Link>
            : 
            <Link to={{
              pathname: "/profile_details?user=" + `${candidate}`,
              key: uuidv4(),
              }} style={{ textDecoration: 'none', color: 'black'}} onClick={() => {window.location.href="/profile_details?user=" + `${candidate}`}}>{candidate}
            </Link>
          }
          
          {props.open && props.type==="invitation" ? <img className='my-profile-invitation-acceptance-button' alt='Accept apply' src='images/approve_icon_alt.png' onClick={() => handleModifyAcceptance(props.id, candidate)}></img> : <div/>}
        </li>
      ))}</ul>
     </div>
    :
    <p>&#91;&#93;</p>}   
     
     <br></br><h6>DESCRIPTION: </h6><br></br><span className='my-profile-invitation-description'>{props.description}</span>
     <br></br>
     {props.type && props.type==="invitation" && props.open ? <button className='my-profile-invitation-close-button' onClick={() => handleCloseInvitation(props.id)}>Close</button> : <div/>}
     <hr class="solid"></hr>    
     </div>    
    </>
    
  );
}

export default MyProfileInvitations;



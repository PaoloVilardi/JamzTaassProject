import React,{useState, useEffect} from 'react';
import { Link } from 'react-router-dom';
import invitationService from './services/invitation.service';

function PostItem(props) {
  const [loggedIn, setLoggedIn] = useState(props.loggedIn);
  const [applied, setApplied] = useState(true);
  const [open, setOpen] = useState(props.open);
  const [userLogged, setUserLogged] = useState(props.userLogged);
  

  const isApplied = async() => {
    if(loggedIn){
      var isApplied = await invitationService.isApplied(props.id)
      console.log("IS APPLIED (var)= " + isApplied);
      if(isApplied){
        setApplied(true);
      } else {
        console.log("CASE SET APPLIED FALSE");
        setApplied(false);
      }
      console.log("IS APPLIED = " + applied);
    }
  }

  const isOpen = () => {
    setOpen(props.open);
  }

  useEffect(() => {
    console.log("creator: " + props.creator);
    isOpen();
    isApplied();
  }, []);

  const handleApply = async (e) => {
    e.preventDefault();
    try {
    await invitationService.apply(props.id).then(
        (response) => {
          console.log("response is " + response);
          //TODO aggiungere nell'applicazione feedback(alert?) per ogni azione, es: login successo/fallimento, modifica effettuata etc etc
          //TODO spostare isLogged in posts
          if(response){
            //window.location.reload();
            alert("Apply andata a buon fine");
            setApplied(true);
          } else {
            alert("Non è stato possibile portare a termine l'apply, post eliminato dal creatore o hai già fatto l'apply per questo Post!");
          }
        },
        (error) => {
        console.log(error);
        }
    );
    } catch (err) {
    console.log(err);
    }
};
  return (
    <>
      <li className='posts__item'>
        <div className='posts__item__info'>
          {props.open ? <h4 className='posts__item__open'>[OPEN]</h4> : <h4 className='posts__item__closed'>[CLOSED]</h4>}
          <div className='posts__item__creator'>          
          <Link to={{
                  pathname: "/profile_details?user=" + `${props.creator}`,
              }} style={{ textDecoration: 'none', color: 'black'}} onClick={() => {window.location.href="/profile_details?user=" + `${props.creator}`}}><h4>CREATOR:</h4><p>{props.creator}</p><img src="images/user-avatar.png"/></Link>
          </div> 
          <h3 className='posts__item__datetime'>{props.localDateTime}</h3>
          <div className='posts__item__title'>
            <h1>{props.title}</h1>
          </div>
          <div className='posts__item__description'>
            <p>{props.description}</p>
          </div>
          <div className='posts__item__footer'>
            <div className='posts__item__genre'>
              <h6> Genre: {props.genre}</h6>
            </div>
            <div className='posts__item__type'>
              <h6> Type: {props.invitationType}</h6>
            </div>
            <div className='posts__item__instrument'>
              <h6> Instrument: {props.instrument}</h6>
            </div>
            <div className='posts__item__tagList'> 
              <h6>TAGS: </h6>
              <ul>
                    {props.tagList && props.tagList.map((tag, index) => (
                        <li key={tag + index}>{tag}</li>
                    ))}
              </ul>
            </div>
            <div className='posts__item__apply__button'>
              {loggedIn && props.open && !applied && userLogged !== props.creator ? <button class="btn_primary" onClick={((e) => handleApply(e))}>Apply</button>: <div/>}
            </div>
          </div>
             {/*TODO TOGLIERE BOTTONE APPLY SULLE PROPRIE INVITATION */}
            {/* TODO IMPOSTARE LINK APPLY E IN GENERALE PRENDERE CORRETTAMENTE GLI ALTRI DATI, AGIRE SU POST.JS(?) */}
        </div>
      </li>
    </>
  );
}

export default PostItem;
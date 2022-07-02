import React, { useState } from 'react';
import '../App.css';
import invitationService from './services/invitation.service';
import { WithContext as ReactTags } from 'react-tag-input';

//TODO cambiare i button
function AddInvitationForm(props) {

    
    const Keys = {
        TAB: 9,
        SPACE: 32,
        COMMA: 188,
        ENTER: 13,
        SEMICOLON: 59,
    };

    const formatTags = (list) => {
        var newList = []
        {list && list.map((element, index) => (
            newList.push({id: index.toString(), text: element})
        ))}
        return newList;
    }

    const handleCloseForm = () => {
        props.updateFromForm();
    }

    const handleDeleteTag = i => {
        setTagList(tagList.filter((tag, index) => index !== i));
      };

    const handleAdditionTag = tag => {
        setTagList([...tagList, tag]);
    };

    const handleAddInvitation = (e) => {
        e.preventDefault();
        console.log(props.creator);
        console.log(typeof props.creator);
        invitationService.addInvitation(title, description, genre, instrument, tagList.map((tag) => (tag.text)), invitationType, props.creator.username).then(response => {        
            if(response){
                alert("Invitation created successfully!");
                props.updateFromForm();
            } else {
                alert("Something went wrong! Try again or contact the technical support");
            }
        });
    }

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [genre, setGenre] = useState("");
    const [instrument, setInstrument] = useState("");
    const [tagList, setTagList] = useState(formatTags(props.tagList));
    const [invitationType, setInvitationType] = useState("gig");  

  return (
    <>
        <div className='my-profile-add-invitation-div'> 
            <form className='my-profile-add-invitation-form' onSubmit={(e) => handleAddInvitation(e)} id= "add-app">
                <button id='my-profile-close-form-button' onClick={() => handleCloseForm()}></button>
        
                <div className='my-profile-add-invitation-form-text'>
                    <label>Title: </label>
                    <input type="text" onChange={(e) => setTitle(e.target.value)}/>
            
                    <label>Description: </label>
                    <textarea type="text" onChange={(e) => setDescription(e.target.value)}/>
            
                    <label>Genre: </label>
                    <input type="text" onChange={(e) => setGenre(e.target.value)}/>

                    <label>Instrument: </label>
                    <input type="text" onChange={(e) => setInstrument(e.target.value)}/>

                    <label>Tags: </label>
                    <ReactTags
                            tags={tagList}
                            delimiters={[Keys.COMMA, Keys.ENTER, Keys.SEMICOLON, Keys.SPACE, Keys.TAB]}
                            placeholder="Add a new tag"
                            handleAddition={handleAdditionTag}
                            handleDelete={handleDeleteTag}
                            maxLength={40}
                            inputFieldPosition='inline'                   
                        />

                    <label>Type: </label>
                    <select defaultValue={"temp"} onChange={(e) => setInvitationType(e.target.value)}>
                        <option value="gig"> gig</option>
                        <option value="jam"> jam</option>
                        <option value="band member"> band member</option>
                    </select>
                    {/* <input type="text" onChange={(e) => setInvitationType(e.target.value)}/> */}
            
                    <button type='submit'>Create</button>
                </div>
            </form>
        </div>
    </>
    
  );
}

export default AddInvitationForm;



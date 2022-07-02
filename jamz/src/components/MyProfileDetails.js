import React, { useState, useRef, useEffect } from 'react';
import '../App.css';
import './MyProfile.css';
import userService from './services/user.service';
import { WithContext as ReactTags } from 'react-tag-input';

//TODO cambiare i button


function MyProfileDetails(props) {

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


    const [location, setLocation] = useState(props.location);
    const [bio, setBio] = useState(props.bio);
    const [available, setAvailable] = useState(props.available);
    const [instrumentTags, setInstrumentTags] = useState(formatTags(props.instrumentList)); 
    const [tagListTags, setTagListTags] = useState(formatTags(props.tagList));
    const [edit, setEdit] = useState(false);
    const [editable, setEditable] = useState(props.editable);
    const editMode = useRef(false);


    const handleDeleteInstrument = i => {
        setInstrumentTags(instrumentTags.filter((instrumentTag, index) => index !== i));
      };

    const handleAdditionInstrument = instrumentTag => {
        setInstrumentTags([...instrumentTags, instrumentTag]);
    };

    const handleDeleteTag = i => {
        setTagListTags(tagListTags.filter((tagListTag, index) => index !== i));
      };

    const handleAdditionTag = tagListTag => {
        setTagListTags([...tagListTags, tagListTag]);
    };

    const HandleCheckboxChange = async(e) => {   
        var av = e.target.checked;
        console.log("AV = " + av)
        setAvailable(av);
    }


    const HandleSubmit = async(e) => {
        if(editable){
            console.log("location = " + location);
            console.log("bio = " + bio);
            console.log("available = " + available);
            console.log("instruments = " + instrumentTags);
            instrumentTags.map((instrument) => (
                console.log(instrument)
            ));
            console.log("tags = " + tagListTags);
            e.preventDefault();   
            try {
                editMode.current = false;
                console.log("SUBMIT");
                await userService.updateMyProfile(bio, location, available, instrumentTags.map((instrument) => (instrument.text)), tagListTags.map((tag) => (tag.text))).then(
                    (response) => {
                    console.log("response is " + response);             
                    },
                    (error) => {
                    console.log(error);
                    }
                );
                setEdit(false);
                props.updateFromChild();
            } catch (err) {
                console.log(err);
            }
        }
    }
    
    const HandleEdit = async(e) => {     
        e.preventDefault(); 
        editMode.current = true;
        setEdit(true);
        console.log("EDIT");
        console.log(editMode.current);
    }

    const HandleCloseEdit = async(e) => {
        e.preventDefault();
        editMode.current = false;
        setEdit(false);
    }


  return (
    <>
    {editable && editMode.current ? 
        <>
        <button class="btn_primary" id='my-profile-edit-button'>Edit</button>
        <form action="#" onSubmit={HandleSubmit}>
            <button type='submit' class="btn_primary" id='my-profile-save-button'>Save</button>
            <img className='my-profile-close-edit-button' src='images/x_icon.png' onClick={HandleCloseEdit}/>
            <div className='my-profile-full-name'>
                {available ? <h3 className='my-profile-available-true'> AVAILABLE </h3> : <h3 className='my-profile-available-false'> NOT AVAILABLE </h3>}
                <label className='my-profile-available-switch'><input className='my-profile-available-input' type="checkbox" defaultChecked={available} id='myCheck' onChange={((e) => HandleCheckboxChange(e))}/><span className='my-profile-available-slider-round'></span></label>
                {/* TODO MODIFICARE CHECKBOX IN MODO CHE IL VALORE INIZIALE SIA IMPOSTATO A AVAILABLE */}
                <h1 className='my-profile-name'>{props.name} {props.surname}</h1>
            </div>
            <h2 className='my-profile-username'>&#40;&#64;{props.username}&#41;</h2>
            <h4>LOCATION: </h4><input type="text" defaultValue={location} className='my-profile-location' onChange={(e) => setLocation(e.target.value)}/>
            <h4>BIO:</h4>
            <textarea className='my-profile-bio-input' defaultValue={bio} onChange={(e) => setBio(e.target.value)}/>
            <div className='my-profile-footer'>
                <div class="my-profile-instruments">
                    <h6>INSTRUMENTS</h6>
                        <ReactTags
                        tags={instrumentTags}
                            delimiters={[Keys.COMMA, Keys.ENTER, Keys.SEMICOLON, Keys.SPACE, Keys.TAB]}
                            placeholder="Add a new instrument"
                            handleAddition={handleAdditionInstrument}
                            handleDelete={handleDeleteInstrument}
                            maxLength={40}
                            inputFieldPosition='bottom'                   
                        />
                </div>
                <div class="my-profile-tags">
                    <h6>TAGS</h6>
                    <ReactTags
                    tags={tagListTags}
                        delimiters={[Keys.COMMA, Keys.ENTER, Keys.SEMICOLON, Keys.SPACE, Keys.TAB]}
                        placeholder="Add a new tag"
                        handleAddition={handleAdditionTag}
                        handleDelete={handleDeleteTag}
                        maxLength={40}
                        inputFieldPosition='bottom'                   
                    />                   
                </div>
            </div>
        </form>
        </> 
    : 
    <>
    {editable ? <div><button class="btn_primary" id='my-profile-edit-button' onClick={HandleEdit}>Edit</button>
    <button type='submit' class="btn_primary" id='my-profile-save-button'>Save</button> </div>: <div/>}
        <div className='my-profile-full-name'>{props.available ? <h3 className='my-profile-available-true'> AVAILABLE </h3> : <h3 className='my-profile-available-false'> NOT AVAILABLE </h3>}<h1 className='my-profile-name'>{props.name} {props.surname}</h1></div>
        <h2 className='my-profile-username'>&#40;&#64;{props.username}&#41;</h2>
        <div className='my-profile-location'>
            <img className='my-profile-location-icon' src='images/location_icon.png'/>
            <h4>{props.location}</h4>
        </div>
        <h4>BIO:</h4>
        <p className='my-profile-bio'>{props.bio}</p>
        <div className='my-profile-footer'>
            <div class="my-profile-instruments">
                <h6>INSTRUMENTS</h6>
                <ul>
                    {props.instrumentList && props.instrumentList.map((instrument, index) => (
                        <li key={instrument + index}>{instrument}</li>
                    ))}
                </ul>
            </div>
            <div class="my-profile-tags">
                <h6>TAGS</h6>
                <ul>
                    {props.tagList && props.tagList.map((tag, index) => (
                        <li key={tag + index}>{tag}</li>
                    ))}
                </ul>
                
            </div>
        </div>
    </>
    }
    
    </>
    
  );
}

export default MyProfileDetails;



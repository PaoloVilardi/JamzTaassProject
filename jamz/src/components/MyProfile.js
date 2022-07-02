import React, { Component } from 'react';
import './MyProfile.css';
import userService from './services/user.service';
import MyProfileInvitations from './MyProfileInvitations';
import MyProfileDetails from './MyProfileDetails';
import AddInvitationForm from './AddInvitationForm';
import invitationService from './services/invitation.service';
import { type } from '@testing-library/user-event/dist/type';
import authService from './services/auth.service';




class MyProfile extends Component{
    constructor(props){
        super(props);
        this.state = {
            id:"",
            name:"",
            surname:"",
            username:"",
            location:"",
            bio:"",
            available: false,
            instrumentList:[],
            tagList:[],
            invitationList:[],
            applyList:[],
            profileDetailsView: null,
            myInvitationsView: null,
            myAppliesView: null,
            openForm: false,
            temp: "",
            myProfileView: false,
            editable: false,
            // editMode: false,

        }
    }

    componentDidMount() {
        console.log("COMPONENT DID MOUNT")
        var queryParams = new URLSearchParams(window.location.search)
        var term = queryParams.get("user")
        console.log(term);
        if(term && term !== authService.getCurrentUser()){ 
            userService.getUserByUsername(term).then(profileData => {
                this.setState({
                    id: profileData.id,
                    name: profileData.name,
                    surname: profileData.surname,
                    username: profileData.username,
                    location: profileData.location,
                    bio: profileData.bio,
                    available: profileData.available,
                    instrumentList: profileData.instrumentList,
                    tagList: profileData.tagList,
                    invitationList: [],
                    applyList: [],
                    profileDetailsView: true,
                    myInvitationsView: false,
                    myAppliesView: false, 
                    openForm: false,
                    myProfileView: false,
                    temp: "",
                    editable: false,
                })
            });
        } else {
            userService.getMyProfile().then(profileData => {
                invitationService.getInvitationsListFromIds(profileData.invitationList).then((invListData) => {
                    invitationService.getInvitationsListFromIds(profileData.applyList).then((applyListData) => {
                        this.setState({
                            id: profileData.id,
                            name: profileData.name,
                            surname: profileData.surname,
                            username: profileData.username,
                            location: profileData.location,
                            bio: profileData.bio,
                            available: profileData.available,
                            instrumentList: profileData.instrumentList,
                            tagList: profileData.tagList,
                            invitationList: invListData ? invListData.data : [],
                            applyList: applyListData ? applyListData.data : [],
                            profileDetailsView: true,
                            myInvitationsView: false,
                            myAppliesView: false, 
                            openForm: false,
                            myProfileView: true,
                            temp: "",
                            editable: true,
                        })
                    })
                })
            });
        }
        
        this.state.invitationList.map((invitation) => (
            console.log("TITLE = " + invitation.title)
        ));

    }

    componentDidUpdate(prevProps, prevState) {
        if (prevState.pokemons !== this.state.pokemons) {
          console.log('DID UPDATE')
        }
    }      

    handleEdit = () => {
        this.setState({
            editMode: true,
        })
    }

    handleProfileDetails = () => {
        this.setState({
            profileDetailsView: true,
            myInvitationsView: false,
            myAppliesView: false,
            openForm: false,     
        })
    }

    handleMyInvitations = () => {
        this.setState({
            profileDetailsView: false,
            myInvitationsView: true,
            myAppliesView: false,  
            openForm: false,    
        })
    }

    handleMyApplies = () => {
        this.setState({
            profileDetailsView: false,
            myInvitationsView: false,
            myAppliesView: true, 
            openForm: false,     
        })
    }

    updateFromChild = () => {
        console.log("FORCE UPDATE")      
        this.setState({
            temp: "temp",
        });
        this.componentDidMount();
    }

    updateFromForm = () => {
        console.log("FORCE UPDATE FORM");
        this.setState({
            openForm: false,
        });
        this.componentDidMount();
        
    }

    setOpenForm = () => {
        console.log("ADD");
        this.setState({
            openForm: true,
        })
    }


    
    render(){ 
        const profileDetailsView = this.state.profileDetailsView;
        const myInvitationsView = this.state.myInvitationsView;
        const myAppliesView = this.state.myAppliesView;
        let element = [];     
        if(profileDetailsView){
            element = [];
            element.push(<MyProfileDetails
            name= {this.state.name ? `${this.state.name}` : ''}
            surname= {this.state.surname ? `${this.state.surname}` : ''}
            username= {this.state.username ? `${this.state.username}` : ''}
            location= {this.state.location ? `${this.state.location}` : ''}
            bio= {this.state.bio ? `${this.state.bio}` : ''}
            available= {this.state.available ? `${this.state.available}` : ''}
            instrumentList= {this.state.instrumentList ? this.state.instrumentList : ''}
            tagList= {this.state.tagList ? this.state.tagList : ''}
            updateFromChild = {this.updateFromChild}
            editable = {this.state.editable}
            // editMode= {this.state.editMode ? `${this.state.editMode}` : false}                
            />)
        } else if(myInvitationsView){
            element = [];
            {this.state.invitationList && this.state.invitationList.map((invitation) =>(                                         
                element.push(<MyProfileInvitations
                id={invitation.id? `${invitation.id}` : ''}
                title= {invitation.title ? `${invitation.title}` : ''}
                description= {invitation.description ? `${invitation.description}` : ''}
                genre= {invitation.genre ? `${invitation.genre}` : ''}
                instrument= {invitation.instrument ? `${invitation.instrument}` : ''}
                tagList= {invitation.tagList ? invitation.tagList : ''}
                invitationType= {invitation.invitationType ? `${invitation.invitationType}` : ''}
                localDateTime= {invitation.localDateTime ? `${invitation.localDateTime}` : ''}
                open= {invitation.open ? invitation.open : false}
                creator= {invitation.creator ? `${invitation.creator}` : ''}
                acceptanceStatus = {invitation.acceptanceStatus ?`${invitation.acceptanceStatus}` : ''}
                candidateList={invitation.candidateList ? invitation.candidateList : null}
                type={"invitation"}
                updateFromChild= {this.updateFromChild}
                                           
            />)                
            ))}             
        } else if(myAppliesView){
            element = [];
            {this.state.applyList && this.state.applyList.map((apply) =>(                            
                element.push(<MyProfileInvitations
                id={apply.id? `${apply.id}` : ''}
                title= {apply.title ? `${apply.title}` : ''}
                description= {apply.description ? `${apply.description}` : ''}
                genre= {apply.genre ? `${apply.genre}` : ''}
                instrument= {apply.instrument ? `${apply.instrument}` : ''}
                tagList= {apply.tagList ? apply.tagList : ''}
                invitationType= {apply.invitationType ? `${apply.invitationType}` : ''}
                localDateTime= {apply.localDateTime ? `${apply.localDateTime}` : ''}
                open= {apply.open ? `${apply.open}` : ''}
                creator= {apply.creator ? `${apply.creator}` : ''}
                candidateList={apply.candidateList ? apply.candidateList : null}
                acceptanceStatus = {apply.acceptanceStatus ?`${apply.acceptanceStatus}` : ''}   
                type={"apply"}     
                updateFromChild= {this.updateFromChild}                                 
            />)
            ))}
            
        }

        return(
            <>
            <div className='my-profile-page'>
                <div class="my-profile-container">
                    <header className='my-profile-header'>
                        <img src="images/user-avatar.png"/>                      
                    </header>
                    <nav className='my-profile-nav'>
                        {/* TODO INSERIRE QUI I RIFERIMENTI AD ALTRE PAGINE(es. my_invitations e my_applies)*/}
                        <a href="#" aria-current="page" onClick={this.handleProfileDetails}>Profile Details</a>
                        {this.state.myProfileView ? 
                        <>
                            <a href="#my_invitations" onClick={this.handleMyInvitations}>My Invitations</a>
                            <a href="#my_applies" onClick={this.handleMyApplies}>My Applies</a>
                        </>  
                        : 
                        <div/>}
                        
                    </nav>
                    {console.log("element in RETURN = " + element)}                 
                    <main className='my-profile-main'>
                    {element && element.map((el) =>(                            
                                el
                            ))}
                    {myInvitationsView ? 
                    <button class="btn_primary" id='my-profile-add-invitation-button' onClick={this.setOpenForm}>Add</button> 
                    :                  
                    <div/>}
                    {this.state.openForm ? 
                        <AddInvitationForm
                        creator={{id: this.state.id, username: this.state.username}}
                        updateFromForm={this.updateFromForm}
                        />
                        :
                        <div/>
                    }
                    </main>
                          
                </div>
            </div>
            </>
        );
    }
}

export default MyProfile;
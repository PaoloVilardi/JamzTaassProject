import React, {useState, Component} from 'react';
import './Posts.css';
import PostItem from './PostItem.js';
import { useParams } from 'react-router-dom';
import invitationService from './services/invitation.service';
import authService from './services/auth.service';
import { WithContext as ReactTags } from 'react-tag-input';


//TODO risolvere l'allineamento dei PostItem(ora horizontal, voglio vertical)
const Keys = {
    TAB: 9,
    SPACE: 32,
    COMMA: 188,
    ENTER: 13,
    SEMICOLON: 59,
};
class Posts extends Component{

    constructor(props){
        super(props);
        this.state = {
            posts_list:[],
            invitationType: "",
            genre: "",
            instrument: "",
            tagList: [],
            open: true,
            openSearch: false,
        }
    }

    

    componentDidMount() {
        invitationService.getAllPublicInvitations().then(data => {
            this.setState({
            posts_list: data,
            invitationType: "",
            genre: "",
            instrument: "",
            tagList: [],
            open: true,
            openSearch: false,
            })
        });

    }

    isLogged = () => {
        if(authService.getUserToken()){
            return true;
        } else {
            return false;
        }
    };

    getUserLogged = () => {
        return authService.getCurrentUser();
    };

    setInvitationType(type) {
        this.setState({
           invitationType: type, 
        })
    }

    setGenre(inv_genre){
        this.setState({
            genre: inv_genre,
        })
    }

    setInstrument(inv_instrument){
        this.setState({
            instrument: inv_instrument,
        })
    }

    setStatus(status) {
        this.setState({
            open: status === "open" ? true : false
        })
    }

    setOpenSearch = async(e) => {
        e.preventDefault();
        this.setState({
            openSearch: true,
        })
    }

    handleCloseSearch = () => {
        this.setState({
            invitationType: "",
            genre: "",
            instrument: "",
            tagList: [],
            open: true,
            openSearch: false,
            })
    }

    handleDeleteTag = i => {
        this.setState({
            tagList: this.state.tagList.filter((tag, index) => index !== i)
        })       
      };

    handleAdditionTag = tag => {
        this.setState({
            tagList: [...this.state.tagList, tag]
        })      
    };


    handleAdvancedSearch = async(e) => {
        e.preventDefault();
        console.log("TYPE = " + this.state.invitationType);
        console.log("GENRE = " + this.state.genre);
        console.log("INSTRUMENT = " + this.state.instrument);
        console.log("TAGS = " + this.state.tagList.map((tag) => (tag.text)));
        console.log("OPEN = " + this.state.open);
        invitationService.filteredSearch(this.state.invitationType, this.state.genre, this.state.instrument, this.state.tagList.map((tag) => (tag.text)), this.state.open).then(response => {        
            console.log(response);
            console.log(typeof response);
            if(response){
                console.log("posts = " + response.map((el) => (el)));
                this.setState({
                    openSearch: false,
                    posts_list: response,
                })
                console.log("POSTS_LIST = " + this.state.posts_list.map((post) => (post)));
            } else {
                this.setState({
                    openSearch: false,
                    posts_list: [],
                })
            }
        });
        this.setState({
            invitationType: "",
            genre: "",
            instrument: "",
            tagList: [],
            open: true,
            })
        
    }

    render(){ 
        return(
            <div className='posts'>
                <h1>Posts!</h1>
                <button class="btn_primary" id='posts__search__button' onClick={(e) => this.setOpenSearch(e)}>Search</button>
                {this.state.openSearch ? 
                <div className='posts__advanced__search'>
                    <form className='posts__advanced__search__form' onSubmit={(e) => this.handleAdvancedSearch(e)}>
                    <button id='posts__advanced__search__close__button' onClick={() => this.handleCloseSearch()}></button>
                    <label>Type: </label>
                    <select defaultValue={"temp"} onChange={(e) => this.setInvitationType(e.target.value)}>
                        <option hidden> -- select an option -- </option>
                        <option value="gig"> gig</option>
                        <option value="jam"> jam</option>
                        <option value="band member"> band member</option>
                    </select>
                    <label>Genre: </label>
                    <input type="text" onChange={(e) => this.setGenre(e.target.value)}/>
                    <label>Instrument: </label>
                    <input type="text" onChange={(e) => this.setInstrument(e.target.value)}/>                      
                    <label>Tags: </label>
                    <ReactTags
                            tags={this.state.tagList}
                            delimiters={[Keys.COMMA, Keys.ENTER, Keys.SEMICOLON, Keys.SPACE, Keys.TAB]}
                            placeholder="Add a new tag"
                            handleAddition={this.handleAdditionTag}
                            handleDelete={this.handleDeleteTag}
                            maxLength={40}
                            inputFieldPosition='inline'                   
                        />
                    <label>Status: </label>
                    <select defaultValue={"temp"} onChange={(e) => this.setStatus(e.target.value)}>
                        <option value="open"> open</option>
                        <option value="jam"> closed</option>
                    </select>
                    <button id='posts__advanced__search__button' type='submit'>Search</button>
                    </form>                    
                </div>
                :
                <div/>              
                } 
                <div className='posts__container'>
                    <div className='posts__wrapper'>
                        <ul className='posts__items'>
                            {this.state.posts_list && this.state.posts_list.map((post) =>(
                                <PostItem
                                loggedIn={this.isLogged()}
                                userLogged={this.getUserLogged()}
                                creator= {post.creator ? `${post.creator}` : ''}
                                localDateTime={post.localDateTime ? `${post.localDateTime}` : ''}
                                title={post.title ? `${post.title}` : ''}
                                description={post.description ? `${post.description}` : ''}  // TODO risolvere l'array
                                instrument={post.instrument ? `${post.instrument}` : ''}
                                tagsList={post.tagList ? post.tagList: ''}
                                id={post.id ? `${post.id}`: ''}
                                open={post.open}
                                genre={post.genre ? `${post.genre}`: ''}
                                invitationType={post.invitationType ? `${post.invitationType}`: ''}
                                tagList={post.tagList ? post.tagList: ''}
                                />
                            ))}
                        </ul>
                    </div>
                </div>               
            </div>
        )
    }
}

export default Posts;
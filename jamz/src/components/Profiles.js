import React, { Component } from 'react';
import './Profiles.css';
import ProfileItem from './ProfileItem.js';
import userService from './services/user.service';


class Profiles extends Component{
    constructor(props){
        super(props);
        this.state = {
            profiles_list:[]
        }
    }

    componentDidMount() {
        userService.getAllPublicUsers().then(data => {
            this.setState({
            profiles_list: data 
            })
        });
    }

    render(){
        return (
            <div className='profiles'>
                <div className='profiles__container'>
                    <div className='profiles__wrapper'>
                        <ul className='profiles__items'>
                            {this.state.profiles_list && this.state.profiles_list.map((profile) =>(                            
                                <ProfileItem
                                //src='images/img-7.jpg'
                                username= {profile.username ? `${profile.username}` : ''}
                                bio={profile.bio ? `${profile.bio}` : ''}
                                location={profile.location ? `${profile.location}` : ''}
                                instrumentList={profile.instrumentList ? profile.instrumentList : ''}  // TODO risolvere l'array
                                tagList={profile.tagList ? profile.tagList: ''}
                                available={profile.available ? `${profile.available}`: ''}
                                />
                            ))}
                        </ul>
                    </div>
                </div>
            </div>
        );
    }
}

export default Profiles;
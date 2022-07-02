import React from 'react';
import { Link } from 'react-router-dom';

function ProfileItem(props) {
    return (
        <>
            <div class="profile-item-container">
                <div class="our-team">
                    <div class="team-content">
                        <div className='profile-item-Link'>
                            <Link to={{
                                pathname: "/profile_details?user=" + `${props.username}`,
                            }} style={{ textDecoration: 'none', color: 'black', fontWeight: 'bold'}}>
                                <ul>
                                    <li>
                                        <div class="picture">
                                            <img class="img-fluid" src="images/user-avatar.png"/>
                                        </div>
                                    </li>
                                    <li>
                                        {props.username}
                                    </li>
                                </ul>
                            </Link>
                        </div>
                        <div class="profile-item-location"><img className='profile-item-location-icon' src='images/location_icon.png'/><h4>{props.location}</h4></div>
                        <p class='profile-item-bio'>{props.bio}</p>
                        <div class="profile-item-instruments">
                            <h6>INSTRUMENTS</h6>
                            <ul>
                                {props.instrumentList && props.instrumentList.map((instrument) => (
                                    <li>{instrument}</li>
                                ))}
                            </ul>
                        </div>
                        <div class="profile-item-tags">
                            <h6>TAGS</h6>
                            <ul>
                                {props.tagList && props.tagList.map((tag) => (
                                    console.log(tag)
                                ))}
                                {props.tagList && props.tagList.map((tag) => (
                                    <li>{tag}</li>
                                ))}
                            </ul>
                            
                        </div>
                        <div class='profile-item-available'>
                            {props.available ? <span>AVAILABLE</span> : <span>NOT AVAILABLE</span>}
                            {/* TODO CONTROLLARE LA CONDIZIONE E AGGIUSTARE L'IMMAGINE*/}
                        </div>
                    </div>
                    <ul class="profile-item-social">
                        <li><a href="#" class="social"><i class="fab fa-facebook-f"></i></a></li>
                        <li><a href="#" class="social"><i class="fab fa-google-plus-g"></i></a></li>
                        <li><a href="#" class="social"><i class="fab fa-linkedin-in"></i></a></li>
                    </ul>
                </div>
            </div>
        
        </>
    );
}

export default ProfileItem;

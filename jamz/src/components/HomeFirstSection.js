import React from 'react';
import './HomeFirstSection.css';


function HomeFirstSection() {
  return (
    <div className='home-first-section'>
        <div className='home-first-section-image'>
            <img src='images/music-search.jpg'></img>
        </div>
        <div className='home-first-section-description'>
            <h2> SEARCH </h2>
            <p> Search for musicians all around the world who have the same interests as you. 
              Customize the search using tags, music genre and much more to find the perfect match for you.
            </p>
        </div>
    </div>
  );
}

export default HomeFirstSection;

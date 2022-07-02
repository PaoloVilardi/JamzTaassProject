import React from 'react';
import '../../App.css';
import Cards from '../Cards';
import HeroSection from '../HeroSection';
import Footer from '../Footer';
import HomeFirstSection from '../HomeFirstSection';
import HomeSecondSection from '../HomeSecondSection';
import HomeThirdSection from '../HomeThirdSection';

function Home() {
  return (
    <>
      <HeroSection />
      <div className='home-sections'>
        <HomeFirstSection/>
        <HomeSecondSection/>
        <HomeThirdSection/>
      </div>
      {/* <Cards /> */}
      <Footer />
    </>
  );
}

export default Home;

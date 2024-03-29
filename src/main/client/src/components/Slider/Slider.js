import React from 'react';
import AliceCarousel from 'react-alice-carousel';
import 'react-alice-carousel/lib/alice-carousel.css';
import './Slider.css';

const handleDragStart = (e) => e.preventDefault();

const Slider = ({ restaurants }) => {
  const items = restaurants.map((restaurant) => {
    return (
      <div className="slide-container" key={restaurant?.id} onDragStart={handleDragStart}>
        <img src={restaurant?.imageUrl?.url} alt={restaurant?.name} className="sliderimg" />
      </div>
    )
  });

  return (
    <div className="carousel-container">
      <h2>Top products</h2>
      <AliceCarousel
        items={items}
        responsive={{
          0: { items: 4 },
          768: { items: 4 },
        }}
        slideToIndex={0}
        dotsDisabled={true}
        buttonsDisabled={false}
        mouseTrackingEnabled={true}
        infinite={true}
        autoPlay={true}
        autoPlayInterval={2000}
        autoPlayDirection="ltr"
        animationDuration={500}
        animationType="fadeout"
        touchTrackingEnabled={true}
        disableSlideInfo={true}
        itemWidth={200}
        stagePadding={{ paddingLeft: 0, paddingRight: 0 }}
        prevButtonClassName="prev-button"
        nextButtonClassName="next-button"
      />
    </div>
  );
}

export default Slider;
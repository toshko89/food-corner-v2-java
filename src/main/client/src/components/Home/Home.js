import { useEffect, useState } from "react";
import { getAllRestaurants } from "../../services/restaurantService.js";
import { Grid, Loading } from '@nextui-org/react';
import HomeCard from "./HomeCard.js";
import Slider from '../Slider/Slider.js';
import { getRandomProducts } from "../../services/productService.js";

export default function Home() {

  const [restaurants, setRestaurants] = useState([]);
  const [products, setProducts] = useState([]);
  useEffect(() => {
    (async function fetchData() {
      try {
        const res = await getAllRestaurants();
        setRestaurants(res)
      } catch (error) {
        throw new Error(error)
      }
    })();
  }, [])

  useEffect(() => {
    (async function fetchData() {
      try {
        const res = await getRandomProducts();
        setProducts(res)
      } catch (error) {
        throw new Error(error)
      }
    })();
  }, [])


  return (
    <>
      <div className="container most_popular py-5">
        <Slider restaurants={products} />
        <h2 className="font-weight-bold mb-3">Restaurants nearby</h2>
        <div className="row">
          <Grid.Container gap={2} justify="center">
            {restaurants.length > 0
              ? restaurants.map(res => <HomeCard key={res.id} data={res} />)
              : <Loading type="points" />}
          </Grid.Container>
        </div>
      </div>
    </>
  );
}

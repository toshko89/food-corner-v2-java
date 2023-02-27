import React from 'react';
import { useEffect, useState } from "react";
import { Grid, Loading } from '@nextui-org/react';
import HomeCard from "../Home/HomeCard.js";
import { getFavorites } from "../../services/restaurantService.js";
import { useSelector } from "react-redux";
import { newQuery } from '../../utils/queryHelper.js'


export default function Favorites() {

  const [restaurants, setRestaurants] = useState([]);
  const userFavorites = useSelector(state => state.auth.favorites);
 
  useEffect(() => {
    (async function fetchData() {
      try {
        const res = await getFavorites(userFavorites);
        setRestaurants(res.data)
      } catch (error) {
        throw new Error(error)
      }
    })();
  }, [])

  return (
    <>
      <div className="container most_popular py-5">
        <h2 className="font-weight-bold mb-3">Favorites</h2>
        <div className="row">
          <Grid.Container gap={2} justify="center">
            {restaurants.length > 0
              ? restaurants.map(res => <HomeCard key={res._id} data={res} />)
              : <h2 className="font-weight-bold mb-3">No favorites yet</h2>}
          </Grid.Container>
        </div>
      </div>
    </>
  )
}
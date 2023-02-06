import React from 'react';
import { useEffect, useState } from "react";
import { getOwnRestaurants } from "../../services/restaurantService.js";
import { Grid, Loading } from '@nextui-org/react';
import { useNavigate } from "react-router-dom";
import HomeCard from "../Home/HomeCard.js";
import { useDispatch } from "react-redux";
import { logoutStateChange } from "../../app/auth.js";

export default function MyRestaurants() {

  const [restaurants, setRestaurants] = useState([]);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    (async function fetchData() {
      try {
        const res = await getOwnRestaurants();
        if (res.status === 401) {
          dispatch(logoutStateChange());
          navigate('/login', { replace: true });
          return;
        }
        setRestaurants(res);
      } catch (error) {
        throw new Error(error)
      }
    })();
  }, [])


  return (
    <div className="osahan-favorites">
      <div className="container most_popular py-5">
        <h2 className="font-weight-bold mb-3">My Restaurants</h2>
        <div className="row">
          <Grid.Container gap={2} justify="center">
            {restaurants.length > 0
              ? restaurants.map(res => <HomeCard key={res.id} data={res} />)
              : <Loading type="points" />}
          </Grid.Container>
        </div>
      </div>
    </div>
  );
}
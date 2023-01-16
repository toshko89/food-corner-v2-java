import React from 'react';  
import { Routes, Route, Navigate } from 'react-router-dom';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import Login from './components/auth/Login.js';
import Logout from './components/auth/Logout.js';
import Footer from './components/Footer/Footer.js';
import Header from './components/Header/Header.js';
import Home from './components/Home/Home.js';
import OwnerGuard from './guards/OwnerGuard.js';
import RestaurantMenu from './components/Menu/RestaurantMenu.js';
import IsLoggedIn from './guards/IsLoggedIn.js';
import { verify } from './services/authService.js';
import { autoLoadFavorites, loginStateChange } from './app/auth.js';
import Profile from './components/auth/Profile.js';
import AllComments from './components/Comments/AllComments.js';
import Register from './components/auth/Register.js';
import MyOrders from './components/Orders/MyOrders.js';
import Successful from './components/Orders/Successful.js';
import Checkout from './components/Restaurants/Checkout.js';
import Favorites from './components/Restaurants/Favorites.js';
import MyRestaurants from './components/Restaurants/MyRestaurants.js'
import CreateRestaurant from './components/Restaurants/CreateRestaurant.js'


function App() {

  // const dispatch = useDispatch();

  // useEffect(() => {
  //   (async function fetchData() {
  //     try {
  //       const user = await verify();
  //       dispatch(autoLoadFavorites());
  //       dispatch(loginStateChange(user))
  //     } catch (error) {
  //       throw new Error(error)
  //     }
  //   })();
  // }, [dispatch]);

  return (
    <>
      <Header />
      <Routes>
        <Route path="/" exact element={<Home />}></Route>
        {/* <Route path="/register" element={<Register />}></Route>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/logout" element={<Logout />}></Route>
        <Route path="/restaurants/:id" element={<RestaurantMenu />}>
          <Route path="edit" element={<OwnerGuard><CreateRestaurant edit={true} /></OwnerGuard>} />
        </Route>
        <Route path="/restaurants/:id/comments" element={<AllComments />}></Route>
        <Route path="/my-account/:id" element={<IsLoggedIn><Profile /></IsLoggedIn>}>
          <Route path="orders" element={<IsLoggedIn><MyOrders /></IsLoggedIn>} />
        </Route>
        <Route path="/my-account/:id/favorites" element={<IsLoggedIn><Favorites /></IsLoggedIn>}></Route>
        <Route path="/my-account/:id/cart" element={<IsLoggedIn><Checkout /></IsLoggedIn>}></Route>
        <Route path="/my-account/:id/cart/success" element={<IsLoggedIn><Successful /></IsLoggedIn>}></Route>
        <Route path="/my-account/:id/create-restaurant" element={<IsLoggedIn><CreateRestaurant /></IsLoggedIn>}></Route>
        <Route path='/my-account/:id/my-restaurants' element={<IsLoggedIn><MyRestaurants /></IsLoggedIn>}></Route> */}
        <Route path='*' element={<Navigate to="/" replace />}></Route>
      </Routes>
      <Footer />
    </>

  );
}

export default App;

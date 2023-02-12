import axios from 'axios';
const REACT_APP_BASE_URL = process.env.REACT_APP_BASE_URL;
// const REACT_APP_BASE_URL = "http://localhost:8080/api/food-corner";

console.log(REACT_APP_BASE_URL);


async function createNewRestaurant(formData) {
  try {
    const restaurant = await axios.post(REACT_APP_BASE_URL + '/restaurants/new-restaurant', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': JSON.parse(localStorage.getItem("Authorization"))
      }
    });
    return restaurant;
  } catch (error) {
    return error.response;
  }
}

async function editRestaurnat(restaurantId, formData) {
  try {
    const restaurant = await axios.put(REACT_APP_BASE_URL + `/restaurants/${restaurantId}/edit`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': JSON.parse(localStorage.getItem("Authorization"))
      }
    });
    return restaurant;
  } catch (error) {
    return error.response;
  }
}

async function getOwnRestaurants() {
  try {
    const restaurants = await fetch(REACT_APP_BASE_URL + '/restaurants/by-owner', {
      method: 'GET',
      headers: { 'Content-Type': 'application/json', 'Authorization': JSON.parse(localStorage.getItem("Authorization")) },
      credentials: 'include'
    });
    if (restaurants.status === 200) {
      return restaurants.json();
    }
    console.log(restaurants);
    return restaurants;
  } catch (error) {
    throw new Error(error)
  }
}

async function getFavorites(favorites = []) {
  try {
    const restaurants = await fetch(REACT_APP_BASE_URL + `/restaurants/favorites/?${favorites}`, {
      method: 'GET',
      credentials: 'include'
    });
    return restaurants.json();
  } catch (error) {
    throw new Error(error)
  }
}

async function deleteRestaurantById(id) {
  try {
    const restaurant = await fetch(REACT_APP_BASE_URL + `/restaurants/${id}`, {
      method: 'DELETE',
      credentials: 'include'
    });
    return restaurant.json();
  } catch (error) {
    throw new Error(error)
  }
}

async function getAllRestaurants() {
  try {
    const restaurants = await fetch(REACT_APP_BASE_URL + '/restaurants', {
      headers: { 'Content-Type': 'application/json', 'Authorization': JSON.parse(localStorage.getItem("Authorization")) },
      method: 'GET',
      credentials: 'include',
    });
    return restaurants.json();
  } catch (error) {
    throw new Error(error)
  }
}


async function getRestaurantById(id) {
  try {
    const restaurants = await fetch(REACT_APP_BASE_URL + `/restaurants/${id}`, {
      method: 'GET',
      credentials: 'include'
    });
    return restaurants.json();
  } catch (error) {
    throw new Error(error)
  }
}

export {
  createNewRestaurant,
  getOwnRestaurants,
  getAllRestaurants,
  getRestaurantById,
  editRestaurnat,
  deleteRestaurantById,
  getFavorites
}
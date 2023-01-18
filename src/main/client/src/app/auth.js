import { createSlice } from '@reduxjs/toolkit';

const authState = {
  email: null,
  isOwner: false,
  _id: null,
  phone: null,
  name: null,
  city: null,
  address: null,
  // favorites: []
}

export const authSlice = createSlice({
  name: 'auth',
  initialState: authState,
  reducers: {
    loginStateChange(state, action) {
      state.email = action.payload.email;
      state.isOwner = action.payload.isOwner;
      state._id = action.payload._id;
      state.phone = action.payload.phone;
      state.name = action.payload.name;
      state.city = action.payload.city;
      state.address = action.payload.address;
    },
    logoutStateChange(state) {
      state.email = null;
      state.isOwner = null;
      state._id = null;
      state.phone = null;
      state.name = null;
      state.city = null;
      state.address = null;
    },
    // addToFavorites(state, action) {
    //   state.favorites.push(action.payload._id);
    //   localStorage.setItem('favorites', JSON.stringify(state.favorites));
    // },
    // removeFromFavorites(state, action) {
    //   const index = state.favorites.indexOf(action.payload._id);
    //   state.favorites.splice(index, 1);
    //   localStorage.setItem('favorites', JSON.stringify(state.favorites));
    // },
    // autoLoadFavorites(state) {
    //   const favorites = localStorage.getItem('favorites');
    //   if (favorites) {
    //     state.favorites = JSON.parse(favorites);
    //   }
    // }
  }

});

export const { loginStateChange,
  logoutStateChange,
  addToFavorites,
  removeFromFavorites,
  autoLoadFavorites } = authSlice.actions;

export default authSlice.reducer;
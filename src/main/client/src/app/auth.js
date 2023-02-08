import { createSlice } from '@reduxjs/toolkit';

const authState = {
  email: null,
  isOwner: false,
  id: null,
  phone: null,
  name: null,
  city: null,
  address: null,
  userRole:null,
  favorites: []
}

export const authSlice = createSlice({
  name: 'auth',
  initialState: authState,
  reducers: {
    loginStateChange(state, action) {
      state.email = action.payload.user.email;
      state.isOwner = action.payload.user.isOwner;
      state.id = action.payload.user.id;
      state.phone = action.payload.user.phone;
      state.name = action.payload.user.name;
      state.city = action.payload.user.city;
      state.address = action.payload.user.address;
      state.userRole = action.payload.user.userRole;
    },
    logoutStateChange(state) {
      state.email = null;
      state.isOwner = null;
      state.id = null;
      state.phone = null;
      state.name = null;
      state.city = null;
      state.address = null;
      state.userRole = null;
    },
    addToFavorites(state, action) {
      state.favorites.push(action.payload.id);
      localStorage.setItem('favorites', JSON.stringify(state.favorites));
    },
    removeFromFavorites(state, action) {
      const index = state.favorites.indexOf(action.payload.id);
      state.favorites.splice(index, 1);
      localStorage.setItem('favorites', JSON.stringify(state.favorites));
    },
    autoLoadFavorites(state) {
      const favorites = localStorage.getItem('favorites');
      if (favorites) {
        state.favorites = JSON.parse(favorites);
      }
    }
  }

});

export const { loginStateChange,
  logoutStateChange,
  addToFavorites,
  removeFromFavorites,
  autoLoadFavorites } = authSlice.actions;

export default authSlice.reducer;
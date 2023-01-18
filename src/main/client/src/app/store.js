import { configureStore } from '@reduxjs/toolkit';
import authSlice from './auth.js';
import restaurantSlice from './restaurant.js';
import cartSlice from './cart.js';
import { loadState } from './localeStorage.js';

const store = configureStore({
  reducer: {
    auth: authSlice,
    restaurant: restaurantSlice,
    cart:cartSlice
  },
  preloadedState: loadState()
});

export default store;
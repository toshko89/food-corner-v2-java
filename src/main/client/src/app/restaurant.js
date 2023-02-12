import { createSlice } from '@reduxjs/toolkit';

const restaurantState = {
  id: '',
  name: '',
  category: '',
  city: '',
  address: '',
  workingHours: '',
  owner: '',
  imageUrl: {
    url: '',
    publicId: ''
  },
  products: [],
  rating: '',
  ratingsCount: ''
}

const restaurantSlice = createSlice({
  name: 'restaurant',
  initialState: restaurantState,
  reducers: {
    setRestaurantState(state, action) {
      state.id = action.payload.id;
      state.category = action.payload.category;
      state.city = action.payload.city;
      state.address = action.payload.address;
      state.workingHours = action.payload.workingHours;
      state.owner = action.payload.owner;
      state.imageUrl = action.payload.imageUrl;
      state.products = action.payload.products;
      state.rating = action.payload.rating;
      state.ratingsCount = action.payload.ratingsCount;
      state.name = action.payload.name
    },
    clearRestaurantState(state) {
      state.id = '';
      state.name = '';
      state.category = '';
      state.city = '';
      state.address = '';
      state.workingHours = '';
      state.owner = '';
      state.imageUrl = { url: '', publicId: '' };
      state.products = [];
      state.rating = '';
      state.ratingsCount = ''
    },

  }
})

export const { setRestaurantState, clearRestaurantState } = restaurantSlice.actions;

export default restaurantSlice.reducer;
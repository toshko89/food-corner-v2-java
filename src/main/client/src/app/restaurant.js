import { createSlice } from '@reduxjs/toolkit';

const restaurantState = {
  _id: '',
  name: '',
  categorie: '',
  city: '',
  address: '',
  working_hours: '',
  owner: '',
  img: {
    secure_url: '',
    public_id: ''
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
      state._id = action.payload._id;
      state.categorie = action.payload.categorie;
      state.city = action.payload.city;
      state.address = action.payload.address;
      state.working_hours = action.payload.working_hours;
      state.owner = action.payload.owner;
      state.img = action.payload.img;
      state.products = action.payload.products;
      state.rating = action.payload.rating;
      state.ratingsCount = action.payload.ratingsCount;
      state.name = action.payload.name
    },
    clearRestaurantState(state) {
      state._id = '';
      state.name = '';
      state.categorie = '';
      state.city = '';
      state.address = '';
      state.working_hours = '';
      state.owner = '';
      state.img = { secure_url: '', public_id: '' };
      state.products = [];
      state.rating = '';
      state.ratingsCount = ''
    },

  }
})

export const { setRestaurantState, clearRestaurantState } = restaurantSlice.actions;

export default restaurantSlice.reducer;
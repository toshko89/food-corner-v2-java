import axios from 'axios';
const REACT_APP_BASE_URL = process.env.REACT_APP_BASE_URL;

async function addProduct(restaurantId, productData) {
  try {
    const newProduct = await axios.post(REACT_APP_BASE_URL + `/products/${restaurantId}/add-product`, productData, {
      headers: { 'Content-Type': 'multipart/form-data', 'Authorization': JSON.parse(localStorage.getItem("Authorization")) },
    });
    return newProduct;
  } catch (error) {
    throw new Error(error)
  }
}

async function editProduct(restaurantId, productId, productData) {
  try {
    const editedProduct = await axios.put(REACT_APP_BASE_URL + `/products/${restaurantId}/edit-product/${productId}`,productData, {
      headers: { 'Content-Type': 'multipart/form-data', 'Authorization': JSON.parse(localStorage.getItem("Authorization")) },
    });
    console.log(editedProduct);
  } catch (error) {
    throw new Error(error)
  }
}

async function deleteProduct(restaurantId, productId) {
  try {
    const restaurant = await fetch(REACT_APP_BASE_URL + `/products/${restaurantId}/delete-product/${productId}`, {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json', 'Authorization': JSON.parse(localStorage.getItem("Authorization")) },
      credentials: 'include',
    });
    return restaurant.json();
  } catch (error) {
    throw new Error(error)
  }
}


export {
  addProduct,
  editProduct,
  deleteProduct
};

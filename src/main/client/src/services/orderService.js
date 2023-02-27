const REACT_APP_BASE_URL = process.env.REACT_APP_BASE_URL;

async function sendOrder(orderData) {
  try {
    const order = await fetch(REACT_APP_BASE_URL + '/orders/create', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json','Authorization': JSON.parse(localStorage.getItem("Authorization")) },
      credentials: 'include',
      body: JSON.stringify(orderData),
    });
    return order;
  } catch (error) {
    throw new Error(error)
  }
}

async function getAllOrdersByUser() {
  try {
    const orders = await fetch(REACT_APP_BASE_URL + '/orders', {
      method: 'GET',
      headers: { 'Content-Type': 'application/json','Authorization': JSON.parse(localStorage.getItem("Authorization")) },
      credentials: 'include',
    });
    if(!orders.ok){
      return orders;
    }
    return orders.json();
  } catch (error) {
    throw new Error(error)
  }
}

export {
  sendOrder,
  getAllOrdersByUser
}
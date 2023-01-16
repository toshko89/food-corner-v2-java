const REACT_APP_BASE_URL = process.env.REACT_APP_BASE_URL;

async function sendOrder(orderData) {
  try {
    const order = await fetch(REACT_APP_BASE_URL + '/orders/create', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(orderData),
    });
    return order.json();
  } catch (error) {
    throw new Error(error)
  }
}

async function getAllOrdersByUserId(userId) {
  try {
    const orders = await fetch(REACT_APP_BASE_URL + `/orders/${userId}`, {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
    });
    return orders.json();
  } catch (error) {
    throw new Error(error)
  }
}

export {
  sendOrder,
  getAllOrdersByUserId
}
import React from 'react';
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { getAllOrdersByUserId } from "../../services/orderService.js";
import OrderCard from "./OrderCard.js";


export default function MyOrders() {

  const [orders, setOrders] = useState([]);
  const userId = useSelector(state => state.auth._id);
  const [error, setError] = useState(null);

  useEffect(() => {
    getAllOrdersByUserId(userId)
      .then(res => {
        setOrders(res);
      })
      .catch(err => {
        setError(err);
      })
  }, [userId]);

  return (
    <div className="container">
      {error && <div className="error-container" role="alert"><p>{error}</p></div>}
      <div className="tab-content col-md-9" id="myTabContent">
        <div id="progress" role="tabpanel" aria-labelledby="progress-tab">
          {orders.length > 0 && <h3>My Orders</h3>}
          <div className="order-body">
            {orders.length > 0
              ? orders.map(order => <OrderCard key={order._id} order={order} />)
              : <h3>No orders yet</h3>}
          </div>
        </div>
      </div>
    </div>
  )
}
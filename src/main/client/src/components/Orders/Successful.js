import React from 'react';
import { Link, useLocation } from "react-router-dom";

export default function Successful() {

  const location = useLocation();
  const { name, id } = location.state;

  return (
    <div className="py-5 osahan-coming-soon d-flex justify-content-center align-items-center">
      <div className="col-md-6">
        <div className="text-center pb-3">
          <h1 className="font-weight-bold">{name}, your order has been successful</h1>
        </div>
        <div className="bg-white rounded text-center p-4 shadow-sm">
          <h1 className="display-1 mb-4">🎉</h1>
          <h6 className="font-weight-bold mb-2">Preparing your order</h6>
          <p className="small text-muted">Your order will be prepared and will come soon</p>
          <Link to={`/my-account/${id}/orders`} className="btn rounded btn-primary btn-lg btn-block">Track My Order</Link>
        </div>
      </div>
    </div>
  )
}
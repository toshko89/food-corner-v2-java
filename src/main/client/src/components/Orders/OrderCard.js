import React from 'react';
import formatDate from "../../utils/dateFormat.js";
import { v4 as uuidv4 } from 'uuid';

export default function OrderCard({ order }) {
  console.log(order);
  let totalPayment = order?.product.price * order?.quantity;
  return (
    order &&
    <>
      <div className="pb-3">
        <div className="p-3 rounded shadow-sm bg-white">
          <div className="d-flex border-bottom pb-3">
            <div className="text-muted mr-3">
              <img alt="#" src={order?.restaurant?.imageUrl.url} className="img-fluid order_img rounded" />
            </div>
            <div>
              <p className="mb-0 font-weight-bold"><a href="restaurant.html" className="text-dark">{order?.restaurant?.name}</a></p>
              <p className="mb-0">{order?.restaurant?.address}</p>
              <p>ORDER {order?.id}</p>
            </div>
            <div className="ml-auto">
              <p className="bg-warning text-white py-1 px-2 rounded small mb-1">Completed</p>
              <p className="small font-weight-bold text-center"><i className="feather-clock"></i> {formatDate(order?.date)}</p>
            </div>
          </div>
          <div className="d-flex pt-3">
            <div className="large">
            <p className="text- font-weight-bold mb-0">{order.product?.name} x {order?.quantity}</p>
              {/* {order?.product.map(item => <p key={uuidv4()} className="text- font-weight-bold mb-0">{item.item?.name} x {item?.quantity}</p>)} */}
            </div>
            <div className="text-muted m-0 ml-auto mr-3 small">Total Payments
              <span className="text-dark font-weight-bold"> {totalPayment > 20 ? totalPayment : (totalPayment += 3.99).toFixed(2)}$</span>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}
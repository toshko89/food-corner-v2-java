import React from 'react';

export default function CartCard({ item, addToCartClick,removeFromCartClick }) {
  return (
    <div className="gold-members d-flex align-items-center justify-content-between px-3 py-2 border-bottom">
      <div className="media align-items-center">
        <div className="media-body">
          <p className="m-0">{item.product.name}</p>
        </div>
      </div>
      <div className="d-flex align-items-center">
        <div className="count-number float-right">
          <button type="button" onClick={() => removeFromCartClick(item)} className="btn-sm left dec btn btn-outline-secondary">
            <i className="feather-minus"></i> </button><input className="count-number-input" type="text" defaultValue={item.quantity} />
          <button type="button" onClick={() => addToCartClick(item)} className="btn-sm right inc btn btn-outline-secondary">
            <i className="feather-plus"></i> </button>
        </div>
        <p className="text-gray mb-0 float-right ml-2 text-muted small">${item.product.price}</p>
      </div>
    </div>
  )
}
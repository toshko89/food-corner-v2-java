
import React from 'react';
import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';


const OwnerGuard = ({ children }) => {
  const user = useSelector(state => state.auth);
  const restaurant = useSelector(state => state.restaurant);
  if (user && user._id === restaurant.owner) {
    return children;
  } else {
    return <Navigate to={'/login'} replace />;
  }

}

export default OwnerGuard
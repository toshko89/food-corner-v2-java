import React from 'react';
import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';

const IsLoggedIn = ({ children }) => {
  const userId = useSelector(state => state.auth._id);
  if (userId) {
    return children;
  } else {
    return <Navigate to={'/login'} replace />;
  }
}

export default IsLoggedIn;
import React from 'react';
import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';

export default function IsLoggedIn({ children }) {
  const userId = useSelector(state => state.auth.id);
  if (userId) {
    return children;
  } else {
    return <Navigate to={'/login'} replace />;
  }
}


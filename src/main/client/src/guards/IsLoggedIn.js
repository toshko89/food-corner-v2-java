import React from 'react';
import jwt_decode from "jwt-decode";
import { useDispatch, useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';
import { logoutStateChange } from '../app/auth.js';

export default function IsLoggedIn({ children }) {
  const token = localStorage.getItem("Authorization");
  const dispatch = useDispatch();
  if (!token) {
    dispatch(logoutStateChange());
    return <Navigate to="/login" />;
  }

  try {
    const decoded = jwt_decode(token);
    const currentTime = new Date().getTime() / 1000;
    if (decoded.exp < currentTime) {
      dispatch(logoutStateChange());
      localStorage.removeItem("Authorization");
      return <Navigate to="/login" />;
    }
  } catch (err) {
    console.error(err);
    dispatch(logoutStateChange());
    return <Navigate to="/login" />;
  }

  return children;
}


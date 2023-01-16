import React from 'react';
import { useDispatch } from "react-redux";
import { Navigate } from "react-router-dom";
import { logoutStateChange } from "../../app/auth.js";
import { logout } from "../../services/authService.js";

export default function Logout() {

  const dispatch = useDispatch();

  logout()
    .then((res) => {
      if (res.status === 200) {
        dispatch(logoutStateChange());
      }
    })
    .catch((err) => {
      console.log(err);
    })

  return (
    <Navigate to="/" />
  );
}
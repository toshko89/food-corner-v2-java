import React from 'react';
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { register } from "../../services/authService.js";
import { useDispatch } from "react-redux";
import { loginStateChange } from "../../app/auth.js";
import emailCheck from "../../utils/emailCheck.js";

export default function Register() {

  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  async function signIn(e) {
    e.preventDefault()
    const formData = new FormData(e.currentTarget);
    const email = formData.get('email');
    const password = formData.get('password');
    const rePass = formData.get('repass');

    if (email.trim() == '' || password.trim() == '' || rePass.trim() == '') {
      setError('All fields are required!');
      return;
    }

    if (password.trim() !== rePass.trim()) {
      setError('Password doesn\'t match');
      return;
    }

    if (!emailCheck(email)) {
      setError('Invalid email address, please try again');
      e.target.reset();
      return;
    }

    try {
      const user = await register(email, password, rePass);
      if (user.message) {
        setError(user.message);
        e.target.reset();
        return;
      }

      dispatch(loginStateChange(user));
      navigate('/');
    } catch (error) {
      e.target.reset();
      setError(error)
    }
  }


  return (
    <>
      <div className="d-flex align-items-center justify-content-center vh-100">
        <div className="px-5 col-md-6 ml-auto">
          <div className="px-5 col-10 mx-auto">
            <h2 className="text-dark my-0">Register</h2>
            {error && <div className="error-container" role="alert"><p>{error}</p></div>}
            <form className="mt-5 mb-4" onSubmit={signIn}>
              <div className="form-group">
                <label htmlFor="exampleInputEmail1" className="text-dark">Email</label>
                <input type="email" autoComplete="email" name="email" placeholder="Enter Email" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                  onBlur={() => setError(null)} />
              </div>
              <div className="form-group">
                <label htmlFor="exampleInputPassword1" className="text-dark">Password</label>
                <input type="password" autoComplete="new-password" name="password" placeholder="Enter Password" className="form-control" id="exampleInputPassword2"
                  onBlur={() => setError(null)} />
              </div>
              <div className="form-group">
                <label htmlFor="exampleInputPassword1" className="text-dark">Repeat Password</label>
                <input type="password" autoComplete="new-password" name="repass" placeholder="Enter Password" className="form-control" id="exampleInputPassword1" />
              </div>
              <button className="btn btn-primary btn-lg btn-block">SIGN IN</button>
            </form>
          </div>
        </div>
      </div>
    </>
  )
}
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login } from "../../services/authService.js";
import { useDispatch } from 'react-redux';
import { loginStateChange } from "../../app/auth.js";
import emailCheck from "../../utils/emailCheck.js";

export default function Login() {

  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  async function loginForm(e) {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const email = formData.get('email');
    const password = formData.get('password');

    if (email.trim() == '' || password.trim() == '') {
      setError('All fields are required!');
      return;
    }

    if (password.trim() < 5 && password.trim() > 20) {
      setError('Password must be between 5 and 20 characters long');
      return;
    }

    if (!emailCheck(email)) {
      setError('Invalid email address, please try again');
      e.target.reset();
      return;
    }

    try {
      const user = await login(email, password);
      if (user.error) {
        setError(user.error);
        e.target.reset();
        return;
      }
      dispatch(loginStateChange(user));
      localStorage.setItem('Authorization', JSON.stringify("Bearer " + user.token));
      navigate('/');
    } catch (error) {
      setError(error);
      e.target.reset();
    }
  }

  return (
    <>
      <div className="d-flex align-items-center justify-content-center vh-100">
        {error && <div className="error-container" role="alert"><p>{error}</p></div>}
        <div className="px-5 col-md-6 ml-auto">
          <div className="px-5 col-10 mx-auto">
            <h2 className="text-dark my-0">Welcome Back</h2>
            <p className="text-50">Sign in to continue</p>
            <form className="mt-5 mb-4" onSubmit={loginForm}>
              <div className="form-group">
                <label htmlFor="exampleInputEmail1" className="text-dark">Email</label>
                <input autoComplete="current-name" type="email" name="email" placeholder="Enter Email" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                  onBlur={() => setError(null)} />
              </div>
              <div className="form-group">
                <label htmlFor="exampleInputPassword1" className="text-dark">Password</label>
                <input type="password" autoComplete="current-password" name="password" placeholder="Enter Password" className="form-control" id="exampleInputPassword2"
                  onBlur={() => setError(null)} />
              </div>
              <button className="btn btn-primary btn-lg btn-block">SIGN IN</button>
            </form>
            <div className="d-flex align-items-center justify-content-center">
              <Link to={"/register"}>
                <p className="text-center m-0">Don't have an account? Sign up</p>
              </Link>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}
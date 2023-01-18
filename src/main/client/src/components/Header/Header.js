import React from 'react';
import { Link } from "react-router-dom";
import { useSelector } from 'react-redux';
import Badge from '@mui/material/Badge';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';


export default function Header() {
  const userId = undefined;
  // const userCredentials = useSelector(state => state.auth.name || state.auth.email);
  // const userId = useSelector(state => state.auth._id);
  // const orders = useSelector(state => state.cart.orders);
  // const itemsInCart = orders.reduce((acc, curr) => acc + curr.quantity, 0);

  return (
    <header className="section-header">
      <section className="header-main shadow-sm bg-white">
        <div className="container">
          <div className="row align-items-center">
            <div className="col-1">
              <Link to={"/"} className="brand-wrap mb-0">
                <img alt="#" className="img-fluid" src="img/logo_web.png" />
              </Link>
            </div>
            <div className="col-3 d-flex align-items-center m-none"></div>
            <div className="col-8">
              <div className="d-flex align-items-center justify-content-end pr-5">
                <Link to={"/"} className="widget-header mr-4 text-white btn bg-primary m-none">
                  <div className="icon d-flex align-items-center">
                    <i className="feather-disc h6 mr-2 mb-0"></i> <span>Restaurants</span>
                  </div>
                </Link>
                {!userId && <Link to={"/login"} className="widget-header mr-4 text-dark m-none">
                  <div className="icon d-flex align-items-center">
                    <i className="feather-user h6 mr-2 mb-0"></i> <span>Sign in</span>
                  </div>
                </Link>}
                {userId &&
                  <div className="dropdown mr-4 m-none">
                    {/* <Link to={`/my-account/${userId}`} className="dropdown-toggle text-dark py-3 d-block" id="dropdownMenuButton"
                      data-toggle="dropdown" aria-expanded="false"> Hi {userCredentials || 'Guest'}
                    </Link> */}
                    <div className="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
                      <Link to={`/my-account/${userId}`} className="dropdown-item" >My account</Link>
                      <Link to={`/my-account/${userId}/my-restaurants`} className="dropdown-item" >My restaurants</Link>
                      <Link to={`/my-account/${userId}/create-restaurant`} className="dropdown-item" >Create Restaurant</Link>
                      <Link to={`/my-account/${userId}/favorites`} className="dropdown-item" >Favorites</Link>
                      <Link to={`/my-account/${userId}/orders`} className="dropdown-item" >My Orders</Link>
                    </div>
                  </div>}
                <Link to={`/my-account/${userId}/cart`} className="widget-header mr-4 text-dark">
                  {/* <Badge badgeContent={itemsInCart} color="primary">
                    <ShoppingCartIcon />
                  </Badge> */}
                </Link>
                {userId && <Link to={"/logout"} className="widget-header mr-4 text-dark m-none">
                  <div className="icon d-flex align-items-center">
                    <i className="feather-user h6 mr-2 mb-0"></i> <span>Logout</span>
                  </div>
                </Link>}
              </div>
            </div>
          </div>
        </div>
      </section>
    </header>
  );
}
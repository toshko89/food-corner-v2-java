import { useEffect, useState } from "react";
import { useDispatch, useSelector } from 'react-redux';
import { useParams, Outlet } from "react-router-dom";
import { clearRestaurantState, setRestaurantState } from "../../app/restaurant.js";
import { getRestaurantById } from "../../services/restaurantService.js";
import RestaurantMenuNavIcons from "./RestaurantMenuNavIcons.js";
import { deleteProduct } from "../../services/productService.js";
import Categories from "../Menu/Categories.js";


export default function RestaurantMenu() {

  const { id } = useParams();
  const dispatch = useDispatch();
  const [error, setError] = useState(null);
  const currentRestaurant = useSelector(state => state.restaurant);
  const user = useSelector(state => state.auth);
  const isOwner = user.userRole === 'ADMIN' || currentRestaurant.owner.id === user.id;
  const products = currentRestaurant.products;
  const categories = currentRestaurant.products.map(product => product.category);
  const restaurantInFavorite = user.favorites.filter(restaurant => restaurant.id === currentRestaurant.id);

  console.log(user.favorites);

  useEffect(() => {
    (async function fetchData() {
      try {
        const res = await getRestaurantById(id);
        if (res.message) {
          setError(res.message);
          return;
        }
        dispatch(setRestaurantState(res));
      } catch (error) {
        console.log(error);
        throw new Error(error)
      }
    })();
    return () => {
      dispatch(clearRestaurantState())
    }
  }, [id])

  async function deleteProductHandler(productId) {
    try {
      const res = await deleteProduct(id, productId);
      if (res.status !== 200) {
        setError(res.message);
        return;
      }
      dispatch(clearRestaurantState());
      dispatch(setRestaurantState(res.data));
    } catch (error) {
      console.log(error);
      throw new Error(error)
    }
  }

  return (
    <>
      <div className="offer-section py-4">
        <div className="container position-relative">
          {error && <div className="error-container" role="alert"><p>{error}</p></div>}
          <img alt="restaurant img" src={currentRestaurant.imageUrl?.url} className="restaurant-pic" />
          <div className="pt-3 text-white">
            <h2 className="font-weight-bold">{currentRestaurant?.name}</h2>
            <p className="text-white m-0">{currentRestaurant?.city}</p>
            <p className="text-white m-0">{currentRestaurant?.address}</p>
          </div>
          <div className="pb-4">
            <div className="row">
              <div className="col-6 col-md-2">
                <p className="text-white-50 font-weight-bold m-0 small">Delivery</p>
                <p className="text-white m-0">$3.99 (free delivery for orders above $20)</p>
              </div>
              <div className="col-6 col-md-2">
                <p className="text-white-50 font-weight-bold m-0 small">Open time</p>
                <p className="text-white m-0">{currentRestaurant?.workingHours}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="container">
        {isOwner &&
          <RestaurantMenuNavIcons
            isOwner={isOwner}
            restaurant={currentRestaurant}
            restaurantInFavorite={restaurantInFavorite.length === 0 ? false : true} />
        }
        <Outlet />
        <Categories
          currentRestaurant={currentRestaurant}
          categories={categories}
          products={products}
          deleteProductHandler={deleteProductHandler}
          isOwner={isOwner}
        >
        </Categories>
      </div>
    </>
  )

}

/* <div class="row m-0">
          <h6 class="p-3 m-0 bg-light w-100">Quick Bites <small class="text-black-50">3 ITEMS</small></h6>
          <div class="col-md-12 px-0 border-top"></div>
          <Grid container spacing={{ xs: 2, md: 3 }} columns={{ xs: 4, sm: 8, md: 12 }}>
            {currentRestaurant.products?.length > 0
              && currentRestaurant.products.map(product =>
                  <MenuCard key={product._id}
                    isOwner={isOwner}
                    deleteProductHandler={deleteProductHandler}
                    product={product} />)}
          </Grid>
          {currentRestaurant.products?.length === 0 &&
            <><Loading type="points" /><h2 className="font-weight-bold mb-3">No products yet</h2></>}
        </div>
        <div class="row m-0">
          <h6 class="p-3 m-0 bg-light w-100">Quick Bites <small class="text-black-50">3 ITEMS</small></h6>
          <div class="col-md-12 px-0 border-top"></div>
          <Grid container spacing={{ xs: 2, md: 3 }} columns={{ xs: 4, sm: 8, md: 12 }}>
            {currentRestaurant.products?.length > 0
              && currentRestaurant.products.map(product =>
                <MenuCard key={product._id}
                  isOwner={isOwner}
                  deleteProductHandler={deleteProductHandler}
                  product={product} />)}
          </Grid>
          {currentRestaurant.products?.length === 0 &&
            <><Loading type="points" /><h2 className="font-weight-bold mb-3">No products yet</h2></>}
        </div> */

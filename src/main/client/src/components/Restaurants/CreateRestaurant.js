import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { LoadingButton } from '@mui/lab';
import SendIcon from '@mui/icons-material/Send';
import { Link, useNavigate } from "react-router-dom";
import { createNewRestaurant, editRestaurnat } from "../../services/restaurantService.js";
import { workTime } from "../../utils/workingTimeCheck.js";

export default function CreateRestaurant({ edit }) {

  const [error, setError] = useState(null);
  const [file, setFile] = useState([]);
  const [restaurant, setRestaurant] = useState({
    name: '', category: '', city: '',
    address: '', workingHours: ''
  });
  const [loading, setLoading] = useState(false);
  const user = useSelector(state => state.auth.id);
  const currentRestaurant = useSelector(state => state.restaurant);
  const userCredentials = useSelector(state => state.auth.name || state.auth.email);
  const navigate = useNavigate();

  function handleFileChange(e) {
    const file = e.target.files[0];
    setFile(file);
  }

  useEffect(() => {
    if (edit) {
      setRestaurant({
        name: currentRestaurant.name,
        category: currentRestaurant.category,
        city: currentRestaurant.city,
        address: currentRestaurant.address,
        workingHours: currentRestaurant.working_hours
      })
    }
  }, [currentRestaurant])

  async function createRestaurant(e) {
    e.preventDefault();
    try {
      // if (restaurant.name.trim() == '' || restaurant.category.trim() == ''
      //   || restaurant.city.trim() == '' || restaurant.address.trim() == '' || restaurant.workingHours == '') {
      //   setError('All fields are required');
      //   setRestaurant({ name: '', category: '', city: '', address: '', workingHours: '' });
      //   setFile([]);
      //   return;
      // }

      // if (file.length === 0) {
      //   setError('Please add cover photo');
      //   return;
      // }

      // if (!workTime(restaurant.workingHours)) {
      //   setError('Working hours not valid, should be in format 08:00-20:00');
      //   setRestaurant({ ...restaurant, workingHours: '' });
      //   return;
      // }

      const data = new FormData();
      data.append('name', restaurant.name);
      data.append('address', restaurant.address);
      data.append('category', restaurant.category);
      data.append('city', restaurant.city);
      data.append('workingHours', restaurant.workingHours);
      data.append('image', file);
      let newRestaurant;

      // console.log(data.get('image'))

      await createNewRestaurant(data);

      // if (edit) {
      //   setLoading(true);
      //   newRestaurant = await editRestaurnat(currentRestaurant.id, user, data);
      // } else {
      //   setLoading(true);
      //   console.log(data.get('CoverPhoto'))
      //   newRestaurant= await createNewRestaurant(data);
      // }

      // if (newRestaurant.message) {
      //   if (newRestaurant.message.includes('E11000')) {
      //     setError('Restaurant name is taken, please choose unique one');
      //     setLoading(false);
      //     setRestaurant({ ...restaurant, name: '' });
      //     setFile([]);
      //     return;
      //   }
      //   setLoading(false);
      //   setFile([]);
      //   setError(newRestaurant.message);
      //   return;
      // }

      // navigate(`/my-account/${user}/my-restaurants`);

    } catch (error) {
      setLoading(false);
      setError(error);
    }
  }

  return (
    <div className="container position-relative">
      {error && <div className="error-container" role="alert"><p>{error}</p></div>}
      <div className="py-5 osahan-profile row">
        <div className="col-md-4 mb-3">
          <div className="bg-white rounded shadow-sm sticky_sidebar overflow-hidden">
            <Link to={`/my-account/${user}/my-restaurants`} >
              <div className="d-flex align-items-center p-3">
                <div className="right">
                  <h6 className="mb-1 font-weight-bold">{userCredentials}<i className="feather-check-circle text-success"></i></h6>
                </div>
              </div>
            </Link>
            <div className="bg-white profile-details">
              <Link to={`/my-account/${user}/my-restaurants`} className="d-flex w-100 align-items-center border-bottom p-3">
                <div className="left mr-3">
                  <h6 className="font-weight-bold mb-1 text-dark">My restaurants</h6>
                  <p className="small text-muted m-0">See own restaurants</p>
                </div>
                <div className="right ml-auto">
                  <h6 className="font-weight-bold m-0"><i className="feather-chevron-right"></i></h6>
                </div>
              </Link>
              <Link to={`/my-account/${user}/favorites`} className="d-flex w-100 align-items-center border-bottom px-3 py-4">
                <div className="left mr-3">
                  <h6 className="font-weight-bold m-0 text-dark"><i className="feather-heart bg-primary text-white p-2 rounded-circle mr-2"></i> Favorites</h6>
                </div>
                <div className="right ml-auto">
                  <h6 className="font-weight-bold m-0"><i className="feather-chevron-right"></i></h6>
                </div>
              </Link>
              <Link to={`/my-account/${user}/orders`} className="d-flex w-100 align-items-center border-bottom px-3 py-4">
                <div className="left mr-3">
                  <h6 className="font-weight-bold m-0 text-dark"><i className="feather-clock bg-primary text-white p-2 rounded-circle mr-2"></i>My orders</h6>
                </div>
                <div className="right ml-auto">
                  <h6 className="font-weight-bold m-0"><i className="feather-chevron-right"></i></h6>
                </div>
              </Link>
            </div>
          </div>
        </div>
        <div className="col-md-8 mb-3">
          <div className="rounded shadow-sm p-4 bg-white">
            <h5 className="mb-4">{edit ? `Edit restaurant "${currentRestaurant.name}"` : 'Create the best restaurant'}</h5>
            <div id="edit_profile">
              <div>
                <form onSubmit={createRestaurant} >
                  <div className="form-group">
                    <label htmlFor="exampleInputName1">Name</label>
                    <input type="text" name="name" className="form-control" id="exampleInputName1d"
                      value={restaurant.name}
                      onChange={(e) => { setRestaurant({ ...restaurant, name: e.target.value }) }}
                      onBlur={() => setError(null)} />
                  </div>
                  <div className="form-group">
                    <label htmlFor="exampleInputNumber1">Category</label>
                    <input type="text" name="category" className="form-control" id="exampleInputNumber1"
                      value={restaurant.category}
                      onChange={(e) => { setRestaurant({ ...restaurant, category: e.target.value }) }}
                      onBlur={() => setError(null)} />
                  </div>
                  <div className="form-group">
                    <label htmlFor="exampleInputName1">City</label>
                    <input type="text" name="city" className="form-control" id="exampleInputName1"
                      value={restaurant.city}
                      onChange={(e) => { setRestaurant({ ...restaurant, city: e.target.value }) }}
                      onBlur={() => setError(null)} />
                  </div>
                  <div className="form-group">
                    <label htmlFor="exampleInputEmail1">Address</label>
                    <input type="text" name="address" className="form-control" id="exampleInputEmail1"
                      value={restaurant.address}
                      onChange={(e) => { setRestaurant({ ...restaurant, address: e.target.value }) }}
                      onBlur={() => setError(null)} />
                  </div>
                  <div className="form-group">
                    <label htmlFor="exampleInputEmail1">Working hours (07:00-23:00)</label>
                    <input type="text" name="workingHours" className="form-control" id="exampleInputEmail1"
                      value={restaurant.workingHours}
                      onChange={(e) => { setRestaurant({ ...restaurant, workingHours: e.target.value }) }}
                      onBlur={() => setError(null)} />
                  </div>
                  <div className="form-group">
                    <label htmlFor="exampleInputEmail1">Cover Photo</label>
                    <input type="file" name="image" className="form-control" id="exampleInputEmail1"
                      onBlur={() => setError(null)} onChange={handleFileChange} />
                  </div>
                  <div className="text-center">
                    <LoadingButton
                      disabled={error !== null}
                      endIcon={<SendIcon />}
                      onClick={createRestaurant}
                      loading={loading}
                      loadingPosition="end"
                      variant="contained"
                    >
                      {edit ? 'Edit Restaurant' : 'Create Restaurant'}
                    </LoadingButton>
                    {/* <button type="submit" className="btn btn-primary btn-block">{edit ? 'Edit Restaurant' : 'Create Restaurant'}</button> */}
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
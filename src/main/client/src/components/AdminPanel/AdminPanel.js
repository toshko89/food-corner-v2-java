import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { getAllUsers, makeAdmin } from "../../services/authService.js";
import { showDialogFailed } from "../../utils/dialogUtils.js";
import AdminPanelCard from "./AdminPanelCard.js";
import { logoutStateChange } from "../../app/auth.js";

export default function AdminPanel() {

  const [users, setUsers] = useState([]);
  const [change, setChange] = useState({});
  const user = useSelector(state => state.auth.id);
  const admin = useSelector(state => state.auth.userRole);
  const userCredentials = useSelector(state => state.auth.name || state.auth.email);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  async function switchRoles(userId) {
    const res = await makeAdmin(userId);
    if (res.status && res.status !== 200) {
      showDialogFailed(res.status, "You are not authorized to edit this user!");
      dispatch(logoutStateChange());
      localStorage.removeItem('Authorization');
      navigate('/login');
      return;
    }
    setChange(res);
  }
 
  useEffect(() => {
    (async function fetchData() {
      try {
        const res = await getAllUsers();
        if (res.status && res.status !== 200) {
          showDialogFailed(res.status, "You are not authorized to view this page!")
          navigate('/');
          return;
        }
        setUsers(res)
      } catch (error) {
        throw new Error(error)
      }
    })();
  }, [change])

  return (
    <>
      <div className="container position-relative">
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
                <Link to={`/my-account/${user}/create-restaurant`} className="d-flex w-100 align-items-center border-bottom p-3">
                  <div className="left mr-3">
                    <h6 className="font-weight-bold mb-1 text-dark">Create Restaurant</h6>
                    <p className="small text-muted m-0">Add or remove a restaurant</p>
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
                {admin === "ADMIN" && <Link to={`/my-account/${user}/admin`} className="d-flex w-100 align-items-center px-3 py-4">
                  <div className="left mr-3">
                    <h6 className="font-weight-bold m-0 text-dark"><i className="feather-lock bg-warning text-white p-2 rounded-circle mr-2"></i> Admin Panel</h6>
                  </div>
                  <div className="right ml-auto">
                    <h6 className="font-weight-bold m-0"><i className="feather-chevron-right"></i></h6>
                  </div>
                </Link>}
              </div>
            </div>
          </div>
          <div className="col-md-8 mb-3">
            <div className="rounded shadow-sm p-4 bg-white">
              <h5 className="mb-4">Admin Panel</h5>
              <div className="row m-0">
                <h6 className="p-3 m-0 bg-light w-100">Users data</h6>
                <div className="col-md-12 px-0 border-top">
                  {users.map((user) => <AdminPanelCard key={user.id} user={user} switchRoles={switchRoles} />)}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
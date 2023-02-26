import IconButton from '@mui/material/IconButton';
import { Link, useNavigate } from "react-router-dom";
import ModeEditOutlineTwoToneIcon from '@mui/icons-material/ModeEditOutlineTwoTone';
import LunchDiningRoundedIcon from '@mui/icons-material/LunchDiningRounded';
import DeleteForeverRoundedIcon from '@mui/icons-material/DeleteForeverRounded';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import FavoriteIcon from '@mui/icons-material/Favorite';
import MapsUgcRoundedIcon from '@mui/icons-material/MapsUgcRounded';
import { Modal, Button, Text } from "@nextui-org/react";
import { LoadingButton } from '@mui/lab';
import ButtonGroup from '@mui/material/ButtonGroup';
import AddProductModal from './AddProductModal.js';
import { useState } from 'react';
import { deleteRestaurantById } from '../../services/restaurantService.js';
import { useDispatch, useSelector } from 'react-redux';
import { addToFavorites, removeFromFavorites } from '../../app/auth.js';
import AddCommentModal from '../Comments/AddCommentModal.js'

export default function RestaurantMenuNavIcons({ isOwner, restaurantInFavorite }) {
  const user = useSelector(state => state.auth.id);
  const restaurant = useSelector(state => state.restaurant);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [visible, setVisible] = useState(false);
  const [loading, setLoading] = useState(false);
  const [deleteModal, setDeleteModal] = useState(false);
  const handler = () => setVisible(true);
  const handlerDeleteModal = () => setDeleteModal(true);
  const closeHandlerDeleteModal = () => {
    setDeleteModal(false);
  };
  function handleClick() {
    setLoading(true);
  }
  const [visibleCommentModal, setVisibleCommentModal] = useState(false);
  const handlerCommentModal = () => setVisibleCommentModal(true);
  const closeHandlerCommentModal = () => {
    setVisibleCommentModal(false);
  };

  async function deleteRestaurant(user,restaurantId) {
    try {
      const res = await deleteRestaurantById(user,restaurantId);
      if (res.status === 200) {
        navigate('/');
      } else {
        navigate('/login', { replace: true });
        return;
      }
    } catch (error) {
      setLoading(false);
      setError(error);
      throw new Error(error);
    }
  }

  function addToFav() {
    dispatch(addToFavorites(restaurant.id));
  }

  function removeFromFav() {
    dispatch(removeFromFavorites(restaurant.id));
  }

  return (
    <>
      <ButtonGroup variant="outlined" aria-label="outlined primary button group">
        {error && <div className="error-container" role="alert"><p>{error}</p></div>}
        {isOwner &&
          <>
            <IconButton component={Link} to={`/restaurants/${user}/edit/${restaurant.id}`} variant="contained" aria-label="edit" size="large">
              <ModeEditOutlineTwoToneIcon fontSize="large" />
            </IconButton>
            <IconButton aria-label="add-menu" size="large" onClick={handler} >
              <LunchDiningRoundedIcon fontSize="large" />
            </IconButton>
            <IconButton onClick={handlerDeleteModal} aria-label="delete" size="large">
              <DeleteForeverRoundedIcon fontSize="large" />
            </IconButton>
          </>
        }
        {!restaurantInFavorite &&
          <IconButton aria-label="favorites" onClick={addToFav} color="error" size="large">
            <FavoriteBorderIcon fontSize="large" />
          </IconButton>}
        {restaurantInFavorite &&
          <IconButton aria-label="favorites" onClick={removeFromFav} color="error" size="large">
            <FavoriteIcon fontSize="large" />
          </IconButton>}
        <IconButton aria-label="comments" onClick={handlerCommentModal} color="primary" size="large">
          <MapsUgcRoundedIcon fontSize="large" />
        </IconButton> 
      </ButtonGroup>
      <AddCommentModal visibleCommentModal={visibleCommentModal} closeHandlerCommentModal={closeHandlerCommentModal}/>
      {isOwner &&
        <>
          <AddProductModal setVisible={setVisible} visible={visible} />
          <Modal
            closeButton
            aria-labelledby="modal-title"
            open={deleteModal}
            onClose={closeHandlerDeleteModal}
          >
            <Modal.Header>
              <Text b id="modal-title" size={18}>
                Are you sure you want to delete this restaurant ({restaurant.name})?
              </Text>
            </Modal.Header>
            <Modal.Footer>
              <Button auto flat color="error" onClick={closeHandlerDeleteModal}>
                Close
              </Button>
              <LoadingButton
                onClick={() => { deleteRestaurant(user,restaurant.id); handleClick(); }}
                loading={loading}
                variant="contained"
              >
                Delete
              </LoadingButton>
            </Modal.Footer>
          </Modal>
        </>}
    </>
  )
}
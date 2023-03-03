import React from 'react';
import formatDate from "../../utils/dateFormat.js";
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { Rating } from '@mui/material';

export default function CommentCard({ comment, user, deleteComment, setVisibleEditModal }) {

  const canChange = user.id === comment?.owner?.id || user?.userRole?.includes("ADMIN");
  const handlerCommentModal = () => setVisibleEditModal({ visible: true, comment: comment.id });

  return (
    <>
      <div className="reviews-members py-3">
        <div className="media">
          <div className="media-body">
            <div className="reviews-members-header">
              <h5 className="mb-0">{comment.title}</h5>
              <p className="text-muted large">{formatDate(comment.date)}</p>
            </div>
            <div className="text-muted large">
            <Rating name="read-only" value={comment.rating} readOnly />
              <p>{comment.comment}</p>
            </div>
          </div>
          {user.id && canChange &&
            <>
              <IconButton onClick={() => handlerCommentModal()} aria-label="update" color="primary">
                <EditIcon />
              </IconButton>
              <IconButton onClick={() => deleteComment(comment.id)} aria-label="delete" color="error">
                <DeleteIcon />
              </IconButton>
            </>
          }
        </div>
      </div >
    </>
  )
}
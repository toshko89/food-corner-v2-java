import React from 'react';
import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import { editCommentById, getRestaurantComments } from "../../services/commentService.js";
import CommentCard from "./CommentCard.js";
import { useSelector } from 'react-redux';
import { deleteCommentById } from '../../services/commentService.js';
import EditCommentModal from "./EditCommentModal.js";

export default function AllComments() {

  const [visibleEditModal, setVisibleEditModal] = useState({ visible: false, comment: null });
  const closeHandlerEditModal = () => {
    setVisibleEditModal({ visible: false, comment: null });
  };
  const [comments, setComments] = useState([]);
  const user = useSelector(state => state.auth);
  const { state } = useLocation();
  const { id } = useParams();

  useEffect(() => {
    getRestaurantComments(id)
      .then(res => {
        setComments(res);
      })
      .catch(err => {
        console.log(err);
      })
  }, [id])

  async function deleteComment(commentId) {
    const res = await deleteCommentById(commentId);
    if (res.status === 200) {
      const comment = comments.find(comment => comment.id === commentId);
      const index = comments.indexOf(comment);
      comments.splice(index, 1);
      setComments([...comments]);
    }
  }



  return (
    <>
      <div className="container position-relative" >
        <div className="row">
          <div className="bg-white p-3 mb-3 restaurant-detailed-ratings-and-reviews shadow-sm rounded">
            <h2 className="mb-1">All Ratings and Reviews for {state}</h2>
            {comments.length > 0
              ? comments.map(comment =>
                <CommentCard key={comment.id}
                  comment={comment}
                  user={user}
                  deleteComment={deleteComment}
                  setVisibleEditModal={setVisibleEditModal}
                />)
              : <h2 className="font-weight-bold mb-3">There are no comments yet</h2>}
          </div>
        </div>
      </div>
      <EditCommentModal visibleEditModal={visibleEditModal} closeHandlerEditModal={closeHandlerEditModal} setComments={setComments} />
    </>
  )
}
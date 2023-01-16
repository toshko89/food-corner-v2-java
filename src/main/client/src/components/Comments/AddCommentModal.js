import React from 'react';
import { Modal, Button, Text, Input, Row } from "@nextui-org/react";
import Rating from '@mui/material/Rating';
import { useState } from "react";
import { useParams } from "react-router-dom";
import { newComment } from "../../services/commentService.js";

export default function AddCommentModal({ visibleCommentModal, closeHandlerCommentModal }) {

  const { id } = useParams();
  const [value, setValue] = useState(null);
  const [error, setError] = useState(false);
  const [comment, setComment] = useState({ name: '', comments: '' });

  async function sendComment() {
    if (comment.name.trim() === '' || comment.comments.trim() === '' || !value) {
      setError('All fields are required');
      return;
    }

    const myComment = {
      name: comment.name,
      comments: comment.comments,
      rating: value
    }

    try {
      const res = await newComment(id, myComment);
      if (res.message) {
        setError(res.message)
        return;
      }
      closeHandlerCommentModal();
      setComment({ name: '', comments: '' });
      setValue(null);
    } catch (error) {
      setError(error)
      return;
    }

  }

  return (
    <Modal
      aria-labelledby="modal-title"
      open={visibleCommentModal}
      onClose={closeHandlerCommentModal}
    >
      <Modal.Header aria-label="modal-header">
        <Text id="modal-title" size={18}>
          New Comment
        </Text>
      </Modal.Header>
      <Modal.Body aria-label="modal-body">
        <Input
          aria-label="modal-name-input"
          onChange={(e) => setComment({ ...comment, name: e.target.value })}
          onBlur={() => setError(false)}
          clearable
          bordered
          fullWidth
          color="primary"
          size="lg"
          placeholder="Name"
        />
        <Input
          aria-label="modal-comment-input"
          onChange={(e) => setComment({ ...comment, comments: e.target.value })}
          onBlur={() => setError(false)}
          clearable
          bordered
          fullWidth
          color="primary"
          size="lg"
          placeholder="Comment"
        />
        <Row justify="space-between">
          <Rating
            aria-label="modal-rating"
            name="simple-controlled"
            value={value}
            onBlur={() => setError(false)}
            onChange={(event, newValue) => {
              setValue(newValue);
            }}
          />
        </Row>
        {error && <Text color="red" size={20}>{error}</Text>}
      </Modal.Body>
      <Modal.Footer>
        <Button auto flat color="error" onClick={closeHandlerCommentModal}>
          Close
        </Button>
        <Button auto disabled={error !== false} onClick={sendComment}>
          Comment
        </Button>
      </Modal.Footer>
    </Modal>
  )
}
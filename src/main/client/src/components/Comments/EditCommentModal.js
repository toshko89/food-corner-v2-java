import React from 'react';
import { Modal, Button, Text, Input, Row } from "@nextui-org/react";
import Rating from '@mui/material/Rating';
import { useParams } from "react-router-dom";
import { editCommentById } from "../../services/commentService.js";
import { useState } from "react";

export default function EditCommentModal({ visibleEditModal, closeHandlerEditModal, setComments }) {

  const { id } = useParams();
  const [value, setValue] = useState(null);
  const [error, setError] = useState(false);
  const [comment, setComment] = useState({ name: '', comments: '' });

  async function editCommet() {
    if (comment.name.trim() === '' || comment.comments.trim() === '' || !value) {
      setError('All fields are required');
      return;
    }

    const commentId = visibleEditModal.comment;
    const myComment = {
      name: comment.name,
      comments: comment.comments,
      rating: value
    }

    try {
      const res = await editCommentById(id, myComment, commentId);
      if (res.message) {
        setError(res.message)
        return;
      }
      closeHandlerEditModal();
      setComments(oldState => [...res]);
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
      open={visibleEditModal.visible}
      onClose={closeHandlerEditModal}
    >
      <Modal.Header aria-label="modal-header">
        <Text id="modal-title" size={18}>
          Edit Comment
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
        <Button auto flat color="error" onClick={closeHandlerEditModal}>
          Close
        </Button>
        <Button auto disabled={error !== false} onClick={editCommet}>
          Edit
        </Button>
      </Modal.Footer>
    </Modal>
  )
}
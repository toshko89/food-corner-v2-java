import { Modal, Button, Text, Input, Row } from "@nextui-org/react";
import Rating from '@mui/material/Rating';
import { editCommentById } from "../../services/commentService.js";
import { useState } from "react";
import { showDialogFailed } from "../../utils/dialogUtils.js";

export default function EditCommentModal({ visibleEditModal, closeHandlerEditModal, setComments }) {

  const [value, setValue] = useState(null);
  const [error, setError] = useState(false);
  const [comment, setComment] = useState({ title: '', comment: '' });

  async function editCommet() {
    if (comment.title.trim() === '' || comment.comment.trim() === '' || !value) {
      setError('All fields are required');
      return;
    }

    const commentId = visibleEditModal.comment;
    const myComment = {
      title: comment.title,
      comment: comment.comment,
      rating: value
    }

    try {
      const res = await editCommentById(myComment, commentId);
      if (res.status === 401 || res.status === 400) {
        closeHandlerEditModal()
        showDialogFailed(res.status, res.statusText)
        return;
      }
      closeHandlerEditModal();
      setComments(oldState => [...res]);
      setComment({ title: '', comment: '' });
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
          onChange={(e) => setComment({ ...comment, title: e.target.value })}
          onBlur={() => setError(false)}
          clearable
          bordered
          fullWidth
          color="primary"
          size="lg"
          placeholder="Title"
        />
        <Input
          aria-label="modal-comment-input"
          onChange={(e) => setComment({ ...comment, comment: e.target.value })}
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
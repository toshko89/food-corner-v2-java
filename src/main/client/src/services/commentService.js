const REACT_APP_BASE_URL = process.env.REACT_APP_BASE_URL;

async function newComment(id, comment) {
  try {
    const res = await fetch(REACT_APP_BASE_URL + `/restaurants/${id}/comments-create`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', 'Authorization': JSON.parse(localStorage.getItem("Authorization")) },
      credentials: 'include',
      body: JSON.stringify(comment),
    });
    return res;
  } catch (error) {
    throw new Error(error)
  }
}

async function getRestaurantComments(id) {
  try {
    const res = await fetch(REACT_APP_BASE_URL + `/restaurants/${id}/comments`, {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
    });
    if (res.status === 200) {
      return res.json();
    }
    return res;
  } catch (error) {
    throw new Error(error)
  }
}

async function deleteCommentById(id) {
  try {
    const res = await fetch(REACT_APP_BASE_URL + `/restaurants/${id}/comments`, {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
    });
    return res;
  } catch (error) {
    throw new Error(error)
  }
}

async function editCommentById(id, comment, commentId) {
  try {
    const res = await fetch(REACT_APP_BASE_URL + `/restaurants/${id}/comments/${commentId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(comment),
    });
    return res.json();
  } catch (error) {
    throw new Error(error)
  }
}

export { newComment, getRestaurantComments, deleteCommentById, editCommentById }
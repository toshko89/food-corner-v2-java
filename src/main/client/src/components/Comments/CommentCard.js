// import React from 'react';
// import formatDate from "../../utils/dateFormat.js";
// import IconButton from '@mui/material/IconButton';
// import DeleteIcon from '@mui/icons-material/Delete';
// import EditIcon from '@mui/icons-material/Edit';

// export default function CommentCard({ comment, user, deleteComment, setVisibleEditModal }) {
//   const ownComment = user._id === comment?.owner;
//   const handlerCommentModal = () => setVisibleEditModal({ visible: true, comment: comment._id });

//   return (
//     <>
//       <div className="reviews-members py-3">
//         <div className="media">
//           <div className="media-body">
//             <div className="reviews-members-header">
//               <h5 className="mb-0">{comment.name}</h5>
//               <p className="text-muted large">{formatDate(comment.date)}</p>
//             </div>
//             <div className="text-muted large">
//               <p>{comment.comment}</p>
//             </div>
//           </div>
//           {user._id && ownComment &&
//             <>
//               <IconButton onClick={() => handlerCommentModal(comment._id)} aria-label="delete" color="primary">
//                 <EditIcon />
//               </IconButton>
//               <IconButton onClick={() => deleteComment(comment._id)} aria-label="delete" color="error">
//                 <DeleteIcon />
//               </IconButton>
//             </>
//           }
//         </div>
//       </div >
//     </>
//   )
// }
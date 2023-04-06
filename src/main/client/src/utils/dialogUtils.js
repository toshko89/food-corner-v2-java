import Swal from "sweetalert2";

const SwalDUPopup = Swal.mixin({
  reverseButtons: true,
  confirmButtonColor: "#0066CC",
  cancelButtonColor: "#aeaeae"
});

const showDialogFailed = (errorCode, errorMsg) => {
  if (errorCode === 401) {
    localStorage.removeItem("Authorization");
    SwalDUPopup.fire({
      icon: "error",
      title: "Your session has expired. Please login again.",
      // title: "",
      html: "<span style='white-space: pre-line'>" + errorMsg + "</span>",
    });
  } else {
    SwalDUPopup.fire({
      icon: "error",
      title: errorCode,
      // title: "",
      html: "<span style='white-space: pre-line'>" + errorMsg + "</span>",
    });
  }
};

const showDialogSuccess = (title, text, functionByConfirm) => {
  SwalDUPopup.fire({
    icon: "success",
    title: title,
    text: text,
    allowOutsideClick: false,
    customClass: {
      confirmButton: "btn btn-primary",
    },
  }).then((result) => {
    if (result.isConfirmed) {
      if (functionByConfirm) {
        functionByConfirm();
      }
    }
  });
};

export { showDialogFailed, showDialogSuccess };

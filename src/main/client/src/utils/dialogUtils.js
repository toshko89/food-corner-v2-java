import Swal from "sweetalert2";

const SwalDUPopup = Swal.mixin({
  reverseButtons: true,
  confirmButtonColor: "#0066CC",
  cancelButtonColor: "#aeaeae"
});

const SwalDUPopupSave = Swal.mixin({
  reverseButtons: true,
  confirmButtonColor: "#0066CC",
  cancelButtonColor: "#808080",
  denyButtonText: "#0000FF"
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

export { showDialogFailed };

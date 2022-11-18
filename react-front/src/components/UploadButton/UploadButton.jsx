import React from "react";
import styles from "./UploadButton.module.css";

function UploadButton({ uploadFile }) {
  return (
    <button className={styles.button} type="submit" onClick={uploadFile}>
      Upload
    </button>
  );
}

export { UploadButton };

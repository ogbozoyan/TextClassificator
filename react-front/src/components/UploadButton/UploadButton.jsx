import React from "react";
import styles from "./UploadButton.module.scss";

function UploadButton({ uploadData }) {
  return (
    <button className={styles.button} type="submit" onClick={uploadData}>
      Upload
    </button>
  );
}

export { UploadButton };

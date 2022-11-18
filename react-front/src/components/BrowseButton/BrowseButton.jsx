import React from "react";
import styles from "./BrowseButton.module.css";

function BrowseButton({ onFile }) {
  return (
    <>
      <div className={styles.btnWrapper}>
        <input className={styles.button} type="file" onChange={onFile} />
      </div>
    </>
  );
}

export { BrowseButton };

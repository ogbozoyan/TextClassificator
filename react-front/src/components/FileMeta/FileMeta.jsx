import React from "react";
import styles from "./FileMeta.module.css";

function FileMeta({ file }) {
  return (
    <>
      <div>
        <p className={styles.text}>
          <b>file meta</b>
        </p>
        <p className={styles.text}>
          <b>name:</b>
          <br />
          {file.name}
        </p>
        <p className={styles.text}>
          <b>size:</b>
          <br />
          {file.size}
        </p>
        <p className={styles.text}>
          <b>type:</b>
          <br />
          {file.type}
        </p>
      </div>
    </>
  );
}

export { FileMeta };

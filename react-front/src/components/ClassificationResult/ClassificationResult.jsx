import React from "react";
import styles from "./ClassificationResult.module.css";

function ClassificationResult({ result }) {
  return (
    <>
      <div>
        <p className={styles.text}>
          <b>result:</b>
          <br />
          {result}
        </p>
      </div>
    </>
  );
}

export { ClassificationResult };

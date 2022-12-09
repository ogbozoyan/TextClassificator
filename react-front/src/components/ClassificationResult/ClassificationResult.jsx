import React from "react";
import styles from "./ClassificationResult.module.scss";
import { useSelector } from "react-redux";

function ClassificationResult() {
  const result = useSelector((state) => state.result);
  return (
    <>
      {result !== "" && (
        <div>
          <p className={styles.text}>
            <b>result:</b>
            <br />
            {result}
          </p>
        </div>
      )}
    </>
  );
}

export { ClassificationResult };

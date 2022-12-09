import React from "react";
import styles from "./FileMeta.module.scss";
import { useSelector } from "react-redux";

function FileMeta() {
  const payload = useSelector((state) => state.payload);

  return (
    <>
      {payload && (
        <div className={styles.container}>
          <div className={styles.container__fields}>
            <p className={styles.container__text}>
              <b>name:</b>
            </p>
            <p className={styles.container__text}>
              <b>size:</b>
            </p>
            <p className={styles.container__text}>
              <b>type:</b>
            </p>
          </div>

          <div className={styles.container__values}>
            <p className={styles.container__text}>{payload.name}</p>
            <p className={styles.container__text}>{payload.size}</p>
            <p className={styles.container__text}>{payload.type}</p>
          </div>
        </div>
      )}
    </>
  );
}

export { FileMeta };

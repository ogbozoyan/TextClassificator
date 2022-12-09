import React from "react";
import styles from "./Action.module.scss";
import { useSelector } from "react-redux";

function Action({ onData }) {
  const type = useSelector((state) => state.type);

  return (
    <>
      {type === "file" ? (
        <>
          <input
            type="file"
            id="file"
            onChange={(e) => onData(e, type)}
            accept="*"
          />
          <div className={styles.addDocumentBtn}>
            <label className={styles.button} htmlFor="file">
              <span className="material-icons">attachment</span>
            </label>
          </div>
        </>
      ) : (
        <>
          <textarea
            className={styles.textarea}
            rows="8"
            placeholder="text"
            onChange={(e) => onData(e, type)}
          ></textarea>
        </>
      )}
      <input type="text" onChange={(e) => onData(e, "key")} />
    </>
  );
}

export { Action };

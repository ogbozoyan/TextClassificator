import styles from "./App.module.css";
import { useState } from "react";
import { Header } from "./components/Header";
import { ClassificationResult } from "./components/ClassificationResult";
import { FileMeta } from "./components/FileMeta";
import { Action } from "./components/Action";
import { UploadButton } from "./components/UploadButton";

import { Routes, Route, Navigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

function App() {
  const dispatch = useDispatch();
  const payload = useSelector((state) => state.payload);
  const key = useSelector((state) => state.key);
  const type = useSelector((state) => state.type);
  const result = useSelector((state) => state.result);
  const error = useSelector((state) => state.error);

  function uploadData(event) {
    event.preventDefault();
    let data = new FormData();
    data.append("key", key);
    data.append("type", type);
    data.append("payload", payload);
    console.log(data);

    fetch("http://172.20.10.7:8080/upload/", {
      method: "POST",
      body: data,
    })
      .then((response) => {
        console.log(response);
        return response.json();
      })
      .then((response) => {
        console.log(response);
        let cl = JSON.parse(response.result);
        dispatch({
          type: "SET_RESULT",
          payload: `id: ${cl.id} | category: ${cl.result}`,
        });
      })
      .catch((err) => {
        dispatch({
          type: "SET_ERROR",
          payload: `error: ${err}`,
        });
      });
  }

  function onData(event, type) {
    if (type === "file") {
      dispatch({
        type: "SET_PAYLOAD",
        payload: event.target.files[0],
      });
      return;
    } else if (type === "text") {
      dispatch({
        type: "SET_PAYLOAD",
        payload: event.target.value,
      });
    } else if (type === "key") {
      dispatch({
        type: "SET_KEY",
        payload: event.target.value,
      });
    }
  }

  return (
    <>
      <form className={styles.form}>
        <Routes>
          <Route
            path="/:type"
            element={
              <>
                <Header />
                <h1>Classificator</h1>
                <Action onData={onData} />
                <UploadButton uploadData={uploadData} />
                {result !== "" && <ClassificationResult />}
                {payload && type === "file" && <FileMeta />}
              </>
            }
          />
          <Route path="*" element={<Navigate to="/file" />} />
        </Routes>
      </form>
    </>
  );
}

export default App;

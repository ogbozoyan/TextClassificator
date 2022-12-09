import styles from "./App.module.css";
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

  function uploadData(event) {
    event.preventDefault();

    if(type == "text"){
    let data = {
      key: key,
      payload: payload,
      };

    fetch("http://127.0.0.1:8080/upload/text/", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) => {
        console.log(response);
        return response.json();
      })
      .then((response) => {
        console.log(response);
        dispatch({
          type: "SET_RESULT",
          payload: `id: ${response.id} | category: ${response.result}`,
        });
      })
      .catch((err) => {
        dispatch({
          type: "SET_ERROR",
          payload: `error: ${err}`,
        });
      });
    }
    if(type == "file"){
        alert("file");
        let data = new FormData();
            data.append("key", key);
            data.append("file", payload);
            console.log(...data);

        fetch("http://127.0.0.1:8080/upload/file/", {
          method: "POST",
          body: data,
        })
          .then((response) => {
            console.log(response);
            return response.json();
          })
          .then((response) => {
            console.log(response);
            dispatch({
              type: "SET_RESULT",
              payload: `id: ${response.id} | category: ${response.result}`,
            });
          })
          .catch((err) => {
            dispatch({
              type: "SET_ERROR",
              payload: `error: ${err}`,
            });
          });
        }
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
                {type === "file" && <FileMeta />}
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

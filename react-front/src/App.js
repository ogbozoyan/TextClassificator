import styles from "./App.module.css";
import { Header } from "./components/Header";
import { ClassificationResult } from "./components/ClassificationResult";
import { FileMeta } from "./components/FileMeta";
import { Action } from "./components/Action";
import { UploadButton } from "./components/UploadButton";
import { Login } from "./components/Login";

import { Routes, Route, Navigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

function App() {
  const dispatch = useDispatch();
  const payload = useSelector((state) => state.payload);
  const key = useSelector((state) => state.key);
  const type = useSelector((state) => state.type);
  const result = useSelector((state) => state.result);
  const is_auth = useSelector((state) => state.is_auth);

  function uploadData(event) {
    event.preventDefault();
    let data = {};
    let headers = {};

    if (type === "text") {
      data = {

        payload: payload,
      };
      data = JSON.stringify(data);
      headers = {
        "Content-Type": "application/json",
      };
    }

    if (type === "file") {
      data = new FormData();

      data.append("file", payload);
    }

    fetch(`http://127.0.0.1:8080/upload/${type}/`, {
      method: "POST",
      headers: headers,
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
                {is_auth ? (
                  <>
                    <Header />
                    <h1>Classificator</h1>
                    <Action onData={onData} />
                    <UploadButton uploadData={uploadData} />
                    {result !== "" && <ClassificationResult />}
                    {type === "file" && <FileMeta />}
                  </>
                ) : (
                  <>
                    <Login />
                  </>
                )}
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

import React from "react";
import styles from "./Login.module.scss";
import { useSelector, useDispatch } from "react-redux";
import { useState } from "react";

function Login() {
  const dispatch = useDispatch();
  // const is_auth = useSelector((state) => state.is_auth);
  const [token, setToken] = useState("");

  function auth() {
    fetch("http://127.0.0.1:8080/token/", {
      method: "POST",
      body: JSON.stringify({ token: token }),
    })
      .then((response) => {
        // console.log(response);
        return response.json();
      })
      .then((response) => {
        // console.log(response);
        dispatch({
          type: "SET_AUTH",
          payload: true,
        });
      })
      .catch((err) => {
        console.log("auth error:", err);
      });
  }

  return (
    <>
      <input type="text" onChange={(e) => setToken(e.target.value)} />
      <button onClick={() => auth()}></button>
    </>
  );
}

export { Login };

import React from "react";
import styles from "./Login.module.scss";
import { useSelector, useDispatch } from "react-redux";
import { useState } from "react";

function Login() {
  const dispatch = useDispatch();
  const [token, setToken] = useState("");

  function auth(e) {
    e.preventDefault();
    dispatch({
          type: "SET_AUTH",
          payload: true,
    });
    dispatch({
           type: "SET_TOKEN",
           payload: token,
    });
  }

  return (
    <>
      <input type="text" onChange={(e) => setToken(e.target.value)} />
      <button onClick={(e) => auth(e)}>Send token</button>
    </>
  );
}

export { Login };

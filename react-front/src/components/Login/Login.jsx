import React from "react";
import styles from "./Login.module.scss";
import { useSelector, useDispatch } from "react-redux";
import { useState } from "react";

function Login() {
  const dispatch = useDispatch();
  const [token, setToken] = useState("");

  function auth(e) {
    e.preventDefault();
    fetch("http://127.0.0.1:8080/token/", {
      method: "POST",
      headers : {
              "Content-Type": "application/json",
            },
      body: JSON.stringify({ token: token }),
    })
      .then((response) => {

        return response.json();
      })
      .then((response) => {
        console.log(response);
        if(response.result){
        dispatch({
          type: "SET_AUTH",
          payload: true,
        });
        } else {
          alert("Wrong token");
        }
      }
    )
      .catch((err) => {
        console.log("auth error:", err);
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

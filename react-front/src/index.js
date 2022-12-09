import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { BrowserRouter } from "react-router-dom";
import { Provider } from "react-redux";
import { createStore } from "redux";

let defaultState = {
  key: "",
  type: "file",
  payload: 0,
  result: "",
  error: "",
  is_auth: false,
};

function dataReduser(state = defaultState, action) {
  switch (action.type) {
    case "SET_TYPE":
      return { ...state, type: action.payload };
    case "SET_PAYLOAD":
      return { ...state, payload: action.payload };
    case "SET_KEY":
      return { ...state, key: action.payload };
    case "SET_RESULT":
      return { ...state, result: action.payload };
    case "SET_ERROR":
      return { ...state, result: action.payload };
    case "SET_AUTH":
      return { ...state, is_auth: action.payload };
    default:
      return state;
  }
}

let store = createStore(dataReduser);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <Provider store={store}>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </Provider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

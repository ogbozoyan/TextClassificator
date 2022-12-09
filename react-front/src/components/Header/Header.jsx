import styles from "./Header.module.scss";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import { useDispatch } from "react-redux";

function Header() {
  const { type } = useParams();
  const dispatch = useDispatch();
  dispatch({ type: "SET_TYPE", payload: type });
  return (
    <header className={styles.header}>
      <Link
        to="/text"
        className={`${styles.button} ${styles.header__btn} ${
          type === "text" ? styles.header__btn_active : ""
        } ${styles.header__btn_l}`}
      >
        <span className="text">Text</span>
      </Link>
      <Link
        to="/file"
        className={`${styles.button} ${styles.header__btn} ${
          type === "file" ? styles.header__btn_active : ""
        } ${styles.header__btn_r}`}
      >
        <span className="text">File</span>
      </Link>
    </header>
  );
}

export { Header };

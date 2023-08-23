import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../helpers/AuthContext";
import "./LogoutButton.css"

function LogoutButton() {
    let navigate = useNavigate();
    const {authState, setAuthState} = useContext(AuthContext);
    const onLogout = () => {
        localStorage.removeItem("token");
        setAuthState(false);
        navigate("/login");
    }
    return (
        <button className="logout-button" onClick={onLogout}>Logout</button>
    )
}

export default LogoutButton;
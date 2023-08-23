import React from "react";
import { useNavigate } from "react-router-dom";
import "./LogButton.css";

function LogButton() {
    let navigate = useNavigate();

    const onClick = () => {
        navigate("/logs");
    }
    return (
        <button className="log-button" onClick={onClick}>Logs</button>
    )
}

export default LogButton;
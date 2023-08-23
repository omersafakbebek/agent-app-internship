import React from "react";
import { useNavigate } from "react-router-dom";
import "./ConfigButton.css";

function ConfigButton() {
    let navigate = useNavigate();

    const onClick = () => {
        navigate("/config");
    }
    return (
        <button className="config-button" onClick={onClick}>Config</button>
    )
}

export default ConfigButton;
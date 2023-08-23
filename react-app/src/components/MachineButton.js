import React from "react";
import { useNavigate } from "react-router-dom";
import "./MachineButton.css";

function MachineButton() {
    let navigate = useNavigate();

    const onClick = () => {
        navigate("/");
    }
    return (
        <button className="machine-button" onClick={onClick}>Machines</button>
    )
}

export default MachineButton;
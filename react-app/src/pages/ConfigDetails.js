// ConfigDetailsPage.js
import React, { useEffect, useState } from 'react';
import './ConfigDetails.css';
import { useParams, useNavigate } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from '../helpers/AuthContext';
import LogoutButton from '../components/LogoutButton';
import { SpinnerContext } from '../helpers/SpinnerContext';
import LogButton from '../components/LogButton';
import MachineButton from "../components/MachineButton";
import ConfigButton from "../components/ConfigButton";
import LoadingSpinner from "../components/LoadingSpinner";

function ConfigDetails() {
  const { id } = useParams();
  const [isLoading, setIsLoading] = useState(true);
  const { authState, setAuthState } = useContext(AuthContext);
  const { isAuthSet, setIsAuthSet } = useContext(SpinnerContext);
  const [selectedConfigType, setSelectedConfigType] = useState('GENERAL');
  const [selectedTaskType, setSelectedTaskType] = useState('ALERT');
  const [username, setUsername] = useState('');
  const [hostname, setHostname] = useState('');
  const [attributes, setAttributes] = useState([{ name: '', value: '' }]);

  const [config, setConfig] = useState();
  const [isEditable, setIsEditable] = useState(false);
let navigate = useNavigate();
  const fetchConfig = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        setAuthState(false);
        navigate("/login");
      }
      const requestOptions = {
        headers: { "token": token }
      }
      const response = await fetch(`http://localhost:8080/config/${id}`, requestOptions);
      if (!response.ok) {
        setAuthState(false);
        navigate("/login");
      }
      const data = await response.json();
      setSelectedConfigType(data.configType);
      setSelectedTaskType(data.taskType);
      setAttributes(data.parameters);
      setHostname(data.machine.hostname);
      setUsername(data.username);
      setIsLoading(false);
    } catch (error) {
      setAuthState(false);
      navigate("/login");
      console.error('Error fetching data:', error);
    }
  }

  useEffect(() => {
    if (!isAuthSet) {
      return;
    }
    if (!authState) {
      navigate("/login")
    }
    fetchConfig();
  }, [isAuthSet]);
  const handleAddRow = () => {
    if (attributes[attributes.length - 1].name && attributes[attributes.length - 1].value) {
      setAttributes([...attributes, { name: '', value: '' }]);
    }
  };

  const handleAttributeChange = (index, field, value) => {
    const updatedAttributes = [...attributes];
    updatedAttributes[index][field] = value;
    setAttributes(updatedAttributes);
  };
  const handleEditClick = () => {
    setIsEditable(true);
  };
  if (isLoading || !isAuthSet) {
    return (
      <LoadingSpinner />
    )
  }
  const handleSaveClick = async () => {
    setIsEditable(false);
    const token = localStorage.getItem("token");
    if (!token) {
      setAuthState(false);
      navigate("/login");
    }
    const body = {
      configType: selectedConfigType,
      taskType: selectedTaskType,
      username: (username && username),
      machine: {
        hostname: (hostname && hostname),
      },
      parameters: attributes
    };
    const requestOptions = {
      headers: { "token": token, "Content-Type": "application/json" },
      method: "PUT",
      body: JSON.stringify(body),
    };
    const response = await fetch(`http://localhost:8080/config/${id}`, requestOptions);
    if (response.status == 401) {
      setAuthState(false);
      navigate("/login");
    }
  };

  const handleDeleteClick = async () => {
    const token = localStorage.getItem("token");
    if (!token) {
      setAuthState(false);
      navigate("/login");
    }
    const requestOptions = {
      headers: { "token": token, "Content-Type": "application/json" },
      method: "DELETE"
    };
    const response = await fetch(`http://localhost:8080/config/${id}`, requestOptions);
    if (response.status == 401) {
      setAuthState(false);
      navigate("/login");
    }
    navigate("/config");
  };

  const handleCancelClick = () => {
    fetchConfig();
    setIsEditable(false);
  }

  return (
    <div className="config-details-page">
      <MachineButton />
      <LogButton />
      <ConfigButton />
      <LogoutButton />
      <h2>Configuration Details</h2>
      <div className="config-form">
        <div>
          <label>Config Type:</label>
          <select
            value={selectedConfigType}
            disabled={!isEditable}
            onChange={(e) => setSelectedConfigType(e.target.value)}
          >
            <option value="GENERAL">General</option>
            <option value="USER">User</option>
            <option value="MACHINE">Machine</option>
          </select>
        </div>
        {selectedConfigType === 'USER' && (
          <div>
            <label>Username:</label>
            <input
              type="text"
              disabled={!isEditable}
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
        )}
        {selectedConfigType === 'MACHINE' && (
          <div>
            <label>Hostname:</label>
            <input
              type="text"
              disabled={!isEditable}
              value={hostname}
              onChange={(e) => setHostname(e.target.value)}
            />
          </div>
        )}
        <div>
          <label>Task Type:</label>
          <select
            value={selectedTaskType}
            disabled={!isEditable}
            onChange={(e) => setSelectedTaskType(e.target.value)}
          >
            <option value="ALERT">Alert</option>
            <option value="TRACK">Track</option>
            <option value="CLOSE">Close</option>
          </select>
        </div>
        {attributes.map((attr, index) => (
          <div className="attribute-row" key={index}>
            <input
              type="text"
              disabled={!isEditable}
              placeholder="Attribute Name"
              value={attr.name}
              onChange={(e) => handleAttributeChange(index, 'name', e.target.value)}
            />
            <input
              type="text"
              disabled={!isEditable}
              placeholder="Attribute Value"
              value={attr.value}
              onChange={(e) => handleAttributeChange(index, 'value', e.target.value)}
            />
          </div>
        ))}
        <button disabled={!isEditable} onClick={handleAddRow}>Add Attribute</button>

      </div>
      <div className="button-container">
        {isEditable ? (
          <>
            <button onClick={handleSaveClick}>Save</button>
            <button onClick={handleCancelClick}>Cancel</button>
          </>
        ) : (
          <>
            <button onClick={handleEditClick}>Edit</button>
            <button onClick={handleDeleteClick}>Delete</button>
          </>
        )}
      </div>
    </div>
  );
}

export default ConfigDetails;

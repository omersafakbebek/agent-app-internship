// PageComponent.js
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from '../helpers/AuthContext';
import { SpinnerContext } from '../helpers/SpinnerContext';
import LoadingSpinner from '../components/LoadingSpinner';
import './Config.css';
import MachineButton from '../components/MachineButton';
import ConfigButton from '../components/ConfigButton';
import LogButton from '../components/LogButton';
import LogoutButton from '../components/LogoutButton';

function Config() {
    const [selectedConfigType, setSelectedConfigType] = useState('GENERAL');
    const [selectedTaskType, setSelectedTaskType] = useState('ALERT');
    const [username, setUsername] = useState('');
    const [hostname, setHostname] = useState('');
    const [attributes, setAttributes] = useState([{ name: '', value: '' }]);
    const [searchQuery, setSearchQuery] = useState('');
    const [configList, setConfigList] = useState([]);

    const [isLoading, setIsLoading] = useState(true);
    const { authState, setAuthState } = useContext(AuthContext);
    const { isAuthSet, setIsAuthSet } = useContext(SpinnerContext);

    let navigate = useNavigate();
    const fetchData = async () => {
        try {
            const token = localStorage.getItem("token");
            if (!token) {
                setAuthState(false);
                navigate("/login");
            }
            const requestOptions = {
                headers: { "token": token }
            }
            const response = await fetch('http://localhost:8080/config', requestOptions);
            if (!response.ok) {
                setAuthState(false);
                navigate("/login");
            }
            const data = await response.json();
            setIsLoading(false);
            setConfigList(data);
        } catch (error) {
            setAuthState(false);
            navigate("/login");
            setIsLoading(false);
            console.error('Error fetching data:', error);
        }
    };
    useEffect(() => {
        if (!isAuthSet) {
            return;
        }
        if (!authState) {
            navigate("/login")
        }
        fetchData();
    }, [isAuthSet]);
    if (isLoading || !isAuthSet) {
        return (
            <LoadingSpinner />
        )
    }
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
    const onSave = async () => {
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
            method: "POST",
            body: JSON.stringify(body),
        };

        const response = await fetch('http://localhost:8080/config', requestOptions);
        if (response.status == 401) {
            setAuthState(false);
            navigate("/login");
        }
        fetchData();

    }
    const filteredItems = configList.filter((config) =>
        (config.id == searchQuery) ||
        (config.username && config.username.includes(searchQuery)) ||
        (config.hostname && config.hostname.includes(searchQuery)) ||
        (config.configType.includes(searchQuery)) ||
        (config.taskType.includes(searchQuery)) ||

        !searchQuery
    );


    return (
        <div className="page-container">
            <div>
                <h1>Config</h1>
            </div>
            <MachineButton />
            <LogButton />
            <ConfigButton />
            <LogoutButton />
            <div className="form-container">
                <h2>Add a new config</h2>
                <div className="form-row">
                    <label>Configuration Type:</label>
                    <select
                        value={selectedConfigType}
                        onChange={(e) => setSelectedConfigType(e.target.value)}
                    >
                        <option value="GENERAL">General</option>
                        <option value="USER">User</option>
                        <option value="MACHINE">Machine</option>
                    </select>
                </div>
                {selectedConfigType === 'USER' && (
                    <div className="form-row">
                        <label>Username:</label>
                        <input
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </div>
                )}
                {selectedConfigType === 'MACHINE' && (
                    <div className="form-row">
                        <label>Hostname:</label>
                        <input
                            type="text"
                            value={hostname}
                            onChange={(e) => setHostname(e.target.value)}
                        />
                    </div>
                )}
                <div className="form-row">
                    <label>Task Type:</label>
                    <select
                        value={selectedTaskType}
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
                            placeholder="Attribute Name"
                            value={attr.name}
                            onChange={(e) => handleAttributeChange(index, 'name', e.target.value)}
                        />
                        <input
                            type="text"
                            placeholder="Attribute Value"
                            value={attr.value}
                            onChange={(e) => handleAttributeChange(index, 'value', e.target.value)}
                        />
                    </div>
                ))}
                <button onClick={handleAddRow}>Add Attribute</button>
                <div className="button-row">
                    <button onClick={onSave}>Save</button>
                </div>
            </div>

            <div className="list-container">
                <h2>Config List</h2>
                <input
                    type="text"
                    placeholder="Search config..."
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                />
                <div className='item-list-scroll'>
                    <table className="item-table">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Config Type</th>
                                <th>Task Type</th>
                                <th>Username</th>
                                <th>Hostname</th>

                            </tr>
                        </thead>
                        <tbody>
                            {filteredItems.map((item) => (
                                <tr key={item.id}>
                                    <td>
                                        <button onClick={() => navigate(`/config/${item.id}`)}>{item.id}</button>
                                    </td>
                                    <td>{item.configType}</td>
                                    <td>{item.taskType}</td>
                                    <td>{item.username}</td>
                                    <td>{item.machine.hostname}</td>

                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}

export default Config;

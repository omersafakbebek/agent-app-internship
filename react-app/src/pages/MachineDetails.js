
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from "react-router-dom";
import LoadingSpinner from "../components/LoadingSpinner";
import TaskGrid from '../components/TaskGrid';
import { useContext } from 'react';
import { AuthContext } from '../helpers/AuthContext';
import LogoutButton from '../components/LogoutButton';
import { SpinnerContext } from '../helpers/SpinnerContext';
import LogButton from '../components/LogButton';
import MachineButton from "../components/MachineButton";
import ConfigButton from "../components/ConfigButton";

function MachineDetailsPage() {
const { hostname } = useParams();
const [machine, setMachine] = useState();
const [tasks, setTasks] = useState([]);
const [isLoading, setIsLoading] = useState(true);
const {authState, setAuthState} = useContext(AuthContext);
const {isAuthSet, setIsAuthSet} = useContext(SpinnerContext);

let navigate = useNavigate();
useEffect(() => {
    if(!isAuthSet) {
      return;
    }
    if (!authState) {
      navigate("/login")
    }
    const fetchMachine = async () => {
          try {
            const token = localStorage.getItem("token");
            if (!token) {
              setAuthState(false);
              navigate("/login");
            }
            const requestOptions = {
              headers: { "token" : token }
            }
            const response = await fetch(`http://localhost:8080/machine/${hostname}`, requestOptions);
            if(!response.ok) {
              setAuthState(false);
              navigate("/login");
            }
            const data = await response.json();
            setMachine(data);
            setIsLoading(false);
          } catch (error) {
            setAuthState(false);
            navigate("/login");
            console.error('Error fetching data:', error);
          }
    }
    const fetchTasks = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) {
          return;
        }
        const requestOptions = {
          headers: { "token" : token }
        }
        const response = await fetch(`http://localhost:8080/task/byMachine/${hostname}`, requestOptions);
        if(!response.ok) {
          setAuthState(false);
          navigate("/login");
        }
        const data = await response.json();
        setTasks(data);
      } catch (error) {
        setAuthState(false);
        navigate("/login");
        console.error('Error fetching data:', error);
      }
    };
    fetchMachine();
    fetchTasks();
    const machineInterval = setInterval(fetchMachine, 1*1000);
    const interval = setInterval(fetchTasks, 1*1000);
    return () => {
        clearInterval(interval);
        clearInterval(machineInterval);
    }
  }, [isAuthSet]);
  if (isLoading || !isAuthSet) {
    return (
        <LoadingSpinner/>
    )
  }
  return (
    <div>
        <MachineButton/>
        <LogButton/>
        <ConfigButton/>
        <LogoutButton />
        <h3>Name: {machine.name} </h3>
        <h3>Hostname: {machine.hostname} </h3>
        <h3>Last Update: {machine.lastSeen} </h3>
        <div className="status-container    details">
            <h3>Status: {machine.agentStatus ? 'Active' : 'Inactive'}</h3>
            <div className={`status-indicator ${machine.agentStatus ? 'green' : 'red'}`} />
        </div>
        <TaskGrid tasks={tasks}/>
    </div>
  );
}

export default MachineDetailsPage;
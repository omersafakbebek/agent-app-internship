import { useState, useContext, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { AuthContext } from '../helpers/AuthContext';
import { SpinnerContext } from '../helpers/SpinnerContext';
import LoadingSpinner from "../components/LoadingSpinner";
import LogoutButton from '../components/LogoutButton';
import ParameterList from "../components/ParameterList";
import LogTable from "../components/LogTable";
import ConfigButton from "../components/ConfigButton";
import LogButton from '../components/LogButton';
import MachineButton from "../components/MachineButton";

function TaskDetailsPage() {
    const { id } = useParams();
    const [task, setTask] = useState();
    const [logs, setLogs] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const {authState, setAuthState} = useContext(AuthContext);
    const {isAuthSet, setIsAuthSet} = useContext(SpinnerContext);

    const statusColors = {
        RUNNING: "green",
        COMPLETED: "yellow",
        FAILED: "red"
    };

    let navigate = useNavigate();

    useEffect(() => {
        if(!isAuthSet) {
            return;
        }
        if (!authState) {
            navigate("/login")
        }
        const fetchTask = async () => {
            try {
                const token = localStorage.getItem("token");
                if (!token) {
                  setAuthState(false);
                  navigate("/login");
                }
                const requestOptions = {
                  headers: { "token" : token }
                }
                const response = await fetch(`http://localhost:8080/task/${id}`, requestOptions);
                if(!response.ok) {
                  setAuthState(false);
                  navigate("/login");
                }
                const data = await response.json();
                setTask(data);
                setIsLoading(false);
            } catch (error) {
              setAuthState(false);
                  navigate("/login");
                console.error('Error fetching data:', error);
            }
        };
        const fetchLogs = async () => {
            try {
                const token = localStorage.getItem("token");
                if (!token) {
                  return;
                }
                const requestOptions = {
                  headers: { "token" : token }
                }
                const response = await fetch(`http://localhost:8080/log/byTask/${id}`, requestOptions);
                if(!response.ok) {
                  setAuthState(false);
                  navigate("/login");
                }
                const data = await response.json();
                setLogs(data);
              } catch (error) {
                setAuthState(false);
                  navigate("/login");
                console.error('Error fetching data:', error);
              }
        };
        fetchTask();
        fetchLogs();
        const taskInterval = setInterval(fetchTask, 1*1000);
        const interval = setInterval(fetchLogs, 1*1000);
        return () => {
            clearInterval(interval);
            clearInterval(taskInterval);
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
            <h3>Task Id: {task.id} </h3>
            <h3>Task Type: {task.config.taskType}</h3>
            <h3>Hostname: {task.machine.hostname} </h3>
            <h3>Username: {task.username} </h3>
            <h3>Date: {task.date} </h3>
            <div className="task-status-container details">
                <h3>Status: {task.machine.agentStatus ? task.threadStatus : "UNKNOWN"}</h3>
                <div className={`task-status-indicator ${task.machine.agentStatus ? statusColors[task.threadStatus] : "white"}`} />
            </div>
            <ParameterList parameters={task.config.parameters}/>
            <LogTable logData={logs}></LogTable>
        </div>
      );
}
export default TaskDetailsPage;
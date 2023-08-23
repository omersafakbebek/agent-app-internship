import React, { useState, useEffect, useContext } from 'react';
import MachineGrid from '../components/MachineGrid';
import "./Machines.css";
import LoadingSpinner from "../components/LoadingSpinner";
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../helpers/AuthContext';
import LogoutButton from '../components/LogoutButton';
import { SpinnerContext } from '../helpers/SpinnerContext';
import LogButton from '../components/LogButton';
import MachineButton from "../components/MachineButton";
import ConfigButton from "../components/ConfigButton";

function Machines() {
    const [machines, setMachines] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const {authState, setAuthState} = useContext(AuthContext);
    const {isAuthSet, setIsAuthSet} = useContext(SpinnerContext);
    let navigate = useNavigate();
    useEffect(() => {
      if (!isAuthSet) {
        return;
      }
      if (!authState) {
        navigate("/login")
      }
      const fetchData = async () => {
        try {
          const token = localStorage.getItem("token");
          if (!token) {
            setAuthState(false);
            navigate("/login");
          }
          const requestOptions = {
            headers: { "token" : token }
          }
          const response = await fetch('http://localhost:8080/machine', requestOptions);
          if(!response.ok) {
            setAuthState(false);
            navigate("/login");
          }
          const data = await response.json();
          setIsLoading(false);
          setMachines(data);
        } catch (error) {
          setAuthState(false);
          navigate("/login");
          setIsLoading(false);
          console.error('Error fetching data:', error);
        }
      };
      fetchData();
      const interval = setInterval(fetchData, 1*1000);
      return () => clearInterval(interval);
    }, [isAuthSet]);
    if (isLoading || !isAuthSet) {
        return (
            <LoadingSpinner/>
        )
      }
    return (
      <div className="Machines">
        <h1>Machines</h1>
        <MachineButton/>
        <LogButton/>
        <ConfigButton/>
        <LogoutButton/>
        <MachineGrid machines={machines} />
      </div>
    );
}

export default Machines;

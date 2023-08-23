
import React, { useEffect, useState } from 'react';
import { useParams } from "react-router-dom";
import LoadingSpinner from "../components/LoadingSpinner";
import "./Tasks.css"
import { Link } from 'react-router-dom';

function TaskGrid({tasks}) {

const [filterOption, setFilterOption] = useState('All');

const filteredTasks = tasks.filter(task =>
     (filterOption === 'All' || (filterOption.toLowerCase() === task.threadStatus.toLowerCase()))
   );
const statusColors = {
    RUNNING: "green",
    COMPLETED: "yellow",
    FAILED: "red",
};
  return (
    <div className='task-item-grid-container'>
        <div className="task-search-and-filter">
          <div className="task-filter-options">
            <label>
              <input
                type="radio"
                name="filterOption"
                value="All"
                checked={filterOption === 'All'}
                onChange={() => setFilterOption('All')}
              />
              All
            </label>
            <label>
              <input
                type="radio"
                name="filterOption"
                value="Running"
                checked={filterOption === 'Running'}
                onChange={() => setFilterOption('Running')}
              />
              Running
            </label>
            <label>
              <input
                type="radio"
                name="filterOption"
                value="Completed"
                checked={filterOption === 'Completed'}
                onChange={() => setFilterOption('Completed')}
              />
              Completed
            </label>
            <label>
              <input
                type="radio"
                name="filterOption"
                value="Failed"
                checked={filterOption === 'Failed'}
                onChange={() => setFilterOption('Failed')}
              />
              Failed
            </label>
          </div>
        </div>
        <div className="task-item-grid">
          {filteredTasks.map(task => (
              <Link to={`/task/${task.id}`} key={task.id}>
                <button className="task-grid-item">    
                    <div className="task-item-details">
                    <div>Username: {task.username}</div>
                    <div>Task Type: {task.config.taskType}</div>
                    <div className="task-status-container">
                        <div>Status:  {task.machine.agentStatus ? task.threadStatus : "UNKNOWN"}</div>
                        <div className={`task-status-indicator ${task.machine.agentStatus ? statusColors[task.threadStatus] : "white"}`} />
                    </div>
                    </div>     
                </button>
              </Link>
          ))}
        </div>
      </div>
  );
}

export default TaskGrid;
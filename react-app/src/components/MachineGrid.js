import React, { useState, useEffect } from 'react';
import './GridView.css'; // Import your CSS file for styling
import { Link } from 'react-router-dom';

function MachineGrid({ machines }) {
    const [searchTerm, setSearchTerm] = useState('');
    const [filterOption, setFilterOption] = useState('All');
  
    const filteredMachines = machines.filter(machine =>
     (machine.name.toLowerCase().includes(searchTerm.toLowerCase()) || machine.hostname.toLowerCase().includes(searchTerm.toLowerCase())) &&
      (filterOption === 'All' || (filterOption === 'Active' && machine.agentStatus) || (filterOption === 'Inactive' && !machine.agentStatus))
    );
    return (
      <div className='item-grid-container'>
        <div className="search-and-filter">
          <div className="search-bar">
            <input
              type="text"
              placeholder="Search machines"
              value={searchTerm}
              onChange={e => setSearchTerm(e.target.value)}
            />
          </div>
          <div className="filter-options">
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
                value="Active"
                checked={filterOption === 'Active'}
                onChange={() => setFilterOption('Active')}
              />
              Active
            </label>
            <label>
              <input
                type="radio"
                name="filterOption"
                value="Inactive"
                checked={filterOption === 'Inactive'}
                onChange={() => setFilterOption('Inactive')}
              />
              Inactive
            </label>
          </div>
        </div>
        <div className="item-grid">
          {filteredMachines.map(machine => (
            <Link to={`/machine/${machine.hostname}`} key={machine.id}>
                <button className="grid-item">    
                    <div className="item-details">
                    <div>Name: {machine.name}</div>
                    <div>Hostname: {machine.hostname}</div>
                    <div className="status-container">
                        <div>Status: {machine.agentStatus ? 'Active' : 'Inactive'}</div>
                        <div className={`status-indicator ${machine.agentStatus ? 'green' : 'red'}`} />
                    </div>
                    </div>     
                </button>
            </Link>
          ))}
        </div>
      </div>
    );
  }

  export default MachineGrid;
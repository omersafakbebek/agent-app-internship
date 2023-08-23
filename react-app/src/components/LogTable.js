import React from 'react';
import './LogTable.css'; // Import your CSS file

const LogTable = ({logData}) => {
  return (
    <div className="table-container">
      <div className="table-scroll">
      <table className="log-table">
        <thead>
          <tr>
            <th>Log Type</th>
            <th>Task ID</th>
            <th>Message</th>
            <th>Date</th>
          </tr>
        </thead>
        <tbody>
          {logData.map((log) => (
            <tr key={log.id}>
              <div className={`log-table log-type ${log.logType.toLowerCase()}`}>
                <td>{log.logType}</td>
              </div>
              <td>{log.task.id}</td>
              <td>{log.message}</td>
              <td>{log.date}</td>
            </tr>
          ))}
        </tbody>
      </table>
      </div>
    </div>
  );
};

export default LogTable;

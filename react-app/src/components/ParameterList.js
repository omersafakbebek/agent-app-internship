import React, { useState } from "react";
import "./ParameterList.css"
function ParameterList({parameters}) {

  return (
    <div className="parameter-list">
      <div className="parameters-title">
        Parameters 
      </div>
      <table className="parameter-table">
        <thead>
          <tr>
            <th>Attribute</th>
            <th>Value</th>
          </tr>
        </thead>
        <tbody>
          {parameters.map((parameter) => (
            <tr key={parameter.id}>
              <td>{parameter.name}</td>
              <td>{parameter.value}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ParameterList;

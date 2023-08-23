import { BrowserRouter as Router, Routes, Route, useNavigate }
    from 'react-router-dom';
import Machines from './pages/Machines';
import MachineDetailsPage from './pages/MachineDetails';
import { useEffect, useState } from 'react';
import { AuthContext } from './helpers/AuthContext';
import Login from './pages/Login';
import { SpinnerContext } from './helpers/SpinnerContext';
import TaskDetailsPage from './pages/TaskDetails';
import Logs from './pages/Logs';
import Config from './pages/Config';
import ConfigDetails from './pages/ConfigDetails';

function App() {
  const [authState, setAuthState] = useState(false);
  const [isAuthSet, setIsAuthSet] = useState(false);
  useEffect(() => {
    if (localStorage.getItem("token")) {
      setAuthState(true);
    } else {
      setAuthState(false);
    }
    setIsAuthSet(true);
  })
  return (
    <SpinnerContext.Provider value={{isAuthSet, setIsAuthSet}}>
      <AuthContext.Provider value={{authState, setAuthState}}>
        <Router>
          <Routes>
            <Route path="/machine/:hostname" element={<MachineDetailsPage/>} />
            <Route path="/task/:id" element={<TaskDetailsPage/>} />
            <Route path="/login" element={<Login/>}/>
            <Route path='/' element={<Machines/>}/>
            <Route path='/logs' element={<Logs/>}/>
            <Route path='/config' element={<Config/>}/>
            <Route path='/config/:id' element={<ConfigDetails/>}/>
          </Routes>
        </Router>
      </AuthContext.Provider>
    </SpinnerContext.Provider>
  );
}

export default App;

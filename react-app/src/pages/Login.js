// LoginPage.js

import React, { useContext, useState, useEffect } from 'react';
import { AuthContext } from '../helpers/AuthContext';
import { useNavigate } from 'react-router-dom';
import './Login.css';
import logo from "../images/tai.png";
import cover from "../images/cover.png";
function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const { authState, setAuthState } = useContext(AuthContext);
    let navigate = useNavigate();
    useEffect(() => {
        console.log(authState);
        if (localStorage.getItem("token") && authState) {
            navigate("/");
        }
    }, [])
    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const body = {
                username: username,
                password: password
            };
            const response = await fetch("http://localhost:8080/admin/login", { body: JSON.stringify(body), method: "POST", headers: { "Content-Type": "application/json" } });
            console.log(response)
            if (response.status == 200) {
                const data = await response.json();
                localStorage.setItem("token", data.accessToken);
                setAuthState(true);
                navigate("/")
            }
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };


    return (
        <div className="login-container">
            <div className="left-side">
                <img src={cover} alt="Cover" className="cover-image" />
            </div>
            <div className="right-side">

                <span class="login100-form-title p-b-43">
                    <img src={logo} />
                </span>

                <form id="manuel-login" style={{ display: "block" }}>
                    <div class="wrap-input100 validate-input" data-validate="Valid email is required: ex@abc.xyz">
                        <input class="input100 has-val" type="text" id="username" name="username" required="required" onChange={e => setUsername(e.target.value)}
                            autofocus autocapitalize="off" />
                        <span class="focus-input100"></span>
                        <span class="label-input100" id="l_user_code">{"Kullanıcı Adı"}</span>
                    </div>

                    <div class="wrap-input100 validate-input" data-validate="Password is required">
                        <input class="input100 has-val" type="password" name="password" id="password" onChange={e => setPassword(e.target.value)}
                            required="required" autocomplete="off" maxlength="4096"
                            onkeyup="checkCapsWarning(event)" onfocus="checkCapsWarning(event)"
                            onblur="removeCapsWarning()" />

                        <span class="focus-input100"></span>
                        <span class="label-input100" id="l_password">Parola</span>
                    </div>

                    <div class="flex-sb-m w-full p-t-3 p-b-32">
                        <div class="contact100-form-checkbox">

                        </div>

                        <div>

                        </div>
                    </div>

                    <div class="container-login100-form-btn">
                        <button type="submit" class="login100-form-btn" onClick={handleLogin} id="l_login"> 
                            <span> GİRİŞ YAP </span>
                        </button>
                    </div>
                    <div class="text-center p-t-15 p-b-20">
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Login;

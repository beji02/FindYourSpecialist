import React from 'react';

function Login() {
    return (
        <div className="login-container">
            <h1>Login</h1>
            <form>
                <label htmlFor="email">Email</label>
                <input type="email" id="email" name="email" required />
                <label htmlFor="password">Password</label>
                <input type="password" id="password" name="password" required />
                <button type="submit">Login</button>
            </form>
        </div>
    );
}

export default Login;

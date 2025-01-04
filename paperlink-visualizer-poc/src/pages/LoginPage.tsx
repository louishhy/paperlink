import * as process from "node:process";
import {useState} from "react";
import * as React from "react";
import {useNavigate} from "react-router-dom";

const API_BASE_URL = process.env.API_BASE_URL;
const LOGIN_API_PATH = process.env.LOGIN_API_PATH;

const LoginPage = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
        // Default: Refresh and form added to the URL. Prevent it.
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            const response = await fetch(`${API_BASE_URL}/${LOGIN_API_PATH}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username: username, password: password }),
                credentials: 'include'
            });

            if (response.ok) {
                navigate("/dashboard");
            } else {
                setError("Login failed.");
            }
        } catch (error: unknown) {
            if (error instanceof Error) {
                setError(error.message);
            } else {
                setError("An unexpected error occurred.");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex items-center justify-center min-h-screen">

        </div>
    )
};

export default LoginPage;
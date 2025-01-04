import {useState} from "react";
import * as React from "react";
import {useNavigate} from "react-router-dom";
import {paperlinkLogin} from "@/services/authService.ts";
import LoginAlertPanel from "@/components/LoginPage/LoginAlertPanel.tsx";
import LoginFormInput from "@/components/LoginPage/LoginFormInput.tsx";
import LoginHeader from "@/components/LoginPage/LoginHeader.tsx";
import {Card} from "@/components/ui/card.tsx";


const LoginPage = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const handleLogin = async (e: React.FormEvent) => {
        // Default: Refresh and form added to the URL. Prevent it.
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            await paperlinkLogin(username, password);
            navigate('/dashboard');
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
            <Card className="w-full max-w-md">
                <LoginHeader />
                <LoginFormInput
                    handleLogin={handleLogin}
                    username={username}
                    setUsername={setUsername}
                    password={password}
                    setPassword={setPassword}
                    loading={loading}
                />
                { error &&
                    <LoginAlertPanel errorMessage={error} />}
            </Card>
        </div>
    )
};

export default LoginPage;
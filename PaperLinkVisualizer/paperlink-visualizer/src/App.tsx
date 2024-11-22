import React, { useState } from 'react';
import { Card } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Alert, AlertDescription } from '@/components/ui/alert';

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const handleLogin = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);

        try {
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
                credentials: 'include' // Important for cookie handling
            });

            if (!response.ok) {
                throw new Error('Login failed');
            }

            const data = await response.json();
            // Handle successful login - the cookie will be automatically set by the browser
            console.log('Login successful', data);

        } catch (err) {
            setError('Login failed. Please check your credentials and try again.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-50">
            <Card className="w-96 p-6">
                <div className="flex flex-col items-center space-y-6">
                    <div className="w-80 h-auto flex items-center justify-center rounded overflow-hidden">
                        <img
                            src="/images/paperlink.png"
                            alt="Paperlink Logo"
                            className="w-full h-full object-cover" // This ensures proper image scaling
                        />
                    </div>

                    <form onSubmit={handleLogin} className="w-full space-y-4">
                    <div className="space-y-2">
                            <label htmlFor="username" className="block text-sm font-medium">
                                Username
                            </label>
                            <Input
                                id="username"
                                type="text"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                required
                                className="w-full"
                            />
                        </div>

                        <div className="space-y-2">
                            <label htmlFor="password" className="block text-sm font-medium">
                                Password
                            </label>
                            <Input
                                id="password"
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                                className="w-full"
                            />
                        </div>

                        {error && (
                            <Alert variant="destructive">
                                <AlertDescription>{error}</AlertDescription>
                            </Alert>
                        )}

                        <Button
                            type="submit"
                            className="w-full"
                            disabled={loading}
                        >
                            {loading ? 'Logging in...' : 'Login'}
                        </Button>
                    </form>
                </div>
            </Card>
        </div>
    );
};

export default LoginPage;
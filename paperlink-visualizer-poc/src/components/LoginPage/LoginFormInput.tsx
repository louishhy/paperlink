import {CardContent, CardFooter} from "@/components/ui/card.tsx";
import {Label} from "@/components/ui/label.tsx";
import {Input} from "@/components/ui/input.tsx";
import React from "react";
import {Button} from "@/components/ui/button.tsx";

interface LoginFormInputProps {
    handleLogin: (value: React.FormEvent) => void;
    username: string;
    setUsername: (value: string) => void;
    password: string;
    setPassword: (value: string) => void;
    loading: boolean;
}

const LoginFormInput: React.FC<LoginFormInputProps> = (props) => {
    return (
        <form onSubmit={props.handleLogin}>
            <CardContent className="space-y-4">
                <div className="space-y-2">
                    <Label htmlFor="username" >Username</Label>
                    <Input
                        id="username"
                        type="text"
                        placeholder="Enter your username"
                        value={props.username}
                        onChange={(e) => props.setUsername(e.target.value)}
                        required
                        />
                </div>
                <div className="space-y-2">
                    <Label htmlFor="password">Password</Label>
                    <Input
                        id="password"
                        type="password"
                        placeholder="Enter your password"
                        value={props.password}
                        onChange={(e) => props.setPassword(e.target.value)}
                        required
                    />
                </div>
            </CardContent>
            <CardFooter>
                <Button type="submit" className="w-full" disabled={props.loading}>
                    { props.loading ? "Logging in..." : "Login"}
                </Button>
            </CardFooter>
        </form>
    )
}

export default LoginFormInput;
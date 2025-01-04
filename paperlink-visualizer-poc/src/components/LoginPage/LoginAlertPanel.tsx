import React from "react";
import {Alert, AlertDescription} from "@/components/ui/alert.tsx";

interface LoginAlertPanelProps {
    errorMessage: string;
}

const LoginAlertPanel: React.FC<LoginAlertPanelProps> = (props) => {
    return (
        <Alert variant="destructive" className="mt-4">
            <AlertDescription>{props.errorMessage}</AlertDescription>
        </Alert>
    )
}

export default LoginAlertPanel;
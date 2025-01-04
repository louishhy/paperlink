import {CardHeader, CardTitle} from "@/components/ui/card.tsx";
import React from "react";

const LoginHeader: React.FC = () => {
    return (
        <CardHeader className="space-y-4 text-center">
            <div className="flex justify-center">
                <img
                    src="/paperlink.png"
                    alt="Paperlink Logo"
                    className="max-h-20 w-auto object-contain"
                />
            </div>
            <CardTitle>Login</CardTitle>
        </CardHeader>
    )
}

export default LoginHeader;
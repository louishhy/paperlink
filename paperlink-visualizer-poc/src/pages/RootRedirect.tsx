import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {paperlinkCheckLoggedIn} from "@/services/authService.ts";

const RootRedirect = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const checkAuthStatus = async () => {
            const loggedIn = await paperlinkCheckLoggedIn();
            if (loggedIn) {
                navigate("/dashboard", { replace: true });
            } else {
                navigate("/login", { replace: true });
            }
            setLoading(false);
        };
        checkAuthStatus().catch(error => {
            console.error(`Auth check failed: ${error}`);
            setLoading(false);
        });
    }, [navigate]);

    return null;
}

export default RootRedirect;
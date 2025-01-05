const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const LOGIN_API_PATH = import.meta.env.VITE_LOGIN_API_PATH;
const CHECK_LOGIN_API_PATH = import.meta.env.VITE_CHECK_LOGIN_API_PATH;

export const paperlinkLogin = async (username: string, password: string) => {
    const response = await fetch(`${API_BASE_URL}/${LOGIN_API_PATH}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username: username, password: password }),
        credentials: 'include'
    });

    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }

    return response.json();
}

export const paperlinkCheckLoggedIn = async () => {
    const response = await fetch(`${API_BASE_URL}/${CHECK_LOGIN_API_PATH}`, {
        method: 'GET',
        credentials: 'include'
    });

    return response.ok;
}
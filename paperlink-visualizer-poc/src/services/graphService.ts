const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const GRAPH_API_PATH = import.meta.env.VITE_GRAPH_API_PATH;

interface GraphResponse {

}

export const fetchGraphs = async () => {
    const response = await fetch(`${API_BASE_URL}/${GRAPH_API_PATH}`, {
        method: 'GET',
        credentials: 'include',
    });
    if (!response.ok) {
        throw new Error("Failed to fetch graphs.");
    }
    const data = await response.json();
    return data;
}

export const fetchGraphDetails = async (graphId: string) => {
    const response = await fetch(`${API_BASE_URL}/${GRAPH_API_PATH}/${graphId}`, {
        method: 'GET',
        credentials: 'include',
    });
    if (!response.ok) {
        throw new Error("Failed to fetch graph details.");
    }
    const data = await response.json();
    return data;
}

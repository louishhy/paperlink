import LoginPage from "@/pages/LoginPage.tsx";
import {Routes, Route} from "react-router-dom";
import RootRedirect from "@/pages/RootRedirect.tsx";
import Dashboard from "@/pages/Dashboard.tsx";

function App() {
  return (
    <Routes>
        <Route path="/" element={<RootRedirect />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/dashboard" element={<Dashboard />} />
    </Routes>
  )
}

export default App;

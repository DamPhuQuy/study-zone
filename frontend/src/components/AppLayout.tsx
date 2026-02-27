import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";

export default function AppLayout() {
  return (
    <div className="min-h-screen bg-gray-950 text-white">
      <Navbar />
      <main className="pt-16">
        <Outlet />
      </main>
    </div>
  );
}

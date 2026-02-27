import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { BookOpen, Music, ShoppingBag, User, LogOut } from 'lucide-react';
import clsx from 'clsx';

const navLinks = [
  { to: '/study', label: 'Study', icon: BookOpen },
  { to: '/shop', label: 'Shop', icon: ShoppingBag },
  { to: '/profile', label: 'Profile', icon: User },
];

export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const { pathname } = useLocation();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="fixed top-0 inset-x-0 z-50 flex items-center justify-between px-6 py-3 bg-black/40 backdrop-blur-md border-b border-white/10">
      {/* Logo */}
      <Link to="/study" className="flex items-center gap-2 text-white font-bold text-lg tracking-tight">
        <Music className="size-5 text-violet-400" />
        <span>StudyZone</span>
      </Link>

      {/* Nav links */}
      <div className="flex items-center gap-1">
        {navLinks.map(({ to, label, icon: Icon }) => (
          <Link
            key={to}
            to={to}
            className={clsx(
              'flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-sm font-medium transition-colors',
              pathname.startsWith(to)
                ? 'bg-violet-600 text-white'
                : 'text-white/70 hover:text-white hover:bg-white/10'
            )}
          >
            <Icon className="size-4" />
            {label}
          </Link>
        ))}
      </div>

      {/* User + logout */}
      <div className="flex items-center gap-3">
        {user?.username && (
          <span className="text-sm text-white/70 hidden sm:block">
            {user.username}
          </span>
        )}
        <button
          onClick={handleLogout}
          className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-sm text-white/70 hover:text-white hover:bg-white/10 transition-colors"
        >
          <LogOut className="size-4" />
          Logout
        </button>
      </div>
    </nav>
  );
}

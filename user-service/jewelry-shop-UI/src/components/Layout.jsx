import { Link, Outlet, useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { api } from '../api/client';
import './Layout.css';

const navItems = [
  { to: '/home', label: 'Home' },
  { to: '/products', label: 'Products' },
  { to: '/orders', label: 'Orders' },
  { to: '/reviews', label: 'Reviews' },
];

export default function Layout() {
  const location = useLocation();
  const [user, setUser] = useState({ name: 'Guest', anonymous: true });

  useEffect(() => {
    api.me().then(setUser).catch(() => {});
  }, [location.pathname]);

  return (
    <div className="layout">
      <header className="site-header">
        <div className="header-inner">
          <Link to="/home" className="brand">
            <span className="brand-accent">Yumine</span> Jewelry
          </Link>
          <nav className="main-nav">
            {navItems.map((item) => (
              <Link
                key={item.to}
                to={item.to}
                className={location.pathname === item.to ? 'nav-link active' : 'nav-link'}
              >
                {item.label}
              </Link>
            ))}
          </nav>
          <div className="user-badge">
            <span className="user-dot" />
            {user.anonymous ? 'Guest' : user.name}
          </div>
        </div>
      </header>
      <main className="page">
        <Outlet />
      </main>
    </div>
  );
}

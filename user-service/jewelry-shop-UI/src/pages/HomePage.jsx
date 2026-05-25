import { Link } from 'react-router-dom';
import './HomePage.css';

const tiles = [
  { to: '/products', title: 'Catalog', desc: 'Browse our jewelry collection with photo-ready product cards.' },
  { to: '/orders', title: 'My Orders', desc: 'Check your order history and delivery status.' },
  { to: '/reviews', title: 'Reviews & Discussions', desc: 'Read reviews and join threaded discussions.' },
];

export default function HomePage() {
  return (
    <div className="container">
      <div className="welcome-card card">
        <h1>
          Welcome to <span>Yumine</span>
        </h1>
        <p>Your boutique jewelry experience — clean, modern, and easy to navigate.</p>
      </div>
      <div className="home-grid">
        {tiles.map((tile) => (
          <div key={tile.to} className="home-tile card">
            <h3>{tile.title}</h3>
            <p>{tile.desc}</p>
            <Link to={tile.to} className="btn">
              Open
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
}

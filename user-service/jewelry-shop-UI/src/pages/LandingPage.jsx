import { Link } from 'react-router-dom';
import './LandingPage.css';

export default function LandingPage() {
  return (
    <div className="landing">
      <div className="landing-card">
        <p className="landing-tag">Yumine Jewelry Store</p>
        <h1>Where quality meets style</h1>
        <p className="landing-sub">
          Browse our collection, track orders, and join discussions on customer reviews.
        </p>
        <Link to="/home" className="btn landing-btn">
          Start browsing
        </Link>
      </div>
    </div>
  );
}

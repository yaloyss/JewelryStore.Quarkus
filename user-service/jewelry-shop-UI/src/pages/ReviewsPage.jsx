import { useCallback, useEffect, useState } from 'react';
import { api } from '../api/client';
import DiscussionPanel from '../components/DiscussionPanel';
import './ReviewsPage.css';

const emptyReview = {
  productId: '',
  rating: 5,
  title: '',
  body: '',
};

function Stars({ rating }) {
  const filled = Math.min(5, Math.max(0, rating || 0));
  return (
    <span className="stars" aria-label={`${filled} out of 5 stars`}>
      {'★'.repeat(filled)}
      {'☆'.repeat(5 - filled)}
    </span>
  );
}

export default function ReviewsPage() {
  const [reviews, setReviews] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [expanded, setExpanded] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState(emptyReview);

  const load = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await api.reviews.list();
      setReviews(data);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    load();
  }, [load]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: name === 'rating' || name === 'productId' ? Number(value) : value }));
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    try {
      await api.reviews.create({
        productId: form.productId,
        rating: form.rating,
        title: form.title,
        body: form.body,
      });
      setShowForm(false);
      setForm(emptyReview);
      load();
    } catch (err) {
      setError(err.message);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this review?')) return;
    try {
      await api.reviews.remove(id);
      if (expanded === id) setExpanded(null);
      load();
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="container reviews-page">
      <div className="page-header">
        <h2 className="section-title">
          Reviews & <span>Discussions</span>
        </h2>
        <button className="btn" onClick={() => setShowForm(!showForm)}>
          {showForm ? 'Close' : '+ Write review'}
        </button>
      </div>

      {error && <div className="error-banner">{error}</div>}

      {showForm && (
        <form className="form-panel" onSubmit={handleCreate}>
          <div className="form-grid">
            <div className="form-group">
              <label>Product ID</label>
              <input name="productId" type="number" value={form.productId} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>Rating (1–5)</label>
              <input name="rating" type="number" min="1" max="5" value={form.rating} onChange={handleChange} required />
            </div>
            <div className="form-group" style={{ gridColumn: '1 / -1' }}>
              <label>Title</label>
              <input name="title" value={form.title} onChange={handleChange} required />
            </div>
            <div className="form-group" style={{ gridColumn: '1 / -1' }}>
              <label>Review</label>
              <textarea name="body" rows={4} value={form.body} onChange={handleChange} required />
            </div>
          </div>
          <div className="form-actions">
            <button type="submit" className="btn">
              Publish review
            </button>
          </div>
        </form>
      )}

      {loading && <p className="empty-state">Loading reviews…</p>}

      <div className="reviews-list">
        {reviews.map((review) => (
          <article key={review.reviewId} className="review-card card">
            <div className="review-top">
              <div>
                <h3>{review.title}</h3>
                <Stars rating={review.rating} />
              </div>
              <button className="btn btn-small btn-danger" onClick={() => handleDelete(review.reviewId)}>
                Delete
              </button>
            </div>
            <p className="review-body">{review.body}</p>
            <p className="review-meta">
              Product #{review.productId}
              {review.createdAt && ` · ${new Date(review.createdAt).toLocaleDateString()}`}
            </p>
            <button
              className="btn btn-small btn-outline discussion-toggle"
              onClick={() => setExpanded(expanded === review.reviewId ? null : review.reviewId)}
            >
              {expanded === review.reviewId ? 'Hide discussion' : 'View discussion'}
            </button>
            {expanded === review.reviewId && <DiscussionPanel reviewId={review.reviewId} />}
          </article>
        ))}
      </div>

      {!loading && !reviews.length && <p className="empty-state">No reviews yet.</p>}
    </div>
  );
}

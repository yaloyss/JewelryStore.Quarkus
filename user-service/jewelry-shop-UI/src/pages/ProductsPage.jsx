import { useCallback, useEffect, useState } from 'react';
import { api } from '../api/client';
import PhotoPlaceholder from '../components/PhotoPlaceholder';
import { resolveProductImage } from '../utils/productImage';
import './ProductsPage.css';

const emptyProduct = {
  name: '',
  price: '',
  weight: '',
  size: '',
  manufacturer: '',
  metalId: 1,
  categoryId: 1,
};

export default function ProductsPage() {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [showCreate, setShowCreate] = useState(false);
  const [editing, setEditing] = useState(null);
  const [form, setForm] = useState(emptyProduct);

  const load = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await api.products.list();
      setProducts(data);
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
    setForm((f) => ({ ...f, [e.target.name]: e.target.value }));
  };

  const toPayload = () => ({
    name: form.name,
    price: Number(form.price),
    weight: Number(form.weight),
    size: Number(form.size),
    manufacturer: form.manufacturer,
    metalId: Number(form.metalId),
    categoryId: Number(form.categoryId),
  });

  const handleCreate = async (e) => {
    e.preventDefault();
    try {
      await api.products.create(toPayload());
      setShowCreate(false);
      setForm(emptyProduct);
      load();
    } catch (err) {
      setError(err.message);
    }
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await api.products.update(editing.productId, {
        ...toPayload(),
        productId: editing.productId,
      });
      setEditing(null);
      setForm(emptyProduct);
      load();
    } catch (err) {
      setError(err.message);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this product?')) return;
    try {
      await api.products.remove(id);
      load();
    } catch (err) {
      setError(err.message);
    }
  };

  const startEdit = (p) => {
    setEditing(p);
    setForm({
      name: p.name || '',
      price: p.price ?? '',
      weight: p.weight ?? '',
      size: p.size ?? '',
      manufacturer: p.manufacturer || '',
      metalId: p.metalId ?? 1,
      categoryId: p.categoryId ?? 1,
    });
    setShowCreate(false);
  };

  const ProductForm = ({ onSubmit, submitLabel }) => (
    <form onSubmit={onSubmit} className="form-panel">
      <div className="form-grid">
        <div className="form-group">
          <label>Name</label>
          <input name="name" value={form.name} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Price ($)</label>
          <input name="price" type="number" step="0.01" value={form.price} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Weight (g)</label>
          <input name="weight" type="number" step="0.1" value={form.weight} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Size (mm)</label>
          <input name="size" type="number" step="0.1" value={form.size} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Manufacturer</label>
          <input name="manufacturer" value={form.manufacturer} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Metal ID</label>
          <input name="metalId" type="number" value={form.metalId} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Category ID</label>
          <input name="categoryId" type="number" value={form.categoryId} onChange={handleChange} required />
        </div>
      </div>
      <div className="form-actions">
        <button type="submit" className="btn">
          {submitLabel}
        </button>
        <button
          type="button"
          className="btn btn-secondary"
          onClick={() => {
            setShowCreate(false);
            setEditing(null);
            setForm(emptyProduct);
          }}
        >
          Cancel
        </button>
      </div>
    </form>
  );

  return (
    <div className="container products-page">
      <div className="page-header">
        <h2 className="section-title">
          Our <span>Collection</span>
        </h2>
        <button className="btn" onClick={() => { setShowCreate(!showCreate); setEditing(null); }}>
          {showCreate ? 'Close form' : '+ Add product'}
        </button>
      </div>

      {error && <div className="error-banner">{error}</div>}
      {showCreate && <ProductForm onSubmit={handleCreate} submitLabel="Create product" />}
      {editing && <ProductForm onSubmit={handleUpdate} submitLabel="Save changes" />}

      {loading ? (
        <p className="empty-state">Loading products…</p>
      ) : (
        <div className="products-grid">
          {products.map((p) => (
            <article key={p.productId} className="product-card card">
              <PhotoPlaceholder imageUrl={resolveProductImage(p)} />
              <div className="product-body">
                <h3>{p.name}</h3>
                <p className="product-price">${p.price}</p>
                <ul className="product-meta">
                  <li>{p.manufacturer}</li>
                  <li>{p.weight}g · {p.size}mm</li>
                </ul>
                <div className="product-actions">
                  <button className="btn btn-small btn-outline" onClick={() => startEdit(p)}>
                    Edit
                  </button>
                  <button className="btn btn-small btn-danger" onClick={() => handleDelete(p.productId)}>
                    Delete
                  </button>
                </div>
              </div>
            </article>
          ))}
          {!products.length && <p className="empty-state">No products yet. Add your first piece.</p>}
        </div>
      )}
    </div>
  );
}

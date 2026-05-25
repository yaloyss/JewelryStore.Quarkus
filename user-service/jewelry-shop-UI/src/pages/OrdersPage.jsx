import { useEffect, useState } from 'react';
import { api } from '../api/client';
import './OrdersPage.css';

function statusClass(status) {
  const s = (status || '').toLowerCase();
  if (s.includes('deliver')) return 'status-delivered';
  if (s.includes('ship')) return 'status-shipped';
  if (s.includes('cancel')) return 'status-cancelled';
  return 'status-pending';
}

export default function OrdersPage() {
  const [orders, setOrders] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.orders
      .list()
      .then(setOrders)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="container orders-page">
      <h2 className="section-title">
        My <span>Orders</span>
      </h2>
      {error && <div className="error-banner">{error}</div>}
      {loading && <p className="empty-state">Loading orders…</p>}
      {!loading &&
        orders.map((order) => (
          <article key={order.orderId} className="order-card card">
            <div className="order-header">
              <span className="order-id">Order #{order.orderId}</span>
              <span className={`order-status ${statusClass(order.status)}`}>{order.status}</span>
            </div>
            <div className="order-details">
              <p>
                <strong>Customer:</strong> {order.customerId}
              </p>
              <p>
                <strong>Date:</strong> {order.orderDate ? new Date(order.orderDate).toLocaleString() : '—'}
              </p>
              {order.orderItems?.length > 0 && (
                <ul className="order-items">
                  {order.orderItems.map((item, i) => (
                    <li key={i}>
                      Product {item.productId} × {item.quantity}
                      {item.price != null && ` — $${item.price}`}
                    </li>
                  ))}
                </ul>
              )}
            </div>
          </article>
        ))}
      {!loading && !orders.length && <p className="empty-state">No orders found.</p>}
    </div>
  );
}

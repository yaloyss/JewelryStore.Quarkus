const API_BASE = import.meta.env.VITE_API_BASE_URL || '';

async function request(path, options = {}) {
  const res = await fetch(`${API_BASE}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  });

  if (res.status === 204) {
    return null;
  }

  const text = await res.text();
  const data = text ? JSON.parse(text) : null;

  if (!res.ok) {
    const err = new Error(data?.error || res.statusText || 'Request failed');
    err.status = res.status;
    err.data = data;
    throw err;
  }

  return data;
}

export const api = {
  me: () => request('/api/me'),
  products: {
    list: () => request('/api/products'),
    get: (id) => request(`/api/products/${id}`),
    create: (body) => request('/api/products', { method: 'POST', body: JSON.stringify(body) }),
    update: (id, body) => request(`/api/products/${id}`, { method: 'PUT', body: JSON.stringify(body) }),
    remove: (id) => request(`/api/products/${id}`, { method: 'DELETE' }),
  },
  orders: {
    list: () => request('/api/orders'),
    get: (id) => request(`/api/orders/${id}`),
  },
  reviews: {
    list: () => request('/api/reviews'),
    get: (id) => request(`/api/reviews/${id}`),
    byProduct: (productId) => request(`/api/reviews/product/${productId}`),
    create: (body) => request('/api/reviews', { method: 'POST', body: JSON.stringify(body) }),
    update: (id, body) => request(`/api/reviews/${id}`, { method: 'PUT', body: JSON.stringify(body) }),
    remove: (id) => request(`/api/reviews/${id}`, { method: 'DELETE' }),
  },
  discussions: {
    list: () => request('/api/discussions'),
    byReview: (reviewId) => request(`/api/discussions/review/${reviewId}`),
    create: (body) => request('/api/discussions', { method: 'POST', body: JSON.stringify(body) }),
    addMessage: (discussionId, body) =>
      request(`/api/discussions/${discussionId}/messages`, {
        method: 'POST',
        body: JSON.stringify(body),
      }),
    remove: (id) => request(`/api/discussions/${id}`, { method: 'DELETE' }),
  },
};

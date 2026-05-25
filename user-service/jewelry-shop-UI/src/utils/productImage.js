/**
 * Static product photos: place files in jewelry-shop-UI/public/images/
 * named by product ID, e.g. 1.jpg, 2.jpg
 */
export function productImageUrl(productId) {
  if (productId == null) return null;
  return `/images/${productId}.jpg`;
}

/** Prefer API imageUrl when present; otherwise use static file convention. */
export function resolveProductImage(product) {
  if (product?.imageUrl) return product.imageUrl;
  if (product?.productId != null) return productImageUrl(product.productId);
  return null;
}

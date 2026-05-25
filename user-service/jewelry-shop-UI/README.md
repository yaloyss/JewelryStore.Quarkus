# jewelry-shop-UI

React frontend for the Yumine Jewelry Store (BFF: `user-service` on port 8080).

## Prerequisites

- Node.js 20+
- Running backend services: `user-service` (8080), `order-service` (8081), `product-service` (8082), `reviews-service` (8083)

## Development

```bash
cd user-service/jewelry-shop-UI
npm install
npm run dev
```

Open http://localhost:5173 — API calls are proxied to http://localhost:8080.

In another terminal, start Quarkus:

```bash
cd user-service
./mvnw quarkus:dev
```

## Production build (embedded in user-service JAR)

```bash
npm run build
```

Output is written to `user-service/src/main/resources/META-INF/resources/`. Then build/run `user-service` and open http://localhost:8080.

## Product photos (catalog)

Add image files here:

```
user-service/jewelry-shop-UI/public/images/
```

**Naming:** use the product’s database ID as the filename:

| Product ID in DB | File to add |
|------------------|-------------|
| 1 | `1.jpg` |
| 2 | `2.jpg` |
| 5 | `5.png` |

Supported formats: **`.jpg`** (recommended), or `.jpeg` / `.png` / `.webp` if you name the file with that extension (e.g. `3.webp` → rename logic uses `{productId}.jpg` by default — use `.jpg` for simplest setup).

After adding images, restart `npm run dev` (or run `npm run build` for production). They are served at:

- Dev: `http://localhost:5173/images/1.jpg`
- Prod (embedded UI): `http://localhost:8080/images/1.jpg`

If a file is missing for that ID, the pink placeholder is shown.

Optional later: store `imageUrl` on the product in `product-service` and the API will override these static files.

## Design

Pink, black, and white palette with light hover animations. Product cards include a photo placeholder when no image file exists.

import './PhotoPlaceholder.css';

export default function PhotoPlaceholder({ label = 'Add product photo', imageUrl }) {
  if (imageUrl) {
    return (
      <div className="photo-placeholder has-image">
        <img src={imageUrl} alt="" />
      </div>
    );
  }

  return (
    <div className="photo-placeholder">
      <div className="photo-icon">◇</div>
      <span>{label}</span>
    </div>
  );
}

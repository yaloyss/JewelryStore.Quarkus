import { useEffect, useState } from 'react';
import { api } from '../api/client';
import './DiscussionPanel.css';

export default function DiscussionPanel({ reviewId }) {
  const [discussion, setDiscussion] = useState(null);
  const [loading, setLoading] = useState(true);
  const [messageText, setMessageText] = useState('');
  const [error, setError] = useState(null);

  const load = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await api.discussions.byReview(reviewId);
      setDiscussion(data);
    } catch (e) {
      if (e.status === 404) {
        setDiscussion(null);
      } else {
        setError(e.message);
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
  }, [reviewId]);

  const startDiscussion = async () => {
    try {
      const created = await api.discussions.create({ reviewId });
      setDiscussion(created);
    } catch (e) {
      setError(e.message);
    }
  };

  const sendMessage = async (e) => {
    e.preventDefault();
    if (!messageText.trim() || !discussion?.discussionId) return;
    try {
      await api.discussions.addMessage(discussion.discussionId, { content: messageText.trim() });
      setMessageText('');
      load();
    } catch (e) {
      setError(e.message);
    }
  };

  if (loading) {
    return <p className="discussion-loading">Loading discussion…</p>;
  }

  return (
    <div className="discussion-panel">
      {error && <p className="discussion-error">{error}</p>}

      {!discussion ? (
        <button type="button" className="btn btn-small btn-outline" onClick={startDiscussion}>
          Start discussion
        </button>
      ) : (
        <>
          <h4 className="discussion-title">Discussion #{discussion.discussionId}</h4>
          <ul className="messages-list">
            {(discussion.messages || []).map((msg) => (
              <li key={msg.messageId} className="message-item">
                <p>{msg.content}</p>
                <time>{msg.createdAt ? new Date(msg.createdAt).toLocaleString() : ''}</time>
              </li>
            ))}
            {!(discussion.messages || []).length && (
              <li className="message-empty">No messages yet — be the first to reply.</li>
            )}
          </ul>
          <form className="message-form" onSubmit={sendMessage}>
            <textarea
              value={messageText}
              onChange={(e) => setMessageText(e.target.value)}
              placeholder="Write a message…"
              rows={2}
              required
            />
            <button type="submit" className="btn btn-small">
              Send
            </button>
          </form>
        </>
      )}
    </div>
  );
}

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import BookmarkService from '../../services/BookmarkService';

import styles from './PublicBookmarksView.module.css';

export default function PublicBookmarksView() {

  let bookmarks = [];
  let notification = {};

  function getPageData() {
    bookmarks = BookmarkService.getPublicBookmarks();
    // bookmarks = BookmarkService.getNoBookmarks();
    if (bookmarks.length === 0) {
      // Log details and display message for user
      notification = {
        type: 'error',
        message: 'Sorry, unable to access bookmark data at this time. Please try again later.',
      };
    }
  }
  getPageData();

  function handleReportClick(event) {
    event.target.textContent = 'Reported';
    event.target.title = 'Reported';
    event.target.classList.add(styles.reportButtonInactive);
    event.target.disabled = true;
  }

  return (
    <>
      <header className={styles.viewHeader}>
        <h2>Public Bookmarks</h2>
      </header>

      {notification.message && (
        <div className={styles.notifications}>
          <div role="alert" className={`${styles.alert} ${styles.error}`}>
            <span className={styles.message}>{notification.message}</span>
          </div>
        </div>
      )}

      {bookmarks.length > 0 && (
        <div>
          <table className={styles.tableFull}>
            <thead>
              <tr>
                <th>Link</th>
                <th>Description</th>
                <th>Tags</th>
                <th>User</th>
                <th>&nbsp;</th>
              </tr>
            </thead>
            <tbody>
              {bookmarks.map((bookmark) => (
                <tr key={bookmark.bookmarkId}>
                  <td>
                    <a href={bookmark.url} target="_blank">
                      {bookmark.title}
                    </a>
                    <FontAwesomeIcon className="external-link" icon="fa-solid fa-external-link" />
                  </td>
                  <td>{bookmark.description}</td>
                  <td>{bookmark.allTags}</td>
                  <td>{bookmark.userDisplayName}</td>
                  <td>
                    <button
                      className={bookmark.flagged ? styles.reportButtonInactive : styles.reportButton}
                      title={bookmark.flagged ? 'Reported' : 'Report this bookmark'}
                      disabled={bookmark.flagged}
                      onClick={handleReportClick}
                    >
                      {bookmark.flagged ? ('Reported') : ('Report')}
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </>
  );
}

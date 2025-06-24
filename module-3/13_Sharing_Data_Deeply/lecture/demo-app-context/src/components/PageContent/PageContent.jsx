import RestrictedContent from '../RestrictedContent/RestrictedContent';
import styles from './PageContent.module.css';

export default function PageContent() {

  return (
    <main className={styles.pageContent}>
      <h1>Welcome</h1>
      <p>This is the main content area.</p>
      <RestrictedContent />
    </main>
  );
}

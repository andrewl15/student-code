import SideNav from '../SideNav/SideNav';
import PageContent from '../PageContent/PageContent';
import styles from './MainContent.module.css';

export default function MainContent() {

  return (
    <div className={styles.container}>
      <SideNav />
      <PageContent />
    </div>
  );
}

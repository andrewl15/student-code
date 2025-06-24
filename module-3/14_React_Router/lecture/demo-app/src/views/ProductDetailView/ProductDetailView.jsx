import { useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import InventoryService from '../../services/InventoryService';
import styles from './ProductDetailView.module.css';

export default function ProductDetailView() {
  // TODO: get the ID from the URL
  // TODO: Display product details
  const { id } = useParams();

  // get the item from inventory
  // THIS WILL NOT WORK WITH A REAL API!!!
  // I'll show you an example of how to grab this from a real api tomorrow
  const [item] = useState(InventoryService.getItem(id));

  return (
    <section className={styles.productDetail}>
      <article>
        <h2>{item.name}</h2>
        <p className={styles.productType}>Type: {item.itemType}</p>
        <p className={styles.productPrice}>Price: {item.price}</p>
        <p className={styles.productDescription}>Description: {item.description}</p>
      </article>

      <aside>
        <Link to='/products'>Back to Product List</Link>
      </aside>
    </section>
  );
}

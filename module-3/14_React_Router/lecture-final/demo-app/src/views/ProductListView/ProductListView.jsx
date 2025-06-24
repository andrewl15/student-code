import { useState } from 'react';
import { Link } from 'react-router-dom';
import InventoryService from '../../services/InventoryService';
import styles from './ProductListView.module.css';

export default function ProductListView() {
  // get all items from inventory
  // THIS WILL NOT WORK WITH A REAL API!!!
  const [items] = useState(InventoryService.getItems());

  //TODO: Link to product page

  return (
    <section className={styles.productList}>
      <h2>Product List</h2>
      {items.map((item) => (
        <article key={item.id}>
          <p className={styles.productName}>
              <Link to={ `/products/${item.id}`} >
                {item.name}
              </Link>
          </p>
          <p className={styles.productType}>Type: {item.itemType}</p>
          <p className={styles.productPrice}>Price: {item.price}</p>
        </article>
      ))}
    </section>
  );
}

import { useState } from 'react';
import styles from './Counter.module.css';

export default function Counter() {

  let localCounter = 0;
  
  const [stateCounter, setStateCounter] = useState(0);

  function handleLocalIncrement() {
    localCounter += 1;
    console.log('Local Counter:', localCounter);
  }

  function updateCounter(){
    setStateCounter(stateCounter + 1);
  }
  return (
    <div className={styles.counterBox}>
      <h1>Counter Example</h1>
      <div>
        <h2>Local Counter: {localCounter} </h2>
        <button onClick={handleLocalIncrement}>Increment Local Counter</button>
      </div>
      <div>
        <h2>State Counter: {stateCounter}</h2>
        <button onClick={updateCounter}>Increment State Counter</button>
      </div>
    </div>
  );
}

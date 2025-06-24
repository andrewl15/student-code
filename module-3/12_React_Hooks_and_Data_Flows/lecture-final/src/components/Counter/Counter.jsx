import styles from './Counter.module.css';
import { useState } from 'react';

export default function Counter() {

  // This is a plain old regular variable, changing its value won't 
  // alter the component's state and will not rerender the component
  let localCounter = 0;

  // TODO: Import useState

  // TODO: Add a state variable called stateCounter, default it to 0.

  // TODO: Add a a wrapper function that calls the stateCounter setter and increases it
  // by 1 each run. 

  // TODO: Add an event listener that responds to clicks on the appropriate button

  const [stateCounter, setStateCounter] = useState(0);

  function handleStateIncrement() {
    setStateCounter( stateCounter + 1);
    console.log('State Counter: ', stateCounter);
  }

  function handleLocalIncrement() {
    localCounter += 1;
    console.log('Local Counter:', localCounter);
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
        <button onClick={handleStateIncrement}>Increment State Counter</button>
      </div>
    </div>
  );
}

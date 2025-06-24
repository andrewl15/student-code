import { useState, useEffect } from 'react';
import CelestialObjectsService from '../../Services/CelestialObjectsService';

export default function CelestialObjects() {

  // TODO: Import useState and useEffect

  // TODO: Define state variables

  // TODO: Make a function that reaches out to the following GET endpoint:
  // https://teapi.netlify.app/api/celestial-objects

  // TODO: Define a useEffect hook, and call the above function

  // TODO: Display the planet info in the HTML

  const [celestialObjects, setCelestialObjects] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  function getPageData() {
    setIsLoading(true);
    CelestialObjectsService.getCelestialObjects().then(
      (response) => {
        console.log(response.data)
        setCelestialObjects(response.data);
        setIsLoading(false);
      }
    ).catch(
      (error) => {
        console.log(error.message)
      }
    );
  }

  useEffect(
    () => { getPageData() },
    []
  );

  return (
    <div>
      {isLoading ? <p>Loading...</p> :
        celestialObjects.map(
          (celestialObject) => (
            <article key={celestialObject.id}>
              <h2>{celestialObject.name}</h2>
              <p>Distance: {celestialObject.distance}</p>
              <p>Description: {celestialObject.description}</p>
            </article>
          )
        )
      }
    </div>
  );
}

//TODO: Import Counter Component 
import Counter from "./components/Counter/Counter";
//TODO: Import CelestialObjects
import CelestialObjects from './components/CelestialObjects/CelestialObjects';
//TODO: Import QuizQuestions
import QuizQuestion from './components/QuizQuestion/QuizQuestion';


export default function App() {
  return (
    <div>
      <Counter/>
      <QuizQuestion/>
    </div>
  );
}

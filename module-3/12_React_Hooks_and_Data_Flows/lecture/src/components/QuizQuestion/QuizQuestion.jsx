import styles from './QuizQuestion.module.css';
import { useState } from 'react'
import QuizAnswer from '../QuizAnswer/QuizAnswer';

export default function QuizQuestion() {

  //TODO: Import useState

  //TODO: Add a state variable for selectedAnswer

  //TODO: Import QuizAnswer

  //TODO: Make a function that wraps around and call setSelectedAnswer call.

  //TODO: Go setup the QuizAnswer component

  //TODO: Loop through answers, make one instance of QuizAnswer per item in answers

  //TODO: Add notification for whether the answer is correct or not
  const quizData = {
    question: 'What is the capital of France?',
    answers: ['Berlin', 'Madrid', 'Paris', 'Rome'],
    correctAnswer: 'Paris',
  }

  const [selectedAnswer, setSelectedAnswer] = useState(null);

  function handleAnswer(answer){
    setSelectedAnswer(answer);
  }

  return (
    <div className={styles.quizQuestion}>
      <h2>{quizData.question}</h2>
      {
        selectedAnswer !== null && (
          <p>
            You answered { selectedAnswer === quizData.correctAnswer ? "Correct" : "Incorrectly"}
          </p>
        )
      }

      <ul className={styles.answerList}>

          {
            quizData.answers.map(
              (answer, index) => (
                <li key={index} className={styles.answerList}>
                  <QuizAnswer
                    answer={answer}
                    isCorrect={answer === quizData.correctAnswer}
                    onAnswer={handleAnswer}
                    selectedAnswer={selectedAnswer}
                  />
                </li>
              )
            )
          }

      </ul>
    </div>
  );
}

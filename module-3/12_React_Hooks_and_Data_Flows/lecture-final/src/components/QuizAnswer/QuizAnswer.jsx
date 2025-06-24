import styles from './QuizAnswer.module.css';

// TODO: Add props

// TODO: Add a reference to function onAnswer

// TODO: Display the answer prop value in the button

// TODO: When the button is clicked, call the parent's
// function referenced by onAnswer.

// TODO: Add conditional logic to format the answer based
// on whether it's correct or not

// TODO: Go back to Quiz Question
export default function QuizAnswer( {answer, isCorrect, onAnswer, selectedAnswer} ) {

  function handleClick(event) {

    if(selectedAnswer) {
      return;
    }

      const button = event.target;

      if(isCorrect) {
        button.classList.add(styles.correct);
      } else {
        button.classList.add(styles.incorrect);
      }

      onAnswer(answer);
  }

  return (
    <button className={styles.answerButton} onClick={handleClick}>
        {answer}
    </button>
  );
}

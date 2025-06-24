import LanguageList from './LanguageList'

export default function App() {

    //TODO: Add two variables

    //TODO: Add a return with valid JSX

    //TODO: Add a function that changes the text of the button

    //TODO: Display the LanguageList Component in the JSX

    //TODO: Display the LanguageList Component in the JSX, but only if showLanguage is set to true

    //TODO: Call the function with an onclick.

    const language = 'React';
    const showLanguages = true;

    function handleClick(event) {
        if( event.target.classList.contains('clicked') ) {
            console.log('Button unclicked');
            event.target.textContent = 'Click Me!';
            event.target.classList.remove('clicked');
        } else {
            console.log('Button clicked');
            event.target.textContent = 'Clicked!';
            event.target.classList.add('clicked');
        }
    }

    return(
        <div>
            <h1>Hello World!</h1>
            <p>This is a {language} application.</p>

            { showLanguages ? <LanguageList/> : <p>Languages are hidden</p> }

            <button onClick={ (event) => handleClick(event) }>Click Me!</button>
        </div>
    );
}

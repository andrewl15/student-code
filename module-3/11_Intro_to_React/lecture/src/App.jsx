import { useState } from "react";
import LanguageList from "./LanguageList";

export default function App() {

    //TODO: Add two variables
    const language = 'React';
    const [visibile, setVisibility] = useState(false);

    //TODO: Add a function that changes the text of the button
    function handleClick(event){
        changeVisibility();

        if(event.target.classList.contains('clicked')) {
            console.log('Button unclicked')
            event.target.textContent = 'Click Me';
            event.target.classList.remove('clicked');
        } else {
            console.log('Button clicked')
            event.target.textContent = 'Clicked';
            event.target.classList.add('clicked');
        }
    }

    function changeVisibility(){
        setVisibility(!visibile);
        
    }

    //TODO: Add a return with valid JSX
    return(
        <>
            <div>
                <h1>Hello World</h1>
                <p>This is a {language} application</p>

                {visibile ? <LanguageList/> : <p>Languages are hidden</p>}

                <button className='clicked' onClick={handleClick}>Click Me</button>
            </div>
        </>
    );
    

    //TODO: Display the LanguageList Component in the JSX

    //TODO: Display the LanguageList Component in the JSX, but only if showLanguage is set to true

    //TODO: Call the function with an onclick.

}

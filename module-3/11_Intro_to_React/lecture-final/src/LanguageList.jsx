//TODO: Import CSS module
import styles from './LanguageList.module.css';

export default function LanguageList() {
    const languages = ['React', 'JavaScript', 'HTML', 'CSS'];
    const languageObjects = [
      { id: 1, language: 'React' },
      { id: 2, language: 'JavaScript' },
      { id: 3, language: 'HTML' },
      { id: 4, language: 'CSS' },
    ];


    //TODO: Loop through languages, make a bullet point list of languages

    //TODO: Loop through languageObjects, make a bullet point list of languages.

    //TODO: Apply css styling to the second list
    
    return(
        <>
            <h1 className={styles.componentHeader}>Language List</h1>
            <ol>
                {
                    languages.map(
                        (item, index) => (
                            <li key={index}>{item}</li>
                        )
                    )
                }
            </ol>

            <ul className={styles.languageList}>
                {
                    languageObjects.map(
                        (item) => (
                            <li key={item.id}>{item.language}</li>
                        )
                    )
                }
            </ul>
        </>
    );
}
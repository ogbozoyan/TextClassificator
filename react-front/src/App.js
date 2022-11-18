import styles from './App.module.css';
import { useState } from 'react';
import { ClassificationResult } from './components/ClassificationResult'
import { FileMeta } from './components/FileMeta'
import { BrowseButton } from './components/BrowseButton'
import { UploadButton } from './components/UploadButton'

function App() {

  const [file, setFile] = useState()
  const [result, setResult] = useState('')

  function uploadFile(event) {
    event.preventDefault()
    var data = new FormData()
    data.append('file', file)

    fetch('http://172.20.10.7:8080/upload/', {
      method: 'POST',
      body: data,
    })
    .then((response) => {
      console.log(response)
      return response.json()
    })
    .then((response) => {
        console.log(response)
        let cl = JSON.parse(response.result)
        setResult(`id: ${cl.id} | category: ${cl.result}`)
      }
    )
  }

  function onFile(event) {
    console.log(event.target.files[0])
    setFile(event.target.files[0])
  }

  return (
    <>
      <form className={styles.form}>
        <h1>Classificator</h1>
        <BrowseButton onFile={onFile}/>
        <UploadButton uploadFile={uploadFile}/>
        {result !== '' &&
          <ClassificationResult result={result}/>
        }
        {file &&
          <FileMeta file={file}/>
        }
      </form>
    </>
  );
}

export default App;

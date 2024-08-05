import './App.css';
import {useState,useEffect,useCallback} from "react";
import Slide from "./component/Slide";

function App() {

  const initSlide = {id:"dummy",type:"TITLE",titleText:"I am loading"};


  const [currentSlide,setCurrentSlide] = useState(initSlide);



  useEffect(() => {

    const intervalID = setInterval(() => {
      //clearInterval(intervalID);
      fetch('http://localhost:8080/api/readSlide?attime=' + Date.now())
          .then(res => res.json())
          .then(loadedData => {
            setCurrentSlide(loadedData)

          });
    }, 1_000);
    return () => clearInterval(intervalID);
  }, []);



  return (
    <div className={"MainApp"}>
      {(!currentSlide) ? <div className={"loadingText"}>Loading...</div> : <Slide content={currentSlide}></Slide>}

    </div>
  );
}

export default App;

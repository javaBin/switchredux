import './App.css';
import {useState,useEffect,useCallback,} from "react";
import Slide from "./component/Slide";

function App() {


  const [slideDeck,setSlideDeck] = useState({id: "dummy",slideList:[]});
  const [ordershown,setOrderShown] = useState(-1);


  const updateSlide = useCallback(() => {
    var newNumber = ordershown+1;
    if (newNumber >= slideDeck.slideList.length) {
      newNumber = 0;
    }
    if (newNumber === 0) {
      fetch('http://localhost:8080')
          .then(res => res.json())
          .then(loadedData => {
            setSlideDeck(loadedData);
            setOrderShown(0);
          });
    } else {
      setOrderShown(newNumber);
    }

  }, [ordershown,slideDeck]);



  /*

  useEffect(() => {
    const intervalID = setInterval(() => {
        fetch('http://localhost:8080')
              .then(res => res.json())
              .then(loadedData => setSlideDeck(loadedData));
    }, 2_000);
    return () => clearInterval(intervalID);
  },[setOrderShown]);*/


  useEffect(() => {
    const intervalID = setInterval(updateSlide, 5000);
    return () => clearInterval(intervalID);
  }, [updateSlide])



  return (
    <div className={"MainApp"}>
      {(slideDeck.slideList.length === 0) ? <div className={"loadingText"}>Loading...</div> : <Slide content={slideDeck.slideList[ordershown]}></Slide>}

    </div>
  );
}

export default App;

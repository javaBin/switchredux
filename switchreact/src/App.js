import './App.css';
import {useState,useEffect,useCallback} from "react";
import Slide from "./component/Slide";

function App() {
  const slideDeck = [
    {
      type: "title",
      titleText: "Welcome to JavaZone"
    },
    {
      type: "content"
    },
    {
      type: "title",
      titleText: "See you at the party"
    },
  ]
  const [ordershown,setOrderShown] = useState(0)

  const updateSlide = useCallback(() => {
    var newNumber = ordershown+1;
    if (newNumber >= slideDeck.length) {
      newNumber = 0;
    }
    setOrderShown(newNumber);
  }, [ordershown]);

  useEffect(() => {
    const intervalID = setInterval(updateSlide, 5000);
    return () => clearInterval(intervalID);
  }, [updateSlide])


  return (
    <div className={"MainApp"}>
      <Slide content={slideDeck[ordershown]}></Slide>

    </div>
  );
}

export default App;

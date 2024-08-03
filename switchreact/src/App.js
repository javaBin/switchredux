import './App.css';
import {useState,useEffect,useCallback} from "react";
import Slide from "./component/Slide";

function App() {

  //const initSlide = {id:"dummy",type:"title",titleText:"I am dummy"};
  const initSlide = {id:"dummy",type:"NEXT_SLOT",roomList: [
      {
        id: "0",
        room: "Room 1"
      },
      {
        id: "1",
        room: "Room 2"
      },
      {
        id: "2",
        room: "Room 3"
      },
      {
        id: "3",
        room: "Room 4"
      },
      {
        id: "4",
        room: "Room 5"
      },
      {
        id: "5",
        room: "Room 6"
      },
      {
        id: "6",
        room: "Room 7"
      },
      {
        id: "7",
        room: "Room 8"
      }
    ]};

  const [currentSlide,setCurrentSlide] = useState(initSlide);



  useEffect(() => {

    const intervalID = setInterval(() => {
      clearInterval(intervalID);
      fetch('http://localhost:8080/api/readSlide?attime=' + Date.now())
          .then(res => res.json())
          .then(loadedData => {
            setCurrentSlide(loadedData)

          });
    }, 1500);
    return () => clearInterval(intervalID);
  }, []);



  return (
    <div className={"MainApp"}>
      {(!currentSlide) ? <div className={"loadingText"}>Loading...</div> : <Slide content={currentSlide}></Slide>}

    </div>
  );
}

export default App;

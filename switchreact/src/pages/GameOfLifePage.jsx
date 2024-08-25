import {useState} from "react";

const boardWidth = 100;
const boardHeight = 70;

const generateBoard = (pattern,spacebetween) => {
    const result = [];
    let genratedRows = 0
    while (genratedRows < boardHeight) {
        const row = [];
        result.push(row);
        genratedRows++;
    }
    return result;
}


const gliderGun = [
    "-------------------------------------",
    "-------------------------*-----------",
    "-----------------------*-*-----------",
    "-------------**------**------------**",
    "------------*---*----**------------**",
    "-**--------*-----*---**--------------",
    "-**--------*---*-**----*-*-----------",
    "-----------*-----*-------*-----------",
    "------------*------------------------",
    "-------------**----------------------",
]

const genBoard = () => {
    const blankspace = 10;
    return Array.from({length: boardHeight}, (ycurrent,y) => {
        const curry = y % (gliderGun.length+blankspace);
        return Array.from({length: boardWidth}, (xcurrent,x) => {
            if (curry >= gliderGun.length) {
                return false;
            }
            const currx = x % (gliderGun[curry].length+blankspace);
            if (currx >= gliderGun[curry].length) {
                return false;
            }
            return (gliderGun[curry][currx] === '*');

        });
    });
}

const contentArrayStart = genBoard();

/*
const contentArrayStart = Array.from({length: boardHeight}, (ycurrent,y) =>
    Array.from({length: boardWidth}, (xcurrent,x) => {
            if (y >= gliderGun.length) {
                return false
            }
            if (x >= gliderGun[y].length) {
                return false
            }
            return (gliderGun[y][x] === '*');
            //return (Math.floor(Math.random() * 1000) < 400);
            //return ((x === 20 && y === 20) || (x === 19 && y === 21) || (x === 20 && y === 21) || (x === 20 && y === 22) || (x === 21 && y === 22));
        }
    ));*/

const cellIsAlive = (board,x,y) => {
    if (x < 0 || y < 0 || y >= boardHeight || x >= boardWidth) {
        return false;
    }
    return board[y][x];
}

const countNeighbours = (board,x,y) => {
    let count = 0;
    count += cellIsAlive(board, x - 1, y - 1) ? 1 : 0;
    count += cellIsAlive(board, x, y - 1) ? 1 : 0;
    count += cellIsAlive(board, x + 1, y - 1) ? 1 : 0;
    count += cellIsAlive(board, x - 1, y) ? 1 : 0;
    count += cellIsAlive(board, x + 1, y) ? 1 : 0;
    count += cellIsAlive(board, x - 1, y + 1) ? 1 : 0;
    count += cellIsAlive(board, x, y + 1) ? 1 : 0;
    count += cellIsAlive(board, x + 1, y + 1) ? 1 : 0;
    return count;
}

const nextCycle = (board) => {
    const nextBoard = Array.from({length: boardHeight}, (ycurrent,y) =>
        Array.from({length: boardWidth}, (xcurrent,x) => {
            const myNeighbourCount = countNeighbours(board,x,y);
            if (board[y][x]) {
                return (myNeighbourCount === 2 || myNeighbourCount === 3);
            } else {
                return (myNeighbourCount === 3);
            }
        }));
    return nextBoard;
}

const GameOfLifePage = () => {
    const [contentArray,setContentArray] = useState(contentArrayStart);

    useState(() => {
        const intervalID = setInterval(() => {
            //clearInterval(intervalID);
            setContentArray((currentContent) => nextCycle(currentContent));
        }, 200);
        return () => clearInterval(intervalID);
    },[])

    return (<div className={"gameOfLifePage"}>
        {contentArray.flat().map((item, index) => {
            const className = item ? "gameOfLifeCellFilled" : "gameOfLifeCellEmpty";
            return (<div className={className} key={index}></div>)
        })}
    </div>) ;
}

export default GameOfLifePage;
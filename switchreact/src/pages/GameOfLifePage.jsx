import {useState} from "react";

const boardWidth = 100;
const boardHeight = 70;

const contentArrayStart = Array.from({length: boardHeight}, (ycurrent,y) =>
    Array.from({length: boardWidth}, (xcurrent,x) =>
        ((x === 20 && y===20) || (x === 19 && y === 21) || (x === 20 && y === 21) || (x === 20 && y === 22) || (x === 21 && y === 22))
    ));

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
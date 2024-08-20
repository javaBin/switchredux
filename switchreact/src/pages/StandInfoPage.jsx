const StandInfoPage = ({content}) => {
    return (<div className={"standInfoPage"}>
        <div className={"standInfoHeader"}>Visit the JavaBin stand. Now you can among others meet...</div>
        <div className={"standPerson"}>{content.name}</div>
        <div className={"chatHeader"}>Lets chat about</div>
        <ul className={"chatList"}>
            {content.chatList.map((chatTopic,index) => <li className={"chatListItem"} key={index}>{chatTopic}</li>)}
        </ul>
    </div>)
};

export default StandInfoPage;
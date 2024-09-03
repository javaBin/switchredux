const StandInfoPage = ({content}) => {
    return (<div className={"standInfoPage"}>
        <div className={"standInfoHeader"}>Visit the javaBin stand now and meet</div>
        <div className={"standPersonSection"}>
            {content.personList.map((standPerson,standPersonIndex) =>
                <div key={standPersonIndex} className={"oneStandPerson"}>
                    <div className={"oneStandPerson"}>
                        <div className={"standPerson"}>{standPerson.name}</div>
                        <div className={"chatHeader"}>Lets chat about</div>
                        <ul className={"chatList"}>
                            {standPerson.chatList.map((chatItem, chatItemIndex) =>
                                <li className={"chatListItem"} key={chatItemIndex}>{chatItem}</li>
                            )}
                        </ul>
                    </div>
                </div>
            )}


        </div>
    </div>)
};

export default StandInfoPage;
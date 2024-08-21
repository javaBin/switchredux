const StandInfoPage = ({content}) => {
    return (<div className={"standInfoPage"}>
        <div className={"standInfoHeader"}>Visit the JavaBin stand now and meet...</div>
        <div className={"standPersonSection"}>
            <div className={"oneStandPerson"}>
                <div className={"standPerson"}>Peder</div>
                <div className={"chatHeader"}>Lets chat about</div>
                <ul className={"chatList"}>
                    <li className={"chatListItem"}>Net</li>
                    <li className={"chatListItem"}>Azure</li>
                </ul>
            </div>
            <div className={"oneStandPerson"}>
                <div className={"standPerson"}>Sverre</div>
                <div className={"chatHeader"}>Lets chat about</div>
                <ul className={"chatList"}>
                    <li className={"chatListItem"}>Java</li>
                    <li className={"chatListItem"}>Jvm</li>
                    <li className={"chatListItem"}>Api design</li>
                </ul>
            </div>
            <div className={"oneStandPerson"}>
                <div className={"standPerson"}>Lisa</div>
                <div className={"chatHeader"}>Lets chat about</div>
                <ul className={"chatList"}>
                    <li className={"chatListItem"}>Garbage collection</li>
                    <li className={"chatListItem"}>Jvm</li>
                    <li className={"chatListItem"}>Class compilation</li>
                </ul>
            </div>

        </div>
    </div>)
};

export default StandInfoPage;
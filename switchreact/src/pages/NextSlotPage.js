function NextSlotPage({content}) {
    return (<div className={"nextSlotPage"}>
        <header className={"nextSlotHeader"}>Starting at {content.startsAt}</header>
        {content.roomList.map((roomItem) => {
            return (<div className={"nextSlotItem"} key={roomItem.roomName}>
                <div className={"nextSlotRoom"}>{roomItem.roomName}</div>

                {roomItem.talkList.map((talkItem) => {
                    return (<div className={"talkItem"} key={talkItem.title}>
                        <div className={"titleText"}>{talkItem.title}</div>
                        <div>{talkItem.speaker}</div>
                    </div>)
                })}


            </div>)
        })}
    </div>);
    /*
    return (<div className={"nextSlotPage"}>
        <header className={"nextSlotHeader"}>Your Header here</header>
        {content.roomList.map((roomItem,roomindex) => {
            const lightningTalk = (roomindex % 2 === 0)
            return (<div className={"nextSlotItem"}>
                <div className={"nextSlotRoom"}>{roomItem.room}</div>
                {lightningTalk && <div>
                    <div className={"shortTalkItem"}>
                        <div className={"titleText"}>Vi skal alle bli gamle – husk å designe for eldre</div>
                        <div>Elen Haksø</div>
                    </div>
                    <div className={"shortTalkItem"}>
                        <div>Vi skal alle bli gamle – husk å designe for eldre (10min)</div>
                        <div>Elen Haksø</div>
                    </div>
                    <div className={"shortTalkItem"}>
                        <div>Vi skal alle bli gamle – husk å designe for eldre</div>
                        <div>Elen Haksø</div>
                    </div>
                </div>}
                {!lightningTalk &&
                    <div className={"longTalkItem"}>
                        <div>Mastering GC: tame the beast and make it your best ally (60 min)</div>
                        <div>Jean-Philippe Bempel</div>
                    </div>
                }

            </div>)
        })}
    </div>);*/
};

export default NextSlotPage;
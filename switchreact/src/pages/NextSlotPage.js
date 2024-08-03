function NextSlotPage({content}) {
    return (<div className={"nextSlotPage"}>
        <header className={"nextSlotHeader"}>Your Header here</header>
        {content.roomList.map((roomItem) => {
            return (<div className={"nextSlotItem"}>
                <div className={"nextSlotRoom"}>{roomItem.room}</div>
                <div>Mastering GC: tame the beast and make it your best ally</div>
                <div>Jean-Philippe Bempel</div>

            </div>)
        })}
    </div>);
};

export default NextSlotPage;
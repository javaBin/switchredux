function ContentPage({content}) {
    return (<div className={"contentPage"}>
        <div className={"contentHeading"}>{content.titleText}</div>
        {content.contextTexts.map((contentText,index) => <div className={"contentContent"} key={index}>{contentText}</div> )}
    </div>)
}

export default ContentPage;
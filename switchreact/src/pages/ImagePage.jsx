const ImagePage = ({content}) => {
    return (<div className={"imagePage"}>
        {content.imagePathList.map((item, index) => (<img key={index} src={item} />))}
    </div>)
};

export default ImagePage;
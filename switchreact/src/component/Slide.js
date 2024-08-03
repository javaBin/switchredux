import TitlePage from "../pages/TitlePage";
import ContentPage from "../pages/ContentPage";
import NextSlotPage from "../pages/NextSlotPage";

function Slide({content}) {
    switch(content.type) {
        case "TITLE":
            return (<TitlePage content={content}></TitlePage>)
        case "CONTENT":
            return (<ContentPage content={content}></ContentPage>)
        case "NEXT_SLOT":
            return (<NextSlotPage content={content}></NextSlotPage> )
        default:
            return (<div>Unknown</div>)
    }
}

export default Slide;
import TitlePage from "../pages/TitlePage";
import ContentPage from "../pages/ContentPage";

function Slide({content}) {
    switch(content.type) {
        case "title":
            return (<TitlePage content={content}></TitlePage>)
        case "content":
            return (<ContentPage content={content}></ContentPage>)
    }
}

export default Slide;
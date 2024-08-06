import TitlePage from "../pages/TitlePage";
import ContentPage from "../pages/ContentPage";
import NextSlotPage from "../pages/NextSlotPage";
import PartnerSummaryPage from "../pages/PartnerSummaryPage";
import GameOfLifePage from "../pages/GameOfLifePage";

function Slide({content}) {
    switch(content.type) {
        case "TITLE":
            return (<TitlePage content={content}></TitlePage>)
        case "CONTENT":
            return (<ContentPage content={content}></ContentPage>)
        case "NEXT_SLOT":
            return (<NextSlotPage content={content}></NextSlotPage> )
        case "PARTNER_SUMMARY":
            return (<PartnerSummaryPage />)
        case "GAME_OF_LIFE":
            return (<GameOfLifePage ></GameOfLifePage>)
        default:
            return (<div>Unknown</div>)
    }
}

export default Slide;
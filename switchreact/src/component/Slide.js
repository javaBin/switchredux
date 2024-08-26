import TitlePage from "../pages/TitlePage";
import ContentPage from "../pages/ContentPage";
import NextSlotPage from "../pages/NextSlotPage";
import PartnerSummaryPage from "../pages/PartnerSummaryPage";
import GameOfLifePage from "../pages/GameOfLifePage";
import {StrictMode} from "react";
import StandInfoPage from "../pages/StandInfoPage";
import ImagePage from "../pages/ImagePage";
import ParterImagePage from "../pages/ParterImagePage";

function Slide({content}) {
    switch(content.type) {
        case "TITLE":
            return (<TitlePage content={content}></TitlePage>)
        case "CONTENT":
            return (<ContentPage content={content}></ContentPage>)
        case "NEXT_SLOT":
            return (<NextSlotPage content={content}></NextSlotPage> )
        case "PARTNER_SUMMARY":
            //return (<PartnerSummaryPage />)
            return (<ParterImagePage></ParterImagePage>);
        case "GAME_OF_LIFE":
            return (<GameOfLifePage ></GameOfLifePage>)
        case "STAND_INFO":
            return (<StandInfoPage content={content}></StandInfoPage>);
        case "IMAGE_SLIDE":
            return (<ImagePage content={content}></ImagePage>)
        default:
            return (<div>Unknown</div>)
    }
}

export default Slide;
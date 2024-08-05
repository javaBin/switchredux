const partnerListStored = [
    { name: "Aboveit", homepageUrl: "https://www.aboveit.no", logoUrl: "/svgpartner/aboveit.svg" },
    { name: "Accenture", homepageUrl: "https://www.accenture.com", logoUrl: "/svgpartner/accenture.svg" },
    { name: "Arktekk", homepageUrl: "https://www.arktekk.no", logoUrl: "/svgpartner/arktekk.svg" },
    { name: "Bekk", homepageUrl: "https://www.bekk.no", logoUrl: "/svgpartner/bekk.svg" },
    { name: "Bouvet", homepageUrl: "https://www.bouvet.no", logoUrl: "/svgpartner/bouvet.svg" },
    { name: "Buypass", homepageUrl: "https://www.buypass.no", logoUrl: "/svgpartner/buypass.svg" },
    { name: "Capgemini", homepageUrl: "https://www.capgemini.com", logoUrl: "/svgpartner/capgemini.svg" },
    { name: "Capra", homepageUrl: "https://www.capraconsulting.no", logoUrl: "/svgpartner/capra.svg" },
    { name: "CGI", homepageUrl: "https://www.cgi.com", logoUrl: "/svgpartner/cgi.svg" },
    { name: "Cloudberries", homepageUrl: "https://www.cloudberries.no", logoUrl: "/svgpartner/cloudberries.svg" },
    { name: "Computas", homepageUrl: "https://www.computas.com", logoUrl: "/svgpartner/computas.svg" },
    { name: "DNB", homepageUrl: "https://www.dnb.no", logoUrl: "/svgpartner/dnb.svg" },
    { name: "Elastic", homepageUrl: "https://www.elastic.co", logoUrl: "/svgpartner/elastic.svg" },
    { name: "Embriq", homepageUrl: "https://www.embriq.no", logoUrl: "/svgpartner/embriq.svg" },
    { name: "Entur", homepageUrl: "https://www.entur.no", logoUrl: "/svgpartner/entur.svg" },
    { name: "Experis", homepageUrl: "https://www.experis.no", logoUrl: "/svgpartner/experis-cropped.svg" },
    { name: "Finn", homepageUrl: "https://www.finn.no", logoUrl: "/svgpartner/finn.svg" },
    { name: "Fremtind", homepageUrl: "https://www.fremtind.no", logoUrl: "/svgpartner/fremtind.svg" },
    { name: "Gjensidige", homepageUrl: "https://www.gjensidige.no", logoUrl: "/svgpartner/gjensidige.svg" },
    { name: "Google", homepageUrl: "https://www.google.com", logoUrl: "/svgpartner/google.svg" },
    { name: "Husbanken", homepageUrl: "https://www.husbanken.no", logoUrl: "/svgpartner/husbanken.svg" },
    { name: "Itera", homepageUrl: "https://www.itera.no", logoUrl: "/svgpartner/itera.svg" },
    { name: "Kantega", homepageUrl: "https://www.kantega.no", logoUrl: "/svgpartner/kantega.svg" },
    { name: "Knowit", homepageUrl: "https://www.knowit.no", logoUrl: "/svgpartner/knowit.svg" },
    { name: "Kodemaker", homepageUrl: "https://www.kodemaker.no", logoUrl: "/svgpartner/kodemaker.svg" },
    { name: "Miles", homepageUrl: "https://www.miles.no", logoUrl: "/svgpartner/miles.svg" },
    { name: "NAV", homepageUrl: "https://www.nav.no", logoUrl: "/svgpartner/nav.svg" },
    { name: "Netcompany", homepageUrl: "https://www.netcompany.com", logoUrl: "/svgpartner/netcompany-cropped.svg" },
    { name: "Norsk Tipping", homepageUrl: "https://www.norsk-tipping.no", logoUrl: "/svgpartner/norsktipping.svg" },
    { name: "KS Digitale Fellestjenester", homepageUrl: "https://ksdif.no/", logoUrl: "/svgpartner/ks-digitale-fellestjenester.svg" },
    { name: "Novari IKS", homepageUrl: "https://www.vigoiks.no/", logoUrl: "/svgpartner/novari.svg" },
    { name: "Oslo", homepageUrl: "https://www.oslo.kommune.no", logoUrl: "/svgpartner/oslo.svg" },
    { name: "Politiet PIT", homepageUrl: "https://www.politiet.no", logoUrl: "/svgpartner/politietpit.svg" },
    { name: "Posten Bring", homepageUrl: "https://www.bring.no", logoUrl: "/svgpartner/postenbring.svg" },
    { name: "Protector", homepageUrl: "https://www.protector.no", logoUrl: "/svgpartner/protector.svg" },
    { name: "Red Hat", homepageUrl: "https://www.redhat.com", logoUrl: "/svgpartner/redhat.svg" },
    { name: "SafetyWing", homepageUrl: "https://www.safetywing.com", logoUrl: "/svgpartner/safetywing-cropped.svg" },
    { name: "Scelto", homepageUrl: "https://www.scelto.no", logoUrl: "/svgpartner/scelto.svg" },
    { name: "Scienta", homepageUrl: "https://www.scienta.no", logoUrl: "/svgpartner/scienta.svg" },
    { name: "Skatteetaten", homepageUrl: "https://www.skatteetaten.no", logoUrl: "/svgpartner/skatteetaten.svg" },
    { name: "Sopra Steria", homepageUrl: "https://www.soprasteria.no", logoUrl: "/svgpartner/soprasteria.svg" },
    { name: "SpareBank 1", homepageUrl: "https://www.sparebank1.no", logoUrl: "/svgpartner/sparebank1.svg" },
    { name: "Spirgroup", homepageUrl: "https://www.spirgroup.no", logoUrl: "/svgpartner/spirgroup-cropped.svg" },
    { name: "SSB", homepageUrl: "https://www.ssb.no", logoUrl: "/svgpartner/ssb.svg" },
    { name: "Statens Vegvesen", homepageUrl: "https://www.vegvesen.no", logoUrl: "/svgpartner/statensvegvesen.svg" },
    { name: "Statnett", homepageUrl: "https://www.statnett.no", logoUrl: "/svgpartner/statnett.svg" },
    { name: "Storebrand", homepageUrl: "https://www.storebrand.no", logoUrl: "/svgpartner/storebrand.svg" },
    { name: "Systek", homepageUrl: "https://www.systek.no", logoUrl: "/svgpartner/systek.svg" },
    { name: "Techpros", homepageUrl: "https://www.techpros.no", logoUrl: "/svgpartner/techpros.svg" },
    { name: "Telenor", homepageUrl: "https://www.telenor.no", logoUrl: "/svgpartner/telenor.svg" },
    { name: "Tet Digital", homepageUrl: "https://ruter.no/om-ruter/jobb", logoUrl: "/svgpartner/tetdigital.svg" },
    { name: "Tietoevry", homepageUrl: "https://www.tietoevry.com", logoUrl: "/svgpartner/tietoevry.svg" },
    { name: "Tolletaten", homepageUrl: "https://www.toll.no", logoUrl: "/svgpartner/tolletaten.svg" },
    { name: "Tripletex", homepageUrl: "https://www.tripletex.no", logoUrl: "/svgpartner/tripletex.svg" },
    { name: "Twoday", homepageUrl: "https://www.twoday.no", logoUrl: "/svgpartner/twoday.svg" },
    { name: "Uptime", homepageUrl: "https://www.uptimeconsulting.no", logoUrl: "/svgpartner/uptime.svg" },
    { name: "Vaadin", homepageUrl: "https://vaadin.com", logoUrl: "/svgpartner/vaadin.svg" },
    { name: "Webstep", homepageUrl: "https://www.webstep.no", logoUrl: "/svgpartner/webstep.svg" },
];



const PartnerSummaryPage = () => {
    const partnerList = [...partnerListStored];
    partnerList.sort(() => Math.random() - 0.5);
    return (<div className={"partnerSummaryPage"}>
        <div className={"partnerSummaryHeading"}>Proud partners</div>
        <div className={"partnerSummaryMain"}>
            {partnerList.map((partner, ) => {
                return (<img className={"partnerimg"} src={partner.logoUrl} alt={partner.name} key={partner.name}/>);
            })}
        </div>
    </div>)
}

export default PartnerSummaryPage;
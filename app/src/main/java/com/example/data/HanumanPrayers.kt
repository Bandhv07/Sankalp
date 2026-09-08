package com.example.data

object HanumanPrayers {

    data class ChalisaVerse(
        val verseNumber: Int,
        val text: String,
        val meaning: String
    )

    data class ChalisaFullScript(
        val language: String,
        val openingDoha: String,
        val verses: List<ChalisaVerse>,
        val closingDoha: String
    )

    data class PrayerScript(
        val language: String,
        val sankalpaTitle: String,
        val sankalpaMantra: String,
        val dhyanaShloka: String,
        val translation: String
    )

    val prayerScripts = listOf(
        PrayerScript(
            language = "Telugu",
            sankalpaTitle = "శ్రీ హనుమాన్ 11 రోజుల సంకల్పం (హనుమాన్ చాలీసా)",
            sankalpaMantra = """
                ఓం అస్య శ్రీ హనుమత్ మహాసంకల్పం:
                మమ సర్వారిష్ట నివారణార్థం, సర్వకార్య సిద్ధిరస్తు, 
                సమస్త భయ నివృత్యర్థం, ఆయురారోగ్య ఐశ్వర్యాభివృద్ధ్యర్థం, 
                శ్రీ సీతారామచంద్ర ప్రీత్యర్థం, శ్రీ ఆంజనేయ స్వామి అనుగ్రహార్థం,
                ఏకాదశ దిన (11 రోజులు) సంకల్పం, ప్రతిదినం ఏకాదశ పర్యాయం (11 సార్లు) 
                శ్రీ హనుమాన్ చాలీసా పఠనం కరిష్యే.
            """.trimIndent(),
            dhyanaShloka = """
                మనోజవం మారుతతుల్య వేగం
                జితేంద్రియం బుద్ధిమతాం వరిష్ఠం |
                వాతాత్మజం వానరయూథ ముఖ్యం
                శ్రీరామదూతం శరణం ప్రపద్యే ||

                బుద్ధిర్బలం యశో ధైర్యం నిర్భయత్వమరోగతా |
                అజాడ్యం వాక్పటుత్వంచ హనుమత్స్మరణాద్భవేత్ ||
            """.trimIndent(),
            translation = "ఓం శ్రీ ఆంజనేయ స్వామి! నా సకల కష్టాలు తొలగి, సమస్త కార్యములు సిద్ధించుటకై, 11 రోజుల పాటు రోజుకు 11 సార్లు బ్రహ్మ ముహూర్తంలో సంకల్ప పూర్వకంగా ఈ హనుమాన్ చాలీసా పఠనము చేయుచున్నాను."
        ),
        PrayerScript(
            language = "English",
            sankalpaTitle = "Sri Hanuman 11-Day Sacred Sankalpam (Hanuman Chalisa)",
            sankalpaMantra = """
                Om Asya Sri Hanumat Maha-Sankalpam:
                Mama sarva-arishta nivaaranaartham, 
                Sarva-kaarya siddhi-artham,
                Samasta bhaya nivritti-artham,
                Aayur-aarogya aishvarya-abhivriddhi-artham,
                Sri Sita-Ramachandra preetyartham, 
                Sri Anjaneya Swamy anugrahaartham,
                Ekadasha dina (11 days) sankalpam, 
                Pratidinam ekadasha paryayam (11 times daily),
                Sri Hanuman Chalisa pathanam karishye.
            """.trimIndent(),
            dhyanaShloka = """
                Manojavam Maaruta-Tulya Vegam
                Jitendriyam Buddhimataam Varishtam |
                Vaataatmajam Vaanara-Yootha Mukhyam
                Sri Raama Dootam Sharanam Prapadye ||

                Buddhir-Balam Yasho Dhairyam Nirbhayatvam-Arogataa |
                Ajaadyam Vaakpatutvam Cha Hanumat-Smaranaad Bhavet ||
            """.trimIndent(),
            translation = "Prayer: May Lord Hanuman remove all obstacles and fears, grant victory in righteous deeds, vitality, wisdom, and health. I undertake this sacred 11-day vow, reciting Sri Hanuman Chalisa 11 times daily at Brahma Muhurtam."
        ),
        PrayerScript(
            language = "Hindi",
            sankalpaTitle = "श्री हनुमान ११ दिवसीय पावन संकल्प (हनुमान चालीसा)",
            sankalpaMantra = """
                ॐ अस्य श्री हनुमत् महासंकल्पम्:
                मम सर्वारिष्ट निवारणार्थम्, सर्वकार्य सिद्धिरस्तु,
                समस्त भय निवृत्त्यर्थम्, आयुरारोग्य ऐश्वर्याभिवृद्ध्यर्थम्,
                श्री सीतारामचन्द्र प्रीत्यर्थम्, श्री आञ्जनेय स्वामी अनुग्रहार्थम्,
                एकादश दिन (११ दिवस) संकल्पम्, प्रतिदिनम् एकादश वारम् (११ बार),
                श्री हनुमान चालीसा पठनं करिष्ये।
            """.trimIndent(),
            dhyanaShloka = """
                मनोजवं मारुततुल्यवेगं जितेन्द्रियं बुद्धिमतां वरिष्ठम्।
                वातात्मजं वानरयूथमुख्यं श्रीरामदूतं शरणं प्रपद्ये॥

                बुद्धिर्बलं यशो धैर्यं निर्भयत्वमरोगता।
                अजाड्यं वाक्पटुत्वं च हनुमत्स्मरणाद्भवेत्॥
            """.trimIndent(),
            translation = "हे पवनसुत हनुमान! समस्त कष्टों के निवारण, निर्भयता, आरोग्य और कार्य सिद्धि हेतु मैं यह ११ दिवसीय पावन संकल्प प्रतिदिन ११ बार श्री हनुमान चालीसा पाठ ब्रह्म मुहूर्त में पूर्ण करने का प्रण लेता हूँ।"
        )
    )

    // Complete 40 Chaupais + Dohas of Sri Hanuman Chalisa in Telugu, English, and Hindi
    val chalisaTelugu = ChalisaFullScript(
        language = "Telugu",
        openingDoha = """
            దోహా:
            శ్రీగురు చరణ సరోజ రజ నిజమను ముకురు సుధారి |
            బరనౌ రఘుబర బిమల జసు జో దాయకు ఫల చారి ||
            బుద్ధిహీన తను జానికై సుమిరౌ పవన-కుమార |
            బల బుద్ధి బిద్యా దేహు మోహి హరహు కలేస బికార ||
        """.trimIndent(),
        verses = listOf(
            ChalisaVerse(1, "జయ హనుమాన జ్ఞాన గుణ సాగర | జయ కపీస తిహు లోక ఉజాగర ||", "జ్ఞాన సముద్రుడైన హనుమంతునికి జయం! త్రిలోకాలను ప్రకాశింపజేసే కపీశ్వరునికి జయం."),
            ChalisaVerse(2, "రామదూత అతులిత బలధామా | అంజని పుత్ర పవనసుత నామా ||", "సాటిలేని బలసంపన్నుడైన శ్రీరామదూత, అంజనీదేవి పుత్రుడు, పవనకుమారుడైన స్వామికి నమస్కారం."),
            ChalisaVerse(3, "మహావీర బిక్రమ బజరంగీ | కుమతి నివార సుమతి కే సంగీ ||", "వజ్రసమాన దేహము కల మహావీరుడు, కుబుద్ధిని తొలగించి సద్బుద్ధిని ప్రసాదించేవాడు."),
            ChalisaVerse(4, "కంచన బరణ బిరాజ సుబేసా | కానన కుండల కుంచిత కేసా ||", "బంగారు వర్ణపు శోభ, దివ్య కుండలాలు, ఉంగరాల జుట్టుతో విరాజిల్లుతున్నవాడు."),
            ChalisaVerse(5, "హాథ బజ్ర ఔ ధ్వజా బిరాజై | కాంధే మూంజ జనేవూ సాజై ||", "చేతిలో వజ్రాయుధం, విజయ ధ్వజం, భుజమున ముంజ గడ్డి యజ్ఞోపవీతము శోభిల్లుతున్నది."),
            ChalisaVerse(6, "శంకర సువన కేసరీ నందన | తేజ ప్రతాప మహా జగ బందన ||", "శివాంశ సంభూతుడు, కేసరీనందనుడు; లోకాలన్నింటిలో పూజించబడే మహా పరాక్రమవంతుడు."),
            ChalisaVerse(7, "బిద్యావాన గుణీ అతి చాతుర | రామ కాజ కరిబే కో ఆతుర ||", "సకల విద్యావంతుడు, గుణవంతుడు, పరమ చతురుడు; శ్రీరాముని సేవకై సదా తత్పరుడు."),
            ChalisaVerse(8, "ప్రభు చరిత్ర సునిబే కో రసియా | రామ లఖన సీతా మన బసియా ||", "శ్రీరామ చరితామృతమును వినుటలో పరమ ఆనందితుడు; రామ లక్ష్మణ సీతల హృదయాల్లో నివసించువాడు."),
            ChalisaVerse(9, "సూక్ష్మ రూప ధరి సియహి దిఖావా | బికట రూప ధరి లంక జరావా ||", "అతి సూక్ష్మ రూపంతో సీతమ్మను దర్శించి, భీకర విరాట్ రూపంతో లంకను దహించినవాడు."),
            ChalisaVerse(10, "భీమ రూప ధరి అసుర సంహారే | రామచంద్ర కే కాజ సంవారే ||", "భయంకర రూపముతో రాక్షసులను సంహరించి, శ్రీరాముని కార్యములను చక్కబెట్టినవాడు."),
            ChalisaVerse(11, "లాయ సంజీవన లఖన జియాయే | శ్రీరఘుబీర హరషి ఉర లాయే ||", "సంజీవని తెచ్చి లక్ష్మణుని ప్రాణాలు నిలిపి, శ్రీరాముని గాఢ ఆలింగనము పొందినవాడు."),
            ChalisaVerse(12, "రఘుపతి కీన్హీ బహుత బడాయీ | తుమ మమ ప్రియ భరతహి సమ భాయీ ||", "రఘునాథుడు నిన్ను ప్రశంసిస్తూ 'నీవు నాకు సోదరుడైన భరతునితో సమానం' అని ప్రశంసించెను."),
            ChalisaVerse(13, "సహస్ర బదన తుమ్హరో జస గావై | అస కహి శ్రీపతి కంఠ లగావై ||", "వేయి పడగల ఆదిశేషుడు నీ కీర్తిని గానము చేయునని శ్రీరాముడు నిన్ను కౌగిలించుకొనెను."),
            ChalisaVerse(14, "సనకాదిక బ్రహ్మాది మునీసా | నారద శారద సహిత అహీసా ||", "సనక సనందనాదులు, బ్రహ్మ, మునులు, నారదుడు, సరస్వతీదేవి నీ గుణాలను గానము చేయుదురు."),
            ChalisaVerse(15, "జమ కుబేర దిగపాల జహాం తే | కబి కోబిద కహి సకే కహాం తే ||", "యముడు, కుబేరుడు, దిక్పాలకులు, కవులు పండితులు కూడా నీ వైభవమును పూర్తిగా వర్ణించలేరు."),
            ChalisaVerse(16, "తుమ ఉపకార సుగ్రీవహి కీన్హా | రామ మిలాయ రాజపద దీన్హా ||", "సుగ్రీవునికి శ్రీరామునితో మైత్రి కుదిర్చి, కిష్కింధా రాజ్యాధిపత్యమును ఇప్పించిన పరమోపకారి."),
            ChalisaVerse(17, "తుమ్హరో మంత్ర బిభీషణ మానా | లంకేశ్వర భయే సబ జగ జానా ||", "నీ ఉపదేశమును పాటించి విభీషణుడు లంకాధిపతియైన సంగతి లోకమంతటికీ తెలిసినదే."),
            ChalisaVerse(18, "జుగ సహస్ర జోజన పర భానూ | లీల్యో తాహి మధుర ఫల జానూ ||", "కోట్ల మైళ్ళ దూరంలో ఉన్న సూర్యుని తియ్యని పండు అనుకొని అలవోకగా మింగినవాడవు."),
            ChalisaVerse(19, "ప్రభు ముద్రికా మేలి ముఖ మాహీ | జలధి లాంఘి గయే అచరజ నాహీ ||", "శ్రీరాముని అంగుళీయకమును నోటనుంచుకుని సముద్రమును సులభముగా దాటావనుటలో ఆశ్చర్యమేమున్నది!"),
            ChalisaVerse(20, "దుర్గమ కాజ జగత కే జేతే | సుగమ అనుగ్రహ తుమ్హరే తేతే ||", "ఈ లోకంలో ఎంతటి కష్టతరమైన పనులైనా నీ కృపతో మిక్కిలి సులభమవుతాయి."),
            ChalisaVerse(21, "రామ దువారే తుమ రఖవారే | హోత న ఆజ్ఞా బిను పైసారే ||", "శ్రీరాముని అంతఃపుర ద్వారపాలకుడవు నీవు; నీ అనుమతి లేక ఎవరూ ప్రవేశించలేరు."),
            ChalisaVerse(22, "సబ సుఖ లహై తుమ్హారీ శరణా | తుమ రక్షక కాహూ కో డర నా ||", "నీ శరణు జొచ్చినవారికి సర్వ సుఖాలు కలుగుతాయి; నీవు రక్షకుడవుగా ఉన్నప్పుడు ఇక భయమేల!"),
            ChalisaVerse(23, "ఆపన తేజ సంహారో ఆపై | తీనోం లోక హాంక తే కాంపై ||", "నీ తేజస్సును నీవే నియంత్రించగలవు; నీ ఒక్క గర్జనకే ముల్లోకాలు గడగడలాడతాయి."),
            ChalisaVerse(24, "భూత పిశాచ నికట నహి ఆవై | మహాబీర జబ నామ సునావై ||", "మహావీర హనుమంతుని నామస్మరణ చేయుచోట భూత ప్రేత పిశాచాలు దరిదాపులకు కూడా రాలేవు."),
            ChalisaVerse(25, "నాసై రోగ హరై సబ పీరా | జపత నిరంతర హనుమత బీరా ||", "హనుమంతుని నిరంతరం జపించే భక్తుల రోగాలన్నీ నశించి, సర్వ బాధలు సమసిపోతాయి."),
            ChalisaVerse(26, "సంకట తే హనుమాన ఛుడావై | మన క్రమ బచన ధ్యాన జో లావై ||", "మనసా, వాచా, కర్మణా నిన్ను ధ్యానించే వారిని సమస్త సంకటముల నుండి విముక్తులను చేస్తావు."),
            ChalisaVerse(27, "సబ పర రామ తపస్వీ రాజా | తినకే కాజ సకల తుమ సాజా ||", "తపస్వి చక్రవర్తియైన శ్రీరాముని సకల కార్యాలను సంపూర్ణముగా నిర్వహించినవాడవు నీవే."),
            ChalisaVerse(28, "ఔర మనోరథ జో కోయీ లావై | సోయి అమిత జీవన ఫల పావై ||", "నీవద్దకు ఏ కోరికతో వచ్చినవారైనా అమితమైన దివ్య ఫలితమును పొందుతారు."),
            ChalisaVerse(29, "చారోం జుగ పరతాప తుమ్హారా | హై పరసిద్ధ జగత ఉజియారా ||", "నాలుగు యుగాలలోనూ నీ ప్రతాపం వెలుగుతూ లోకమంతటికీ దివ్య కాంతిని ప్రసాదిస్తున్నది."),
            ChalisaVerse(30, "సాధు సంత కే తుమ రఖవారే | అసుర నికందన రామ దులారే ||", "సాధు సజ్జనుల రక్షకుడవు, దుష్ట రాక్షస సంహారకుడవు, శ్రీరామునికి ప్రాణప్రియమైనవాడవు."),
            ChalisaVerse(31, "అష్ట సిద్ధి నవ నిధి కే దాతా | అస బర దీన్హ జానకీ మాతా ||", "అణిమాది అష్ట సిద్ధులు, నవ నిధులను అనుగ్రహించే వరమును సీతామాత నీకు ప్రసాదించెను."),
            ChalisaVerse(32, "రామ రసాయన తుమ్హరే పాసా | సదా రహో రఘుపతి కే దాసా ||", "రామనామ రసాయనం నీవద్ద సదా ఉన్నది; నీవు నిరంతరం రఘుపతి సేవలోనే నిమగ్నుడవు."),
            ChalisaVerse(33, "తుమ్హరే భజన రామ కో పావై | జన్మ జన్మ కే దుఃఖ బిసరావై ||", "నిన్ను భజించినవారు శ్రీరాముని అనుగ్రహం పొంది జన్మజన్మల దుఃఖాలను మరచిపోతారు."),
            ChalisaVerse(34, "అంత కాల రఘుబర పుర జాయీ | జహాం జన్మ హరిభక్త కహాయీ ||", "అంత్యకాలమున వారు శ్రీరాముని పరమధామమును చేరి, సదా హరిభక్తులుగా వర్ధిల్లుతారు."),
            ChalisaVerse(35, "ఔర దేవతా చిత్త న ధరయీ | హనుమత సేయి సర్వ సుఖ కరయీ ||", "ఇతర దేవతలను ఆరాధించకున్ననూ, ఒక్క హనుమంతుని సేవిస్తేనే సర్వ సుఖాలు చేకూరుతాయి."),
            ChalisaVerse(36, "సంకట కటై మిటై సబ పీరా | జో సుమిరై హనుమత బలబీరా ||", "మహా బలవంతుడైన హనుమంతుని స్మరించే భక్తులకు సమస్త సంకటాలు, బాధలు నివారణమవుతాయి."),
            ChalisaVerse(37, "జై జై జై హనుమాన గోసాయీ | కృపా కరహు గురుదేవ కీ నాయీ ||", "జయ జయ శ్రీ హనుమాన్! గురుదేవుని వలె నాపై అపారమైన కృపను కురిపించు స్వామి!"),
            ChalisaVerse(38, "జో శత వార పాఠ కర కోయీ | ఛూటహి బంది మహా సుఖ హోయీ ||", "ఎవరైతే దీనిని నూరు మార్లు (లేదా భక్తితో) పఠిస్తారో వారు సమస్త బంధనాల నుండి ముక్తులై పరమ సుఖాన్ని పొందుతారు."),
            ChalisaVerse(39, "జో యహ పఢై హనుమాన చాలీసా | హోయ సిద్ధి సాఖీ గౌరీసా ||", "ఈ హనుమాన్ చాలీసాను పఠించిన వారికి సర్వ సిద్ధులు లభించునని సాక్షాత్తు గౌరీపతియైన శివుడే సాక్షి."),
            ChalisaVerse(40, "తులసీదాస సదా హరి చేరా | కీజై నాథ హృదయ మహం డేరా ||", "తులసీదాసు సదా శ్రీరాముని సేవకుడు; ఓ నాథా! నా హృదయమందిరంలో సదా కొలువై ఉండుము.")
        ),
        closingDoha = """
            దోహా:
            పవనతనయ సంకట హరణ మంగళ మూరతి రూప |
            రామ లఖన సీతా సహిత హృదయ బసహు సుర భూప ||
        """.trimIndent()
    )

    val chalisaEnglish = ChalisaFullScript(
        language = "English",
        openingDoha = """
            Doha:
            Shri Guru Charan Saroj Raj, Nij Manu Mukur Sudhari |
            Barnau Raghuvar Bimal Jasu, Jo Dayaku Phal Chari ||
            Budhiheen Tanu Janike, Sumirau Pavan-Kumar |
            Bal Budhi Vidya Dehu Mohi, Harahu Kalesh Bikaar ||
        """.trimIndent(),
        verses = listOf(
            ChalisaVerse(1, "Jai Hanuman Gyan Gun Sagar | Jai Kapis Tihun Lok Ujagar ||", "Victory to Hanuman, ocean of knowledge and virtue! Victory to the Lord of monkeys who illuminates the three worlds."),
            ChalisaVerse(2, "Ram Doot Atulit Bal Dhama | Anjani Putra Pavan Sut Nama ||", "Messenger of Lord Rama, storehouse of immeasurable strength, son of Anjani, revered as Pavan-suta."),
            ChalisaVerse(3, "Mahabir Bikram Bajrangi | Kumati Nivar Sumati Ke Sangi ||", "Great hero, possessing thunderbolt strength, dispeller of evil thoughts and companion of pure wisdom."),
            ChalisaVerse(4, "Kanchan Baran Biraj Subesa | Kanan Kundal Kunchit Kesa ||", "Radiant with golden hue, splendidly adorned with ear-rings and curly locks."),
            ChalisaVerse(5, "Hath Bajra Aur Dhwaja Birajai | Kandhe Moonj Janeu Saajai ||", "Holding the thunderbolt and sacred flag in hand, wearing the holy sacred thread over the shoulder."),
            ChalisaVerse(6, "Shankar Suvan Kesari Nandan | Tej Pratap Maha Jag Bandan ||", "Incarnation of Lord Shiva and joy of Kesari; your magnificent aura is adored by the universe."),
            ChalisaVerse(7, "Vidyavan Guni Ati Chatur | Ram Kaj Karibe Ko Aatur ||", "Supreme scholar, virtuous and profoundly wise; always eager to fulfill Lord Rama's divine mission."),
            ChalisaVerse(8, "Prabhu Charitra Sunibe Ko Rasiya | Ram Lakhan Sita Man Basiya ||", "Immersed in bliss listening to the glories of Rama; dwelling forever in the hearts of Rama, Lakshmana, and Sita."),
            ChalisaVerse(9, "Sukshma Roop Dhari Siyahin Dikhava | Bikat Roop Dhari Lanka Jarava ||", "Appearing before Mother Sita in minute form, and assuming a colossal form to incinerate Lanka."),
            ChalisaVerse(10, "Bhim Roop Dhari Asur Samhare | Ramchandra Ke Kaaj Sanvare ||", "Assuming a formidable warrior form to vanquish demons and accomplish Sri Rama's work."),
            ChalisaVerse(11, "Laye Sanjivan Lakhan Jiyaye | Shri Raghubir Harashi Ur Laye ||", "Bringing the Sanjeevani herb to revive Lakshmana, embraced in overwhelming joy by Lord Rama."),
            ChalisaVerse(12, "Raghupati Kinhi Bahut Badayi | Tum Mam Priya Bharat-hi Sam Bhai ||", "Lord Rama extolled you with immense praise: 'You are as dear to me as my brother Bharata!'"),
            ChalisaVerse(13, "Sahas Badan Tumharo Jas Gave | As Kahi Shripati Kanth Lagave ||", "'The thousand-hooded serpent sings your glory,' spoke Sri Rama while embracing you tenderly."),
            ChalisaVerse(14, "Sanakadik Brahmadi Munisa | Narad Sarad Sahit Ahisa ||", "Sanaka and the sages, Lord Brahma, Narada, Goddess Saraswati, and Sheshanaga sing your virtues."),
            ChalisaVerse(15, "Yam Kuber Digpal Jahan Te | Kabi Kobid Kahi Sake Kahan Te ||", "Yama, Kubera, and the guardians of directions cannot fully capture your limitless glory."),
            ChalisaVerse(16, "Tum Upkar Sugrivahin Kinha | Ram Milaye Rajpad Dinha ||", "You rendered monumental service to Sugriva by uniting him with Rama and restoring his kingdom."),
            ChalisaVerse(17, "Tumharo Mantra Bibhishan Mana | Lankeshwar Bhaye Sab Jag Jana ||", "Vibhishana followed your sacred counsel and became the ruler of Lanka, known to all."),
            ChalisaVerse(18, "Jug Sahasra Jojan Par Bhanu | Lilyo Tahi Madhur Phal Janu ||", "Leaping across millions of miles toward the sun, you swallowed it as a sweet fruit."),
            ChalisaVerse(19, "Prabhu Mudrika Meli Mukh Mahi | Jaladhi Langhi Gaye Acharaj Nahi ||", "Holding Rama's signet ring in your mouth, you effortlessly crossed the ocean—no wonder!"),
            ChalisaVerse(20, "Durgam Kaaj Jagat Ke Jete | Sugam Anugraha Tumhre Tete ||", "Every impossible task in this world becomes effortless and achieved through your divine grace."),
            ChalisaVerse(21, "Ram Duware Tum Rakhware | Hot Na Agya Binu Paisare ||", "You stand guard at Sri Rama's threshold; no one enters without your loving permission."),
            ChalisaVerse(22, "Sab Sukh Lahai Tumhari Sharna | Tum Rakshak Kahu Ko Dar Na ||", "All joy and tranquility reside under your refuge; with you as protector, what is there to fear?"),
            ChalisaVerse(23, "Aapan Tej Samharo Aape | Teenon Lok Hank Te Kampe ||", "Only you can contain your blazing radiance; at your roar, all three realms tremble."),
            ChalisaVerse(24, "Bhoot Pishach Nikat Nahi Aave | Mahabir Jab Naam Sunave ||", "Negative forces, ghosts, and afflictions dare not approach when the heroic name of Mahavira is uttered."),
            ChalisaVerse(25, "Nase Rog Hare Sab Peera | Japat Nirantar Hanumat Beera ||", "All ailments are cured and every suffering ends for those who chant the name of brave Hanuman continuously."),
            ChalisaVerse(26, "Sankat Te Hanuman Chhudave | Man Kram Bachan Dhyan Jo Lave ||", "Hanuman frees from all crises anyone who meditates upon him in thought, action, and speech."),
            ChalisaVerse(27, "Sab Par Ram Tapasvi Raja | Tin Ke Kaaj Sakal Tum Saja ||", "Lord Rama reigns supreme over all as the ascetic king, and you orchestrated all his noble endeavors."),
            ChalisaVerse(28, "Aur Manorath Jo Koi Lave | Soi Amit Jivan Phal Pave ||", "Whoever brings any pure prayer to you receives the limitless, immortal fruits of life."),
            ChalisaVerse(29, "Charon Jug Partap Tumhara | Hai Parsiddh Jagat Ujiyara ||", "Your glory shines across all four cosmic ages, filling the universe with divine luminescence."),
            ChalisaVerse(30, "Sadhu Sant Ke Tum Rakhware | Asur Nikandan Ram Dulare ||", "Protector of saints and seekers, destroyer of dark demons, dearest beloved of Sri Rama."),
            ChalisaVerse(31, "Asht Siddhi Nav Nidhi Ke Data | As Bar Deen Janaki Mata ||", "Mother Sita bestowed upon you the divine boon to grant the eight supernatural powers and nine treasures."),
            ChalisaVerse(32, "Ram Rasayan Tumhre Pasa | Sada Raho Raghupati Ke Dasa ||", "You hold the elixir of Rama's sacred name; may you forever remain the devoted servant of Raghupati."),
            ChalisaVerse(33, "Tumhre Bhajan Ram Ko Pave | Janam Janam Ke Dukh Bisrave ||", "Devotion to you leads directly to Sri Rama, washing away sorrows accumulated over lifetimes."),
            ChalisaVerse(34, "Anta Kaal Raghubar Pur Jayi | Jahan Janma Hari-Bhakta Kahayi ||", "At the time of departure, the devotee enters Rama's eternal realm, living as a true servant of the Divine."),
            ChalisaVerse(35, "Aur Devata Chitta Na Dharayi | Hanumat Sei Sarva Sukh Karayi ||", "Even without worshiping other deities, serving Hanuman alone bestows every fulfillment."),
            ChalisaVerse(36, "Sankat Kate Mite Sab Peera | Jo Sumire Hanumat Balbeera ||", "All tribulations vanish and every agony ceases for one who remembers the valiant Hanuman."),
            ChalisaVerse(37, "Jai Jai Jai Hanuman Gosain | Kripa Karahu Gurudev Ki Nayi ||", "Hail, hail, hail Lord Hanuman! Bestow your boundless grace like a supreme spiritual teacher."),
            ChalisaVerse(38, "Jo Shat Var Path Kar Koi | Chhutahi Bandi Maha Sukh Hoi ||", "Whoever recites this hundredfold (or with steady faith) is liberated from bondage and gains highest peace."),
            ChalisaVerse(39, "Jo Yeh Padhe Hanuman Chalisa | Hoy Siddhi Sakhi Gaurisa ||", "Whoever recites this Hanuman Chalisa attains spiritual perfection—Lord Shiva himself is the witness."),
            ChalisaVerse(40, "Tulsidas Sada Hari Chera | Kijai Nath Hriday Mahn Dera ||", "Tulsidas is eternally the servant of the Lord. O Protector, make your dwelling in my heart forever.")
        ),
        closingDoha = """
            Doha:
            Pavan Tanay Sankat Haran, Mangal Murati Roop |
            Ram Lakhan Sita Sahit, Hriday Basahu Sur Bhoop ||
        """.trimIndent()
    )

    val chalisaHindi = ChalisaFullScript(
        language = "Hindi",
        openingDoha = """
            दोहा:
            श्रीगुरु चरण सरोज रज निज मनु मुकुरु सुधारि।
            बरनउँ रघुबर बिमल जसु जो दायकु फल चारि॥
            बुद्धिहीन तनु जानिके सुमिरौ पवन-कुमार।
            बल बुद्धि बिद्या देहु मोहि हरहु कलेस बिकार॥
        """.trimIndent(),
        verses = listOf(
            ChalisaVerse(1, "जय हनुमान ज्ञान गुन सागर। जय कपीस तिहुँ लोक उजागर॥", "ज्ञान और गुणों के सागर हनुमान जी की जय हो! तीनों लोकों को प्रकाशित करने वाले कपीश्वर की जय हो।"),
            ChalisaVerse(2, "राम दूत अतुलित बल धामा। अंजनि-पुत्र पवनसुत नामा॥", "आप श्रीराम के दूत, अतुलित बल के धाम, माता अंजनी के पुत्र और पवनसुत के नाम से विख्यात हैं।"),
            ChalisaVerse(3, "महाबीर बिक्रम बजरंगी। कुमति निवार सुमति के संगी॥", "आप परम पराक्रमी, वज्र के समान सुदृढ़ अंग वाले, कुबुद्धि को दूर करने वाले और सुबुद्धि के साथी हैं।"),
            ChalisaVerse(4, "कंचन बरन बिराज सुबेसा। कानन कुंडल कुंचित केसा॥", "आपका वर्ण स्वर्ण के समान दैदीप्यमान है, कानों में मनोहर कुण्डल और घुंघराले केश शोभा पा रहे हैं।"),
            ChalisaVerse(5, "हाथ बज्र औ ध्वजा बिराजै। काँधे मूँज जनेऊ साजै॥", "आपके हाथों में वज्र और ध्वजा शोभायमान हैं, और कंधे पर मूँज का पवित्र जनेऊ सुशोभित है।"),
            ChalisaVerse(6, "शंकर सुवन केसरीनंदन। तेज प्रताप महा जग बंदन॥", "आप भगवान शिव के अवतार और केसरी के नंदन हैं; आपके प्रचंड तेज और प्रताप की वंदना पूरा जगत करता है।"),
            ChalisaVerse(7, "बिद्यावान गुनी अति चातुर। राम काज करिबे को आतुर॥", "आप समस्त विद्याओं के ज्ञाता, परम गुणी व अत्यंत चतुर हैं; प्रभु श्रीराम के काज को तत्पर रहते हैं।"),
            ChalisaVerse(8, "प्रभु चरित्र सुनिबे को रसिया। राम लखन सीता मन बसिया॥", "आप श्रीराम कथा सुनने के परम रसिक हैं; श्रीराम, लक्ष्मण और माता सीता आपके हृदय में निवास करते हैं।"),
            ChalisaVerse(9, "सूक्ष्म रूप धरि सियहिं दिखावा। बिकट रूप धरि लंक जरावा॥", "आपने अत्यंत सूक्ष्म रूप धरकर सीता जी के दर्शन किए और विकराल रूप धारण करके लंका का दहन किया।"),
            ChalisaVerse(10, "भीम रूप धरि असुर संहारे। रामचंद्र के काज संवारे॥", "आपने विशाल रूप धारण कर राक्षसों का संहार किया और श्रीरामचन्द्र जी के कार्यों को संवारा।"),
            ChalisaVerse(11, "लाय सजीवन लखन जियाये। श्रीरघुबीर हरषि उर लाये॥", "संजीवनी बूटी लाकर आपने लक्ष्मण जी के प्राण बचाये, जिससे हर्षित होकर श्रीराम ने आपको हृदय से लगा लिया।"),
            ChalisaVerse(12, "रघुपति कीन्ही बहुत बड़ाई। तुम मम प्रिय भरतहि सम भाई॥", "श्रीराम ने आपकी भूरि-भूरि प्रशंसा की और कहा कि 'तुम मुझे भरत के समान प्रिय भाई हो।'"),
            ChalisaVerse(13, "सहस बदन तुम्हरो जस गावैं। अस कहि श्रीपति कंठ लगावैं॥", "श्रीराम ने यह कहकर आपको गले लगाया कि 'हजारों मुख वाले शेषनाग भी तुम्हारा यश गाते हैं।'"),
            ChalisaVerse(14, "सनकादिक ब्रह्मादि मुनीसा। नारद सारद सहित अहीसा॥", "सनक, सनंदन, ब्रह्मा आदि ऋषि, नारद जी, सरस्वती जी और शेषनाग आपका गुणगान करते हैं।"),
            ChalisaVerse(15, "जम कुबेर दिगपाल जहाँ ते। कबि कोबिद कहि सके कहाँ ते॥", "यमराज, कुबेर और दसों दिशाओं के रक्षक भी आपके पूर्ण यश का वर्णन करने में असमर्थ हैं।"),
            ChalisaVerse(16, "तुम उपकार सुग्रीवहिं कीन्हा। राम मिलाय राजपद दीन्हा॥", "आपने सुग्रीव को श्रीराम से मिलाकर और उन्हें राज्यपद दिलाकर उन पर महान उपकार किया।"),
            ChalisaVerse(17, "तुम्हरो मंत्र बिभीषन माना। लंकेश्वर भए सब जग जाना॥", "आपके परामर्श को मानकर विभीषण लंका के राजा बने, यह सारा संसार जानता है।"),
            ChalisaVerse(18, "जुग सहस्र जोजन पर भानू। लील्यो ताहि मधुर फल जानू॥", "हजारों योजन दूरी पर स्थित सूर्य को आपने मीठा फल समझकर बालपन में ही निगल लिया था।"),
            ChalisaVerse(19, "प्रभु मुद्रिका मेलि मुख माहीं। जलधि लाँघि गये अचरज नाहीं॥", "श्रीराम की अंगूठी मुख में रखकर आपने सहज ही समुद्र लाँघ लिया, इसमें कोई आश्चर्य नहीं।"),
            ChalisaVerse(20, "दुर्गम काज जगत के जेते। सुगम अनुग्रह तुम्हरे तेते॥", "इस संसार के जितने भी कठिन कार्य हैं, वे सब आपकी कृपा से अत्यंत सरल हो जाते हैं।"),
            ChalisaVerse(21, "राम दुआरे तुम रखवारे। होत न आज्ञा बिनु पैसारे॥", "श्रीराम के द्वार के आप रक्षक हैं; आपकी अनुमति के बिना कोई भीतर प्रवेश नहीं कर सकता।"),
            ChalisaVerse(22, "सब सुख लहै तुम्हारी सरना। तुम रक्षक काहू को डर ना॥", "आपकी शरण में आने वाले को सब सुख प्राप्त होते हैं; जब आप रक्षक हैं तो किसी का कोई भय नहीं।"),
            ChalisaVerse(23, "आपन तेज सम्हारो आपै। तीनों लोक हाँक तें काँपै॥", "अपने प्रचंड वेग को आप ही संभाल सकते हैं; आपकी एक हुंकार से तीनों लोक कांप उठते हैं।"),
            ChalisaVerse(24, "भूत पिसाच निकट नहिं आवै। महाबीर जब नाम सुनावै॥", "महावीर हनुमान का नाम स्मरण करने पर भूत-प्रेत और पिशाच कभी पास नहीं आ सकते।"),
            ChalisaVerse(25, "नासै रोग हरै सब पीरा। जपत निरंतर हनुमत बीरा॥", "वीर हनुमान जी का निरंतर जप करने से समस्त रोग और पीड़ाएं नष्ट हो जाती हैं।"),
            ChalisaVerse(26, "संकट तें हनुमान छुड़ावै। मन क्रम बचन ध्यान जो लावै॥", "जो मन, कर्म और वचन से हनुमान जी का ध्यान करता है, उसे वे सब संकटों से मुक्त कर देते हैं।"),
            ChalisaVerse(27, "सब पर राम तपस्वी राजा। तिन के काज सकल तुम साजा॥", "तपस्वी राजा श्रीराम सर्वोपरि हैं, और उनके समस्त कार्यों को आपने ही सिद्ध किया।"),
            ChalisaVerse(28, "और मनोरथ जो कोई लावै। सोइ अमित जीवन फल पावै॥", "जो भी भक्त कोई शुद्ध मनोरथ लेकर आता है, वह जीवन का असीम मंगलमय फल पाता है।"),
            ChalisaVerse(29, "चारों जुग परताप तुम्हारा। है परसिद्ध जगत उजियारा॥", "चारों युगों में आपका प्रताप फैला हुआ है, जिसकी कीर्ति से सम्पूर्ण जगत आलोकित है।"),
            ChalisaVerse(30, "साधु-संत के तुम रखवारे। असुर निकंदन राम दुलारे॥", "आप साधु-संतों के रक्षक, दुष्ट असुरों का नाश करने वाले और श्रीराम के परम दुलारे हैं।"),
            ChalisaVerse(31, "अष्ट सिद्धि नौ निधि के दाता। अस बर दीन जानकी माता॥", "माता जानकी ने आपको यह वरदान दिया है कि आप अष्ट सिद्धि और नौ निधियों के दाता हैं।"),
            ChalisaVerse(32, "राम रसायन तुम्हरे पासा। सदा रहो रघुपति के दासा॥", "आपके पास राम नाम रूपी रसायन है; आप सदा भगवान रघुपति के अनन्य सेवक बने रहें।"),
            ChalisaVerse(33, "तुम्हरे भजन राम को पावै। जनम-जनम के दुख बिसरावै॥", "आपके भजन से भक्त श्रीराम को प्राप्त करता है और जन्म-जन्मांतर के दुखों को भूल जाता है।"),
            ChalisaVerse(34, "अन्तकाल रघुबर पुर जाई। जहाँ जन्म हरि-भक्त कहाई॥", "मृत्यु के उपरांत वह श्रीराम के परमधाम को जाता है और सदा हरिभक्त कहलाता है।"),
            ChalisaVerse(35, "और देवता चित्त न धरई। हनुमत सेइ सर्ब सुख करई॥", "अन्य किसी देवी-देवता का ध्यान न धरते हुए भी केवल हनुमान जी की सेवा से सब सुख प्राप्त होते हैं।"),
            ChalisaVerse(36, "संकट कटै मिटै सब पीरा। जो सुमिरै हनुमत बलबीरा॥", "जो भी बलवीर हनुमान जी का स्मरण करता है, उसके सब संकट कट जाते हैं और सारी पीड़ाएं मिट जाती हैं।"),
            ChalisaVerse(37, "जै जै जै हनुमान गोसाईं। कृपा करहु गुरुदेव की नाईं॥", "हे प्रभु हनुमान! आपकी जय हो, जय हो, जय हो! गुरुदेव की तरह मुझ पर अपनी असीम कृपा कीजिए।"),
            ChalisaVerse(38, "जो सत बार पाठ कर कोई। छूटहि बंदि महा सुख होई॥", "जो कोई इस चालीसा का सौ बार पाठ करता है, वह सब बंधनों से छूटकर परम सुख को पाता है।"),
            ChalisaVerse(39, "जो यह पढ़ै हनुमान चालीसा। होय सिद्धि साखी गौरीसा॥", "जो भी इस हनुमान चालीसा को पढ़ता है, उसे समस्त सिद्धियां प्राप्त होती हैं—इसके साक्षी भगवान शिव हैं।"),
            ChalisaVerse(40, "तुलसीदास सदा हरि चेरा। कीजै नाथ हृदय मँह डेरा॥", "तुलसीदास सदा भगवान के दास हैं; हे नाथ! आप मेरे हृदय में सदा के लिए वास कीजिए।")
        ),
        closingDoha = """
            दोहा:
            पवन तनय संकट हरन, मंगल मूरति रूप।
            राम लखन सीता सहित, हृदय बसहु सुर भूप॥
        """.trimIndent()
    )

    fun getChalisa(language: String): ChalisaFullScript {
        return when {
            language.equals("Telugu", ignoreCase = true) -> chalisaTelugu
            language.equals("Hindi", ignoreCase = true) -> chalisaHindi
            else -> chalisaEnglish
        }
    }

    val brahmaMuhurtamInsights = listOf(
        "Brahma Muhurtam begins precisely 2 Muhurtas (96 minutes / 1h 36m) before local sunrise based on your geographic coordinates.",
        "Known as the 'Creator's Hour', it is when Sattva Guna is at its cosmic peak, mind chatter is tranquil, and the power of Sri Hanuman Chalisa multiplies manifold.",
        "Getting 7 hours of restorative sleep before waking up ensures your body and mind are fresh, pure, and alert for the 11 sacred recitations.",
        "Tradition recommends taking a refreshing bath, lighting a clean ghee diya facing East, and reciting the 40 Chaupais with unwavering devotion."
    )
}

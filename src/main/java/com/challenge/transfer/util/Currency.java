package com.challenge.transfer.util;

//CHECKSTYLE.OFF: JavadocVariable - Simple dictionary
public enum Currency {
    AED("AED", "784", 2, "UAE Dirham"),
    AFN("AFN", "971", 2, "Afghani"),
    ALL("ALL", "008", 2, "Lek"),
    AMD("AMD", "051", 2, "Armenian Dram"),
    ANG("ANG", "532", 2,
            "Netherlands Antillean Guilder"),
    ARS("ARS", "032", 2, "Argentine Peso"),
    AUD("AUD", "036", 2, "Australian Dollar"),
    AWG("AWG", "533", 2, "Aruban Florin"),
    AZN("AZN", "944", 2, "Azerbaijan Manat"),
    BAM("BAM", "977", 2, "Convertible Mark"),
    BBD("BBD", "052", 2, "Barbados Dollar"),
    BDT("BDT", "050", 2, "Taka"),
    BGN("BGN", "975", 2, "Bulgarian Lev"),
    BHD("BHD", "048", 3, "Bahraini Dinar"),
    BIF("BIF", "108", 0, "Burundi Franc"),
    BMD("BMD", "060", 2, "Bermudian Dollar"),
    BND("BND", "096", 2, "Brunei Dollar"),
    BOB("BOB", "068", 2, "Boliviano"),
    BOV("BOV", "984", 2, "Mvdol"),
    BRL("BRL", "986", 2, "Brazilian Real"),
    BSD("BSD", "044", 2, "Bahamian Dollar"),
    BTN("BTN", "064", 2, "Ngultrum"),
    BWP("BWP", "072", 2, "Pula"),
    BYN("BYN", "933", 2, "Belarusian Ruble"),
    BZD("BZD", "084", 2, "Belize Dollar"),
    CAD("CAD", "124", 2, "Canadian Dollar"),
    CDF("CDF", "976", 2, "Congolese Franc"),
    CHE("CHE", "947", 2, "WIR Euro"),
    CHF("CHF", "756", 2, "Swiss Franc"),
    CHW("CHW", "948", 2, "WIR Franc"),
    CLF("CLF", "990", 4, "Unidad de Fomento"),
    CLP("CLP", "152", 0, "Chilean Peso"),
    CNY("CNY", "156", 2, "Yuan Renminbi"),
    COP("COP", "170", 2, "Colombian Peso"),
    COU("COU", "970", 2, "Unidad de Valor Real"),
    CRC("CRC", "188", 2, "Costa Rican Colon"),
    CUC("CUC", "931", 2, "Peso Convertible"),
    CUP("CUP", "192", 2, "Cuban Peso"),
    CVE("CVE", "132", 2, "Cabo Verde Escudo"),
    CZK("CZK", "203", 2, "Czech Koruna"),
    DJF("DJF", "262", 0, "Djibouti Franc"),
    DKK("DKK", "208", 2, "Danish Krone"),
    DOP("DOP", "214", 2, "Dominican Peso"),
    DZD("DZD", "012", 2, "Algerian Dinar"),
    EGP("EGP", "818", 2, "Egyptian Pound"),
    ERN("ERN", "232", 2, "Nakfa"),
    ETB("ETB", "230", 2, "Ethiopian Birr"),
    EUR("EUR", "978", 2, "Euro"),
    FJD("FJD", "242", 2, "Fiji Dollar"),
    FKP("FKP", "238", 2, "Falkland Islands Pound"),
    GBP("GBP", "826", 2, "Pound Sterling"),
    GEL("GEL", "981", 2, "Lari"),
    GHS("GHS", "936", 2, "Ghana Cedi"),
    GIP("GIP", "292", 2, "Gibraltar Pound"),
    GMD("GMD", "270", 2, "Dalasi"),
    GNF("GNF", "324", 0, "Guinean Franc"),
    GTQ("GTQ", "320", 2, "Quetzal"),
    GYD("GYD", "328", 2, "Guyana Dollar"),
    HKD("HKD", "344", 2, "Hong Kong Dollar"),
    HNL("HNL", "340", 2, "Lempira"),
    HRK("HRK", "191", 2, "Kuna"),
    HTG("HTG", "332", 2, "Gourde"),
    HUF("HUF", "348", 2, "Forint"),
    IDR("IDR", "360", 2, "Rupiah"),
    ILS("ILS", "376", 2, "New Israeli Sheqel"),
    INR("INR", "356", 2, "Indian Rupee"),
    IQD("IQD", "368", 3, "Iraqi Dinar"),
    IRR("IRR", "364", 2, "Iranian Rial"),
    ISK("ISK", "352", 0, "Iceland Krona"),
    JMD("JMD", "388", 2, "Jamaican Dollar"),
    JOD("JOD", "400", 3, "Jordanian Dinar"),
    JPY("JPY", "392", 0, "Yen"),
    KES("KES", "404", 2, "Kenyan Shilling"),
    KGS("KGS", "417", 2, "Som"),
    KHR("KHR", "116", 2, "Riel"),
    KMF("KMF", "174", 0, "Comorian Franc"),
    KPW("KPW", "408", 2, "North Korean Won"),
    KRW("KRW", "410", 0, "Won"),
    KWD("KWD", "414", 3, "Kuwaiti Dinar"),
    KYD("KYD", "136", 2, "Cayman Islands Dollar"),
    KZT("KZT", "398", 2, "Tenge"),
    LAK("LAK", "418", 2, "Lao Kip"),
    LBP("LBP", "422", 2, "Lebanese Pound"),
    LKR("LKR", "144", 2, "Sri Lanka Rupee"),
    LRD("LRD", "430", 2, "Liberian Dollar"),
    LSL("LSL", "426", 2, "Loti"),
    LYD("LYD", "434", 3, "Libyan Dinar"),
    MAD("MAD", "504", 2, "Moroccan Dirham"),
    MDL("MDL", "498", 2, "Moldovan Leu"),
    MGA("MGA", "969", 2, "Malagasy Ariary"),
    MKD("MKD", "807", 2, "Denar"),
    MMK("MMK", "104", 2, "Kyat"),
    MNT("MNT", "496", 2, "Tugrik"),
    MOP("MOP", "446", 2, "Pataca"),
    MRU("MRU", "929", 2, "Ouguiya"),
    MUR("MUR", "480", 2, "Mauritius Rupee"),
    MVR("MVR", "462", 2, "Rufiyaa"),
    MWK("MWK", "454", 2, "Malawi Kwacha"),
    MXN("MXN", "484", 2, "Mexican Peso"),
    MXV("MXV", "979", 2,
            "Mexican Unidad de Inversion (UDI)"),
    MYR("MYR", "458", 2, "Malaysian Ringgit"),
    MZN("MZN", "943", 2, "Mozambique Metical"),
    NAD("NAD", "516", 2, "Namibia Dollar"),
    NGN("NGN", "566", 2, "Naira"),
    NIO("NIO", "558", 2, "Cordoba Oro"),
    NOK("NOK", "578", 2, "Norwegian Krone"),
    NPR("NPR", "524", 2, "Nepalese Rupee"),
    NZD("NZD", "554", 2, "New Zealand Dollar"),
    OMR("OMR", "512", 3, "Rial Omani"),
    PAB("PAB", "590", 2, "Balboa"),
    PEN("PEN", "604", 2, "Sol"),
    PGK("PGK", "598", 2, "Kina"),
    PHP("PHP", "608", 2, "Philippine Piso"),
    PKR("PKR", "586", 2, "Pakistan Rupee"),
    PLN("PLN", "985", 2, "Zloty"),
    PYG("PYG", "600", 0, "Guarani"),
    QAR("QAR", "634", 2, "Qatari Rial"),
    RON("RON", "946", 2, "Romanian Leu"),
    RSD("RSD", "941", 2, "Serbian Dinar"),
    RUB("RUB", "643", 2, "Russian Ruble"),
    RWF("RWF", "646", 0, "Rwanda Franc"),
    SAR("SAR", "682", 2, "Saudi Riyal"),
    SBD("SBD", "090", 2, "Solomon Islands Dollar"),
    SCR("SCR", "690", 2, "Seychelles Rupee"),
    SDG("SDG", "938", 2, "Sudanese Pound"),
    SEK("SEK", "752", 2, "Swedish Krona"),
    SGD("SGD", "702", 2, "Singapore Dollar"),
    SHP("SHP", "654", 2, "Saint Helena Pound"),
    SLL("SLL", "694", 2, "Leone"),
    SOS("SOS", "706", 2, "Somali Shilling"),
    SRD("SRD", "968", 2, "Surinam Dollar"),
    SSP("SSP", "728", 2, "South Sudanese Pound"),
    STN("STN", "930", 2, "Dobra"),
    SVC("SVC", "222", 2, "El Salvador Colon"),
    SYP("SYP", "760", 2, "Syrian Pound"),
    SZL("SZL", "748", 2, "Lilangeni"),
    THB("THB", "764", 2, "Baht"),
    TJS("TJS", "972", 2, "Somoni"),
    TMT("TMT", "934", 2, "Turkmenistan New Manat"),
    TND("TND", "788", 3, "Tunisian Dinar"),
    TOP("TOP", "776", 2, "Pa’anga"),
    TRY("TRY", "949", 2, "Turkish Lira"),
    TTD("TTD", "780", 2, "Trinidad and Tobago Dollar"),
    TWD("TWD", "901", 2, "New Taiwan Dollar"),
    TZS("TZS", "834", 2, "Tanzanian Shilling"),
    UAH("UAH", "980", 2, "Hryvnia"),
    UGX("UGX", "800", 0, "Uganda Shilling"),
    USD("USD", "840", 2, "US Dollar"),
    USN("USN", "997", 2, "US Dollar (Next day)"),
    UYI("UYI", "940", 0,
            "Uruguay Peso en Unidades Indexadas (URUIURUI)"),
    UYU("UYU", "858", 2, "Peso Uruguayo"),
    UZS("UZS", "860", 2, "Uzbekistan Sum"),
    VEF("VEF", "937", 2, "Bolívar"),
    VND("VND", "704", 0, "Dong"),
    VUV("VUV", "548", 0, "Vatu"),
    WST("WST", "882", 2, "Tala"),
    XAF("XAF", "950", 0, "CFA Franc BEAC"),
    XAG("XAG", "961", -1, "Silver"),
    XAU("XAU", "959", -1, "Gold"),
    XBA("XBA", "955", -1,
            "Bond Markets Unit European Composite Unit (EURCO)"),
    XBB("XBB", "956", -1,
            "Bond Markets Unit European Monetary Unit (E.M.U.-6)"),
    XBC("XBC", "957", -1,
            "Bond Markets Unit European Unit of Account 9 (E.U.A.-9)"),
    XBD("XBD", "958", -1,
            "Bond Markets Unit European Unit of Account 17 (E.U.A.-17)"),
    XCD("XCD", "951", 2, "East Caribbean Dollar"),
    XDR("XDR", "960", -1,
            "SDR (Special Drawing Right)"),
    XOF("XOF", "952", 0, "CFA Franc BCEAO"),
    XPD("XPD", "964", -1, "Palladium"),
    XPF("XPF", "953", 0, "CFP Franc"),
    XPT("XPT", "962", -1, "Platinum"),
    XSU("XSU", "994", -1, "Sucre"),
    XTS("XTS", "963", -1,
            "Codes specifically reserved for testing purposes"),
    XUA("XUA", "965", -1, "ADB Unit of Account"),
    XXX("XXX", "999", -1, "No currency"),
    YER("YER", "886", 2, "Yemeni Rial"),
    ZAR("ZAR", "710", 2, "Rand"),
    ZMW("ZMW", "967", 2, "Zambian Kwacha"),
    ZWL("ZWL", "932", 2, "Zimbabwe Dollar");

    private final String code;
    private final String numericCode;
    private final int minor;
    private final String name;

    Currency(String code, String numericCode, int minor, String name) {
        this.code = code;
        this.numericCode = numericCode;
        this.minor = minor;
        this.name = name;
    }

    /**
     * Currency code.
     * @return Currency gode
     */
    public String getCode() {
        return code;
    }

    /**
     * Currency numeric code.
     * @return Currency numeric code
     */
    public String getNumericCode() {
        return numericCode;
    }

    /**
     * Currency minor unit.
     * @return Currency minor unit
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Currency name.
     * @return Currency name
     */
    public String getName() {
        return name;
    }
}
//CHECKSTYLE.ON: JavadocVariable

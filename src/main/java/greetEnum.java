public enum greetEnum{
    ENGLISH("english", "Welcome"),
    CZECH("czech", "Vitejte"),
    DANISH("danish", "Velkomst"),
    DUTCH("dutch", "Welkom"),
    ESTONIAN("estonian", "Tere tulemast"),
    FINNISH("finnish", "Tervetuloa"),
    FLEMISH("flemish", "Welgekomen"),
    FRENCH("french", "Bienvenue"),
    IRISH("irish", "Failte"),
    ITALIAN("italian", "Benvenuto"),
    LATVIAN("latvian", "Bienvenue"),
    LITHUANIAN("lithuanian", "LaukiamasLaukiamas"),
    POLISH("polish", "Witamy"),
    SPANISH("spanish", "Bienvenido"),
    SWEDISH("swedish", "Valkommen"),
    WELSH("welsh", "Croeso");

    String country;
    String greeting;

    greetEnum(String country, String greeting){
        this.country = country;
        this.greeting = greeting;
    }


}
public final class InputHandler {
    public static boolean isDirection(char input)
    {
        String directions = "wasd";
        return directions.contains("" + input);
    }
    
    public static boolean isUtilityKey(char input)
    {
        String utilityKeys = "qe123456789";
        return utilityKeys.contains("" + input);
    }

    public static boolean isBattleKey(char input)
    {
        String battleKeys = "1234";
        return battleKeys.contains("" + input);
    }

    public static boolean isFlee(char input)
    {
        return input == '4';
    }

    public static boolean isQuit(char input)
    {
        return input == '~';
    }

    public static boolean isHelp(char input)
    {
        return input == '?';
    }
}

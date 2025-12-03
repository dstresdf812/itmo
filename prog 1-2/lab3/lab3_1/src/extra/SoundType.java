package extra;

public enum SoundType {
    FOOTSTEP("шаги", "звук шагов"),
    CREAK("скрип", "скрипучий звук"),
    RUSTLE("шорох", "шуршащий звук"),
    WHISPER("шёпот", "тихий звук"),
    SHOUT("крик", "громкий звук");

    private final String name;
    private final String description;

    SoundType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
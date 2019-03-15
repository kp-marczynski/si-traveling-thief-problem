package pl.marczynski.pwr.si.ttp.genetic.description;

public enum DescriptionFileType {
    TRIVIAL("trivial_"),
    EASY("easy_"),
    MEDIUM("medium_"),
    HARD("hard_");

    DescriptionFileType(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    private String namePrefix;

    public String getNamePrefix() {
        return namePrefix;
    }
}

package other;

import managers.Checkable;
import utils.StudyGroupComparator;

import java.util.Comparator;

public class StudyGroup implements Checkable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer studentsCount; //Значение поля должно быть больше 0, Поле не может быть null
    private long expelledStudents; //Значение поля должно быть больше 0
    private Long shouldBeExpelled; //Значение поля должно быть больше 0, Поле может быть null
    private FormOfEducation formOfEducation;
    private Person groupAdmin; //Поле может быть null
    // static list of ids
    public boolean check(){
        // if (this.id <= 0) { return false; }
        if (this.name == null || this.name == "") { return false; }
        if (this.coordinates == null) { return false; }
        if (this.creationDate == null) { return false; }
        if (this.studentsCount <= 0 || this.studentsCount == null) { return false; }
        if (this.expelledStudents <= 0) { return false; }
        if (this.shouldBeExpelled <= 0) { return false; }
        return true;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public java.time.ZonedDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(java.time.ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }
    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public long getExpelledStudents() {
        return expelledStudents;
    }
    public void setExpelledStudents(long expelledStudents) {
        this.expelledStudents = expelledStudents;
    }

    public Long getShouldBeExpelled() {
        return shouldBeExpelled;
    }
    public void setShouldBeExpelled(Long shouldBeExpelled) {
        this.shouldBeExpelled = shouldBeExpelled;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }
    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }
    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    @Override
    public String toString() {
        String s = new String();
        s += "ID: " + this.id + "\n";
        s += "Имя: " + this.name + "\n";
        s += "Координаты: " + this.coordinates + "\n";
        s += "Дата создания: " + this.creationDate + "\n";
        s += "Кол-во студентов: " + this.studentsCount + "\n";
        s += "Отчислено студентов: " + this.expelledStudents + "\n";
        s += "Должно быть отчислено студентов: " + this.shouldBeExpelled + "\n";
        s += "Форма обучения: " + (this.formOfEducation == null ? "NULL" + "\n" : this.formOfEducation + "\n");
//        try {
//            s += "Форма обучения: " + this.formOfEducation.toString() + "\n";
//        } catch (NullPointerException e) {
//            s += "Форма обучения: " + "NULL" + "\n";
//        }
        s += "Староста: " + this.groupAdmin + "\n";
        return s;
    }
}

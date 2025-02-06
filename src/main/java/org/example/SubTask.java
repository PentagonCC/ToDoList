package org.example;

public class SubTask extends Task {

//    private String title;
//    private String description;
//    private int id;
//    private Status status;
    private int epicId;

    public SubTask(String title, String description, int id, int epicId) {
       super(title, description, id);
       this.epicId = epicId;
    }


    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return super.toString() + '\n' + "Связана с эпиком:" + epicId;
    }
}

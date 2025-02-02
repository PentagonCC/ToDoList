package org.example;

public class SubTask extends Task {

//    private String title;
//    private String description;
//    private int id;
//    private Status status;
    private int epicId;

    public SubTask(String title, String description, int id, int epicId, Status status) {
       super(title, description, id, status);
       this.epicId = epicId;
    }


    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}

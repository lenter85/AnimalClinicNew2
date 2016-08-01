package com.example.myapplication.community.dto;

/**
 * Created by Administrator on 2016-07-07.
 */
public class Board {

    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private String boardDate;
    private int boardImage;

    public String getBoardDate() {
        return boardDate;
    }

    public void setBoardDate(String boardDate) {
        this.boardDate = boardDate;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }

    public String getBoardWriter() {
        return boardWriter;
    }

    public void setBoardWriter(String boardWriter) {
        this.boardWriter = boardWriter;
    }

    public int getBoardImage() {
        return boardImage;
    }

    public void setBoardImage(int boardImage) {
        this.boardImage = boardImage;
    }
}

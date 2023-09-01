package org.example.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public class DataDto<T> {

    private int currentPage;
    private int allPageCount;
    private List<T> data;

    public DataDto(int currentPage, int allPageCount, List<T> data) {

        this.currentPage = currentPage;
        this.allPageCount = allPageCount;
        this.data = data;
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getAllPageCount() {
        return allPageCount;
    }

    public void setAllPageCount(int allPageCount) {
        this.allPageCount = allPageCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}

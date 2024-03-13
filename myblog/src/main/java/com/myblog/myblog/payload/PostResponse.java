package com.myblog.myblog.payload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

//The purpose of this class is to return a list of posts along with the pageable parameters as a custom response back to client

@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
     private List<PostDto> dto;
     private int pageNo;
     private int pageSize;

    public List<PostDto> getDto() {
        return dto;
    }

    public void setDto(List<PostDto> dto) {
        this.dto = dto;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    private boolean lastPage;
     private  int totalPages;
}

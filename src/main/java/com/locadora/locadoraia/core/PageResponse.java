package com.locadora.locadoraia.core;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponse<T> {
    private List<T> content;
    private int number;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;

    public PageResponse(List<T> content, int number, int pageSize, long totalElements, int totalPages, boolean last, boolean first) {
        this.content = content;
        this.number = number;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
        this.first = first;
    }

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }
    public static <T> PageResponse<T> from(T content) {
        return new PageResponse<>(
                List.of(content),0, 1,1,1,true,true
        );
    }

    public List<T> getContent() {
        return content;
    }

    public int getNumber() {
        return number;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public boolean isFirst() {
        return first;
    }
}


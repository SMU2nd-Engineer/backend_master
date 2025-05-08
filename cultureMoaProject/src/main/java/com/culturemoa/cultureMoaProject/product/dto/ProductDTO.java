package com.culturemoa.cultureMoaProject.product.dto;

import java.time.LocalDateTime;

public class ProductDTO {
    private Long idx;
    private Long category_idx;
    private String title;
    private LocalDateTime sDate;
    private LocalDateTime eDate;
    private String useId;
    private Long price;
    private Long flag;

    public ProductDTO(Long idx, Long category_idx, String title, LocalDateTime sDate, LocalDateTime eDate, String useId, Long price, Long flag) {
        this.idx = idx;
        this.category_idx = category_idx;
        this.title = title;
        this.sDate = sDate;
        this.eDate = eDate;
        this.useId = useId;
        this.price = price;
        this.flag = flag;
    }

    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
    }

    public Long getCategory_idx() {
        return category_idx;
    }

    public void setCategory_idx(Long category_idx) {
        this.category_idx = category_idx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getsDate() {
        return sDate;
    }

    public void setsDate(LocalDateTime sDate) {
        this.sDate = sDate;
    }

    public LocalDateTime geteDate() {
        return eDate;
    }

    public void seteDate(LocalDateTime eDate) {
        this.eDate = eDate;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getFlag() {
        return flag;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }
}

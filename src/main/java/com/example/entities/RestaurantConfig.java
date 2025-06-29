package com.example.entities;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "restaurant_config")
public class RestaurantConfig {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "restaurant_name")
    private String restaurantName;
    
    @Column(name = "site_url")
    private String siteUrl;
    
    @Column(name = "color")
    private String color;
    
    @Transient
    private MultipartFile bannerPhoto;
    
    @Column(name = "banner_photo_path")
    private String bannerPhotoPath;
    
    @Column(name = "admin_password")
    private String adminPassword;
    
    @Column(name = "admin_username")
    private String adminUsername;
    
    @Column(name = "is_configured")
    private boolean configured = false;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "opening_hours", columnDefinition = "TEXT")
    private String openingHours;
    
    @Column(name = "specialities", columnDefinition = "TEXT")
    private String specialities;
    
    @Column(name = "average_price")
    private String averagePrice;
    
    @Column(name = "capacity")
    private Integer capacity;
    
    @Column(name = "parking_available")
    private Boolean parkingAvailable = false;
    
    @Column(name = "delivery_available")
    private Boolean deliveryAvailable = false;
    
    @Column(name = "takeaway_available")
    private Boolean takeawayAvailable = false;
    
    @Column(name = "reservation_required")
    private Boolean reservationRequired = false;
    
    @Column(name = "social_media_links", columnDefinition = "TEXT")
    private String socialMediaLinks;
    
    public RestaurantConfig() {}
    
    public RestaurantConfig(String restaurantName, String siteUrl, String color, String adminPassword) {
        this.restaurantName = restaurantName;
        this.siteUrl = siteUrl;
        this.color = color;
        this.adminPassword = adminPassword;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRestaurantName() {
        return restaurantName;
    }
    
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    
    public String getSiteUrl() {
        return siteUrl;
    }
    
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public MultipartFile getBannerPhoto() {
        return bannerPhoto;
    }
    
    public void setBannerPhoto(MultipartFile bannerPhoto) {
        this.bannerPhoto = bannerPhoto;
    }
    
    public String getBannerPhotoPath() {
        return bannerPhotoPath;
    }
    
    public void setBannerPhotoPath(String bannerPhotoPath) {
        this.bannerPhotoPath = bannerPhotoPath;
    }
    
    public String getAdminPassword() {
        return adminPassword;
    }
    
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
    
    public String getAdminUsername() {
        return adminUsername;
    }
    
    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }
    
    public boolean isConfigured() {
        return configured;
    }
    
    public void setConfigured(boolean configured) {
        this.configured = configured;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getOpeningHours() {
        return openingHours;
    }
    
    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }
    
    public String getSpecialities() {
        return specialities;
    }
    
    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }
    
    public String getAveragePrice() {
        return averagePrice;
    }
    
    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public Boolean getParkingAvailable() {
        return parkingAvailable;
    }
    
    public void setParkingAvailable(Boolean parkingAvailable) {
        this.parkingAvailable = parkingAvailable;
    }
    
    public Boolean getDeliveryAvailable() {
        return deliveryAvailable;
    }
    
    public void setDeliveryAvailable(Boolean deliveryAvailable) {
        this.deliveryAvailable = deliveryAvailable;
    }
    
    public Boolean getTakeawayAvailable() {
        return takeawayAvailable;
    }
    
    public void setTakeawayAvailable(Boolean takeawayAvailable) {
        this.takeawayAvailable = takeawayAvailable;
    }
    
    public Boolean getReservationRequired() {
        return reservationRequired;
    }
    
    public void setReservationRequired(Boolean reservationRequired) {
        this.reservationRequired = reservationRequired;
    }
    
    public String getSocialMediaLinks() {
        return socialMediaLinks;
    }
    
    public void setSocialMediaLinks(String socialMediaLinks) {
        this.socialMediaLinks = socialMediaLinks;
    }
} 
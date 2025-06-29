package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entities.Plat;
import com.example.entities.Review;
import com.example.repositories.PlatRepository;
import com.example.repositories.ReviewRepository;

import jakarta.validation.Valid;
import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private PlatRepository platRepository;

    @GetMapping("/review/{platId}")
    public String showReviewForm(@PathVariable Integer platId, Model model) {
        try {
            Plat plat = platRepository.findById(platId).orElse(null);
            if (plat == null) {
                return "redirect:/carte";
            }
            
            Review review = new Review();
            model.addAttribute("review", review);
            model.addAttribute("plat", plat);
            return "review-form";
        } catch (Exception e) {
            return "redirect:/config";
        }
    }

    @PostMapping("/review/{platId}")
    public String submitReview(@PathVariable Integer platId, 
                              @Valid @ModelAttribute("review") Review review,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        try {
            Plat plat = platRepository.findById(platId).orElse(null);
            if (plat == null) {
                return "redirect:/carte";
            }
            
            if (bindingResult.hasErrors()) {
                model.addAttribute("plat", plat);
                return "review-form";
            }
            
            review.setPlat(plat);
            
            reviewRepository.save(review);
            
            redirectAttributes.addFlashAttribute("successMessage", "Votre avis a été ajouté avec succès !");
            return "redirect:/carte";
        } catch (Exception e) {
            return "redirect:/config";
        }
    }

    @GetMapping("/reviews")
    public String showAllReviews(Model model) {
        try {
            List<Review> reviews = reviewRepository.findAllByOrderByDateDesc();
            model.addAttribute("reviews", reviews);
            
            if (!reviews.isEmpty()) {
                double averageRating = reviews.stream()
                    .mapToInt(Review::getNote)
                    .average()
                    .orElse(0.0);
                
                long positiveReviewsCount = reviews.stream()
                    .filter(review -> review.getNote() >= 4)
                    .count();
                
                model.addAttribute("averageRating", String.format("%.1f", averageRating));
                model.addAttribute("positiveReviewsCount", positiveReviewsCount);
            } else {
                model.addAttribute("averageRating", "0.0");
                model.addAttribute("positiveReviewsCount", 0);
            }
            
            return "reviews";
        } catch (Exception e) {
            return "redirect:/config";
        }
    }
} 
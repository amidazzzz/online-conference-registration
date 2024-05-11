package org.amida.onlineconferenceregistration.controller;

import org.amida.onlineconferenceregistration.model.Conference;
import org.amida.onlineconferenceregistration.model.User;
import org.amida.onlineconferenceregistration.service.ConferenceService;
import org.amida.onlineconferenceregistration.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/conference")
public class ConferenceController {

    private final ConferenceService conferenceService;
    private final UserService userService;

    public ConferenceController(ConferenceService conferenceService, UserService userService) {
        this.conferenceService = conferenceService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String getConferenceList(Model model) {
        model.addAttribute("conferences", conferenceService.findAllConferences());
        return "conferences";
    }


    @PostMapping("/register")
    public String registerForConference(@RequestParam("conferenceId") Long conferenceId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        userService.registerForConference(conferenceId, username);
        return "redirect:/conference/" + conferenceId;
    }

    @GetMapping("/{conferenceId}")
    public String getConference(@PathVariable Long conferenceId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Conference conference = conferenceService.getConferenceById(conferenceId);
        if (conference == null) {
            return "redirect:/error";
        }

        String username = userDetails.getUsername();
        boolean isRegistered = userService.isUserRegisteredForConference(conferenceId, username);
        model.addAttribute("isRegistered", isRegistered);
        model.addAttribute("conference", conference);
        return "conference-details";
    }

    @GetMapping("/create")
    public String showCreateConferenceForm(Model model) {
        model.addAttribute("conference", new Conference());
        return "conference-create-form";
    }

    @PostMapping("/create")
    public String createConference(@ModelAttribute("conference") Conference conference, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, @AuthenticationPrincipal UserDetails userDetails) {
        User createdBy = (User) userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDetails.getUsername()));

        conference.setCreatedBy(createdBy);
        conference.setCreationDate(LocalDate.now());

        conference.setEndDate(endDate);
        if (endDate == null) {
            return "redirect:/conference/create?error=endDateRequired";
        }

        conferenceService.save(conference);

        return "redirect:/conference/list";
    }

    @GetMapping("/delete/{conferenceId}")
    public String deleteConference(@PathVariable Long conferenceId, @AuthenticationPrincipal UserDetails userDetails) {
        Conference conference = conferenceService.getConferenceById(conferenceId);
        if (conference == null) {
            return "redirect:/error";
        }

        conferenceService.deleteConferenceById(conferenceId);
        return "redirect:/conference/list";
    }

    @GetMapping("/edit/{conferenceId}")
    public String editConference(@PathVariable Long conferenceId, Model model) {
        Conference conference = conferenceService.getConferenceById(conferenceId);
        model.addAttribute("conference", conference);
        return "conference-edit-form";
    }

    @PostMapping("/edit")
    public String editConference(@ModelAttribute("conference") Conference conference, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Conference existingConference = new Conference();

        existingConference.setId(conference.getId());
        existingConference.setTitle(conference.getTitle());
        existingConference.setDetails(conference.getDetails());
        existingConference.setEndDate(endDate);
        existingConference.setCreationDate(LocalDate.now());

        conferenceService.save(existingConference);

        return "redirect:/conference/" + conference.getId();
    }


}

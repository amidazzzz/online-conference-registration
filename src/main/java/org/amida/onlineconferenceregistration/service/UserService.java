package org.amida.onlineconferenceregistration.service;

import org.amida.onlineconferenceregistration.model.Conference;
import org.amida.onlineconferenceregistration.model.User;
import org.amida.onlineconferenceregistration.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ConferenceService conferenceService;


    public UserService(UserRepository userRepository, ConferenceService conferenceService, ConferenceService conferenceService1) {
        this.userRepository = userRepository;
        this.conferenceService = conferenceService1;
    }

    public Optional<Object> findByUsername(String username) {
        userRepository.findByUsername(username);
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public void registerForConference(Long conferenceId, String username) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Conference conference = conferenceService.getConferenceById(conferenceId);
            user.getConferences().add(conference);
            userRepository.save(user);
        }
    }

    public boolean isUserRegisteredForConference(Long conferenceId, String username) {
        User user = userRepository.findByUsername(username);
        Conference conference = conferenceService.getConferenceById(conferenceId);

        return user.getConferences().contains(conference);
    }
}

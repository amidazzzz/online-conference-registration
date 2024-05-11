package org.amida.onlineconferenceregistration.service;

import org.amida.onlineconferenceregistration.model.Conference;
import org.amida.onlineconferenceregistration.repository.ConferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;

    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    public List<Conference> findAllConferences() {
        return conferenceRepository.findAll();
    }

    public Conference getConferenceById(Long conferenceId) {
        Optional<Conference> conference = conferenceRepository.findById(conferenceId);
        return conference.orElse(null);
    }

    public void save(Conference conference) {
        conferenceRepository.save(conference);
    }

    public void deleteConferenceById(Long conferenceId) {
        conferenceRepository.deleteById(conferenceId);
    }
}